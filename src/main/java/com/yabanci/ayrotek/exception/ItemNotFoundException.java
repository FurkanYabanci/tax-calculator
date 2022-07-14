package com.yabanci.ayrotek.exception;


import com.yabanci.ayrotek.enums.BaseErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(BaseErrorMessage message) {
        super(message.getMessage());
    }

}