package injection.exception;

public class ParsingException extends RuntimeException {

    public ParsingException(Throwable cause) {
        super("An error occurred while trying to parse class", cause);
    }
}
