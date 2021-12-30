package goprea.software.backend.model;


import goprea.software.backend.exception.ExceptionTranslationStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RestMessage {
    private String message;
    private List<String> messages;
    private LocalDateTime date;
    private ExceptionTranslationStatus exceptionTranslationStatus;
    private HttpStatus status;

    public RestMessage(){
        date = LocalDateTime.now();
    }

    public RestMessage(List<String> messages) {
        this.messages = messages;
    }

    public RestMessage(String message) {
        this.message = message;
    }

    public RestMessage(String message, ExceptionTranslationStatus exceptionTranslationStatus, HttpStatus status){
        this.message = message;
        this.exceptionTranslationStatus = exceptionTranslationStatus;
        this.status = status;
    }

}
