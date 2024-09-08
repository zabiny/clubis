package club.klabis.members.events;

import club.klabis.common.DomainEvent;
import club.klabis.members.domain.Member;

@org.jmolecules.event.annotation.DomainEvent
public class MemberEditedEvent extends DomainEvent<Member> {

    public MemberEditedEvent(Member aggregate) {
        super(aggregate);
    }
}
