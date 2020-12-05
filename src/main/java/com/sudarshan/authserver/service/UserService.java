package com.sudarshan.authserver.service;

import com.sudarshan.authserver.dto.AuthTokenDto;
import com.sudarshan.authserver.dto.SuccessDto;
import com.sudarshan.authserver.dto.UserResponseDto;
import com.sudarshan.authserver.model.User;

public interface UserService {

    public SuccessDto signUp(User user);

    public AuthTokenDto signIn(User user);

    public boolean checkUsernameAvailability(String username);

    public UserResponseDto searchUserByUsername(String username);
}
