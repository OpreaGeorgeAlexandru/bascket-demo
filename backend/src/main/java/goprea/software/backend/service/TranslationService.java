package goprea.software.backend.service;

import goprea.software.backend.model.TranslationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class TranslationService {

    private final MessageSource messageSource;

    @Autowired
    public TranslationService(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    public String getMessage(Locale locale, TranslationMessage translationMessage, Object... args){
        String defaultErrorMessage = messageSource.getMessage(TranslationMessage.UNEXPECTED_ERROR.getMessageKey(),null,locale);
        return messageSource.getMessage(translationMessage.getMessageKey(), args, defaultErrorMessage, locale);
    }

    public String getMessage(String language, TranslationMessage translationMessage, Object... args){
        Locale locale = null;
        try{
            // try to convert to locale else use default ENGLISH
            locale = new Locale(language);
        } catch (Exception e){
            locale = Locale.ENGLISH;
        }
        return getMessage(locale,translationMessage,args);
    }
}
