package com.sudarshan.authserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {
    private String username;
    private String lastUpdated;
    private List<String> rolesAssigned;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<String> getRolesAssigned() {
        return rolesAssigned;
    }

    public void setRolesAssigned(List<String> rolesAssigned) {
        this.rolesAssigned = rolesAssigned;
    }
}
