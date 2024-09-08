package club.klabis.appusers.domain;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface ApplicationUsersRepository extends ListCrudRepository<ApplicationUser, Integer> {

    Optional<ApplicationUser> findByUserName(String username);

    Optional<ApplicationUser> findByGoogleSubject(String googleSub);

    ApplicationUser findByMemberId(int memberId) throws ApplicationUserNotFound;

}
