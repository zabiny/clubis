package club.klabis.members.events;

import club.klabis.common.DomainEvent;
import club.klabis.members.domain.Member;

@org.jmolecules.event.annotation.DomainEvent
public class MemberCreatedEvent extends DomainEvent<Member> {

    public MemberCreatedEvent(Member aggregate) {
        super(aggregate);
    }
}
