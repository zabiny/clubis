package club.klabis.members.events;

import club.klabis.common.DomainEvent;
import club.klabis.members.domain.Member;

@org.jmolecules.event.annotation.DomainEvent
public class MemberWasSuspendedEvent extends DomainEvent<Member> {
    public MemberWasSuspendedEvent(Member suspendedMember) {
        super(suspendedMember);
    }
}
