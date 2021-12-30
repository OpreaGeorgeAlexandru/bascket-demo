package goprea.software.backend.exception;

import goprea.software.backend.exception.types.AlreadyExistsRestException;
import goprea.software.backend.exception.types.NotFoundRestException;
import goprea.software.backend.exception.types.RestException;
import goprea.software.backend.model.RestMessage;
import goprea.software.backend.model.TranslationMessage;
import goprea.software.backend.service.TranslationService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class RestExceptionHandler {

    private final TranslationService translationService;

    @Autowired
    public RestExceptionHandler(TranslationService translationService) {
        this.translationService = translationService;
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestMessage> handleIllegalArgument(RestException ex, Locale locale) {
        String errorMessage = translationService.getMessage(locale, TranslationMessage.UNEXPECTED_ERROR,ex.getArgs());
        ex.printStackTrace();
        return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotFoundRestException.class)
    public ResponseEntity<RestMessage> handleExceptions(NotFoundRestException ex, Locale locale) {
        String errorMessage = translationService.getMessage(locale, TranslationMessage.NOT_FOUND_ERROR,ex.getArgs());
        ex.printStackTrace();
        return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsRestException.class)
    public ResponseEntity<RestMessage> handleExceptions(AlreadyExistsRestException ex, Locale locale) {
        String errorMessage = translationService.getMessage(locale, TranslationMessage.ALREADY_EXIST_EXCEPTION,ex.getArgs());
        ex.printStackTrace();
        return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestMessage> handleExceptions(MethodArgumentNotValidException ex, Locale locale) {
        String errorMessage = translationService.getMessage(locale, TranslationMessage.INCORRECT_PASSWORD_PATTERN);
        ex.printStackTrace();
        return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestMessage> handleExceptions(Exception ex, Locale locale) {
        String errorMessage = translationService.getMessage(locale, TranslationMessage.UNEXPECTED_ERROR);
        ex.printStackTrace();
        return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RestMessage> handle(final BadCredentialsException ex) {
        return getResponse(HttpStatus.BAD_REQUEST, ex, ExceptionTranslationStatus.INVALID_CREDENTIALS);
    }

    @ExceptionHandler
    public ResponseEntity<RestMessage> handle(final ExpiredJwtException ex) {
        return getResponse(HttpStatus.UNAUTHORIZED, ex, ExceptionTranslationStatus.SESSION_EXPIRED);
    }

    @ExceptionHandler
    public ResponseEntity<RestMessage> handle(final SignatureException ex) {
        return getResponse(HttpStatus.UNAUTHORIZED, ex, ExceptionTranslationStatus.TOKEN_MISMATCH);
    }

    private ResponseEntity<RestMessage> getResponse(final HttpStatus status, final Exception ex, final ExceptionTranslationStatus... exceptionTranslationStatus) {
        final RestMessage errorResponse = new RestMessage(
                ex.getMessage(),
                exceptionTranslationStatus != null && exceptionTranslationStatus.length > 0 ? exceptionTranslationStatus[0] : null,
                status
                );

        ex.printStackTrace();

        return new ResponseEntity<>(errorResponse, status);
    }
}
