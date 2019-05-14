package main.exception;

public class ParsingException extends RuntimeException {
    public ParsingException(Throwable cause) {
        super(cause);
    }
}
