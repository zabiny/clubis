package club.klabis.common.inmemoryrepository;

import club.klabis.appusers.domain.ApplicationUser;
import club.klabis.appusers.domain.ApplicationUserNotFound;
import club.klabis.appusers.domain.ApplicationUsersRepository;

import java.util.Optional;

class ApplicationUsersInMemoryRepository extends InMemoryRepositoryImpl<ApplicationUser, Integer> implements ApplicationUsersRepository {
    ApplicationUsersInMemoryRepository() {
        super(ApplicationUser::getId);
    }

    @Override
    public Optional<ApplicationUser> findByUserName(String username) {
        return this.findAll().stream().filter(it -> username.equals(it.getUsername())).findAny();
    }

    @Override
    public Optional<ApplicationUser> findByGoogleSubject(String googleSub) {
        return this.findAll().stream().filter(it -> googleSub.equals(it.getGoogleSubject())).findAny();
    }

    @Override
    public ApplicationUser findByMemberId(int memberId) {
        return findAll().stream().filter(it -> it.getMemberId().filter(id -> id == memberId).isPresent()).findAny().orElseThrow(() -> ApplicationUserNotFound.forMemberId(memberId));
    }
}
