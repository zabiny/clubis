package club.klabis.common.inmemoryrepository;

import club.klabis.members.domain.Member;
import club.klabis.members.domain.MembersRepository;
import club.klabis.members.domain.RegistrationNumber;
import club.klabis.members.domain.Sex;

import java.time.LocalDate;
import java.util.List;

class MembersInMemoryRepository extends InMemoryRepositoryImpl<Member, Integer> implements MembersRepository {
    MembersInMemoryRepository() {
        super(Member::getId);
    }

    @Override
    public List<Member> findMembersWithSameBirthyearAndSex(LocalDate birthDate, Sex sex) {
        return findAll().stream().filter(m -> sex.equals(m.getSex()) && m.getDateOfBirth().getYear() == birthDate.getYear()).toList();
    }

    @Override
    public boolean isRegistrationNumberUsed(RegistrationNumber registrationNumber) {
        return this.findAll().stream().anyMatch(it -> it.getRegistration().equals(registrationNumber));
    }

    @Override
    public List<Member> findAllActive() {
        return findAll().stream().filter(m -> !m.isSuspended()).toList();
    }
}
