package goprea.software.backend.exception.types;

public class NotEnoughRightsRestException extends RestException{

  public NotEnoughRightsRestException() {
    super("Exception.not.enough.rights");
  }
}
