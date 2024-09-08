package club.klabis.common.inmemoryrepository;

import club.klabis.appusers.domain.ApplicationUser;
import club.klabis.appusers.domain.ApplicationUsersRepository;
import club.klabis.members.domain.Member;
import club.klabis.members.domain.MembersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("inmemorydb")
class RepositoryFactory {

    private final InMemoryRepositoryWithEventsFactory repoFactory;

    RepositoryFactory(InMemoryRepositoryWithEventsFactory repoFactory) {
        this.repoFactory = repoFactory;
    }

    @Bean
    public MembersRepository membersRepository() {
        return repoFactory.initializeRepositoryWithEventPublishingPostprocessing(MembersRepository.class, new MembersInMemoryRepository(), Member.class);
    }

    @Bean
    public ApplicationUsersRepository applicationUsersRepository() {
        return repoFactory.initializeRepositoryWithEventPublishingPostprocessing(ApplicationUsersRepository.class, new ApplicationUsersInMemoryRepository(), ApplicationUser.class);
    }

}
