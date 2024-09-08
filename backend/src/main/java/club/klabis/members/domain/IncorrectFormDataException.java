package club.klabis.members.domain;

public class IncorrectFormDataException extends RuntimeException {
    public IncorrectFormDataException(String message) {
        super(message);
    }
}
