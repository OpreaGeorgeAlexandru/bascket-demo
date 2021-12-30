package goprea.software.backend.exception.types;

import goprea.software.backend.model.TranslationMessage;
import lombok.Getter;

@Getter
public class RestException extends RuntimeException {
    protected final String message;
    protected final Object[] args;

    public RestException(){
        message = null;
        args = null;
    }

    public RestException(String message){
        this.message = message;
        args = null;
    }

    public RestException(String message,Object... args){
        this.message = message;
        this.args = args;
    }

    public RestException(TranslationMessage translationMessage, Object... args){
        this.message = translationMessage.getMessageKey();
        this.args = args;
    }
}
