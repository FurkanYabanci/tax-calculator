package com.yabanci.ayrotek.enums;

public enum TaxErrorMessage implements BaseErrorMessage{

    TAX_NOT_FOUND("Tax not found!"),;

    private String message;
    TaxErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}