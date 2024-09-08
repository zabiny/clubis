package club.klabis.config.authserver.socialloginsupport;

import club.klabis.appusers.domain.ApplicationUser;
import club.klabis.appusers.domain.ApplicationUsersRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
class GoogleOidcUserToKlabisSocialLoginOidcUserMapper implements SocialLoginOidcUserToKlabisOidcUserMapper {
    @Override
    public String getRegistration() {
        return "google";
    }

    @Override
    public Function<String, Optional<ApplicationUser>> findMemberFunction(ApplicationUsersRepository memberService) {
        return memberService::findByGoogleSubject;
    }
}
