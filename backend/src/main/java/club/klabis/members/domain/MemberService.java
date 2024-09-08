package club.klabis.members.domain;

import club.klabis.members.domain.forms.MemberEditForm;
import club.klabis.members.domain.forms.RegistrationForm;
import jakarta.validation.Valid;
import org.jmolecules.ddd.annotation.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Validated
@Service
public interface MemberService {
    Member registerMember(@Valid RegistrationForm registrationForm);

    List<Member> findAll(boolean includeSuspended);

    Optional<Member> findById(Integer memberId);

    RegistrationNumber suggestRegistrationNumber(LocalDate dateOfBirth, Sex sex);

    Member editMember(Integer memberId, @Valid MemberEditForm editForm);

    Optional<MembershipSuspensionInfo> getSuspensionInfoForMember(int memberId);

    void suspendMembershipForMember(int memberId, boolean forceSuspension);
}
