import {paths} from "@/api/schema";
import {
  AppLoadContext,
  createCookieSessionStorage,
} from "@remix-run/cloudflare";
import {redirect} from "@remix-run/router";
import {jwtDecode} from "jwt-decode";
import {Authenticator} from "remix-auth";
import {OAuth2Strategy} from "remix-auth-oauth2";
import createClient from "openapi-fetch";

// Create an instance of the authenticator, pass a generic with what
// strategies will return and will store in the session
type User = {
  username: string;
  accessToken: string;
  refreshToken: string;
};

// https://sergiodxa.com/articles/working-with-refresh-tokens-in-remix
// https://github.com/sergiodxa/remix-auth-oauth2
export async function getAuth({
                                request,
                                context,
                                headers = new Headers(),
                              }: {
  request: Request;
  context: AppLoadContext;
  headers?: Headers;
}): Promise<User & { headers: Headers }> {
  const {auth, sessionStorage, strategy} = authenticate(context);
  const session = await sessionStorage.getSession("cookie");
  const user = await auth.isAuthenticated(request);

  // get the auth data from the session
  let accessToken = user?.accessToken;
  let refreshToken = user?.refreshToken;

  // if not found, redirect to login, this means the user is not even logged-in
  if (!user || !accessToken || !refreshToken) throw redirect("/login");

  try {
    if (new Date(getExpirationDate(accessToken)) > new Date()) {
      return {...user, headers: new Headers()};
    }

    const tokens = await strategy.refreshToken(refreshToken);

    accessToken = tokens.access_token;
    refreshToken = tokens.refresh_token as string;

    session.set(auth.sessionKey, {
      ...user,
      accessToken: accessToken,
      refreshToken: refreshToken,
    });
    headers.append("Set-Cookie", await sessionStorage.commitSession(session));

    // redirect to the same URL if the request was a GET (loader)
    if (request.method === "GET") throw redirect(request.url, {headers});

    // return the access token so you can use it in your action
    return {...user, accessToken, refreshToken, headers};
  } catch {
    throw redirect("/login");
  }
}

export const KLABIS_AUTH = "klabis-auth";

export function authenticate(context: AppLoadContext) {
  const sessionStorage = createCookieSessionStorage({
    cookie: {
      name: "_klabis_session", // use any name you want here
      sameSite: "lax", // this helps with CSRF
      path: "/", // remember to add this so the cookie will work in all routes
      httpOnly: true, // for security reasons, make this cookie http only
      secrets: [context.cloudflare.env.COOKIE_SECRET], // replace this with an actual secret
      secure: process.env.NODE_ENV === "production", // enable this in prod only
    },
  });

  const strategy = new OAuth2Strategy<
    User,
    { provider: "klabis-auth" },
    { id_token: string }
  >(
    {
      clientId: context.cloudflare.env.AUTH_CLIENT_ID,
      clientSecret: context.cloudflare.env.AUTH_CLIENT_SECRET,

      authorizationEndpoint: "https://klabis-auth.polach.cloud/oauth/authorize",
      tokenEndpoint: "https://klabis-auth.polach.cloud/oauth/token",
      redirectURI: `${context.cloudflare.env.BASE_URL}/auth/callback`,
      tokenRevocationEndpoint: "https://klabis-auth.polach.cloud/oauth2/revoke",

      // codeChallengeMethod: "S256", // optional
      scopes: ["openid", "profile", "email"], // optional

      // authenticateWith: "request_body", // optional
    },
    async ({tokens, profile, context, request}) => {
      // const res = await fetch("https://klabis-auth.polach.cloud/oidc/userinfo", {
      //   headers: {
      //     authorization: `Bearer ${tokens.access_token}`
      //   }
      // });
      // const json = await res.json();
      const accessToken: string = tokens.access_token;
      const refreshToken: string = tokens.refresh_token as string;
      const parsed = jwtDecode(accessToken);
      const username = parsed.sub as string;

      // // @ts-ignore
      return {username, accessToken, refreshToken};
    },
  );
  // this is optional, but if you setup more than one OAuth2 instance you will
  // need to set a custom name to each one

  const authenticator = new Authenticator<User>(sessionStorage);
  authenticator.use(strategy, KLABIS_AUTH);
  return {auth: authenticator, strategy, sessionStorage};
}

export async function getClient({
                                  context,
                                  request,
                                }: { request: Request; context: AppLoadContext }) {
  const user = await getAuth({context, request});
  const client = createClient<paths>({
    baseUrl: "https://klabis-auth.polach.cloud",
    headers: {
      Authorization: `Bearer ${user.accessToken}`,
    },
  });
  return client;
}

function getExpirationDate(token: string): Date {
  const parsed = jwtDecode(token);
  return new Date((parsed.exp as number) * 1000);
}
