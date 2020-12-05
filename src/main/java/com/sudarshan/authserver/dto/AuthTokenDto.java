package com.sudarshan.authserver.dto;

import lombok.Data;

@Data
public class AuthTokenDto {
    private String token;
    private String lastUpdated;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
