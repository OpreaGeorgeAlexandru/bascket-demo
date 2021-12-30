package goprea.software.backend.model;

import lombok.Getter;

public enum TranslationMessage {

    UNEXPECTED_ERROR("Exception.unexpected"),
    INCORRECT_PASSWORD_PATTERN("Exception.password.pattern.incorrect"),
    NOT_FOUND_ERROR("Exception.not.found"),
    NOT_FOUND_RESOURCE_ERROR("Exception.not.found.resource"),
    ALREADY_EXIST_EXCEPTION("Exception.already.exist"),
    ENTITY_ALREADY_EXIST("Exception.already.exist");

    @Getter
    private final String messageKey;

    TranslationMessage(String messageKey){
        this.messageKey = messageKey;
    }
}
