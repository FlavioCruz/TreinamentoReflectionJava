package injection.exception;

public class CastingException extends RuntimeException {

    public CastingException(Throwable e){
        super("An error occurred while casting variable", e);
    }
}
