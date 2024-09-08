package club.klabis.members.domain;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String message) {
        super(message);
    }

    public MemberNotFoundException(Integer memberId) {
        this("Member with ID '%s' doesnt exist".formatted(memberId));
    }
}
