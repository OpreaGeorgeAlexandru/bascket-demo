package goprea.software.backend.exception.types;

public class AlreadyExistsRestException extends RestException{
    public AlreadyExistsRestException(Object... args) {
        super("Exception.already.exist",args);
    }
}
