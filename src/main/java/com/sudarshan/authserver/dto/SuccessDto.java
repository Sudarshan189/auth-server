package com.sudarshan.authserver.dto;

import lombok.Data;

@Data
public class SuccessDto {
    private String message;
    private String currentTime;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
