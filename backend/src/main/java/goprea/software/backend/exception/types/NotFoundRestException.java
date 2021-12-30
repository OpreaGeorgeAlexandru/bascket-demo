package goprea.software.backend.exception.types;

public class NotFoundRestException extends RestException{

    public NotFoundRestException(Object... args) {
        super("Exception.not.found", args);
    }

    public NotFoundRestException() {
    super("Exception.not.found.resource");
  }
}
