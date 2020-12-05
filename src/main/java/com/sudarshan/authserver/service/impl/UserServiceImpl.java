package com.sudarshan.authserver.service.impl;

import com.sudarshan.authserver.controller.UserController;
import com.sudarshan.authserver.dto.AuthTokenDto;
import com.sudarshan.authserver.dto.SuccessDto;
import com.sudarshan.authserver.dto.UserResponseDto;
import com.sudarshan.authserver.exception.UserException;
import com.sudarshan.authserver.model.User;
import com.sudarshan.authserver.repository.UserRepository;
import com.sudarshan.authserver.service.UserService;
import com.sudarshan.authserver.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public SuccessDto signUp(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("signUp service initiated for user "+ user.getUsername());
        user.setLastUpdated(LocalDateTime.now());
        if (!checkUsernameAvailability(user.getUsername())) {
            throw new UserException("User name alredy present with name "+ user.getUsername(), HttpStatus.FOUND);
        }
        User savedUser = userRepository.save(user);
        if (savedUser!=null) {
            SuccessDto successDto = new SuccessDto();
            successDto.setMessage("User "+savedUser.getUsername()+" successfully created.!!!");
            successDto.setCurrentTime(LocalDateTime.now().toString());
            logger.info("User successfully added "+ savedUser.getUsername());
            return  successDto;
        } else {
            logger.error("Something went wrong. No output from DB for user "+ user.getUsername());
            throw new UserException("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public AuthTokenDto signIn(User user) {
        logger.info("Initiating authentication for user "+ user.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            logger.info("Successfully authenticated for  "+ user.getUsername());
            AuthTokenDto authTokenDto = new AuthTokenDto();
            authTokenDto.setToken(jwtTokenUtil.generateJwtToken((UserDetails) authentication.getPrincipal()));
            authTokenDto.setLastUpdated(LocalDateTime.now().toString());
            return authTokenDto;
        } catch (AuthenticationException exception) {
            logger.error("Authenticating failed for  "+ user.getUsername() + " with error "+ exception.getMessage());
            throw new UserException(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public boolean checkUsernameAvailability(String username) {
        logger.info("Initiating username availability check for "+ username);
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty()) {
            logger.info("Username not found with "+ username);
            return true;
        }
        logger.info("Username found with "+ username);
        return false;
    }

    @Override
    public UserResponseDto searchUserByUsername(String username) {
        logger.info("Initiating searchUserByUsername for user "+ username);
        final User user = userRepository.findByusername(username);
        if (user==null) {
            logger.error("No user found with name "+username);
            throw new UserException("No user found with name " + username, HttpStatus.OK);
        }
        return userToUserResponseDto(user);
    }

    private UserResponseDto userToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setLastUpdated(user.getLastUpdated().toString());
        StringBuilder stringBuilder = new StringBuilder();
        List<String> rolesList = new ArrayList<String>();
        user.getRoles().forEach(role -> {
            rolesList.add(role.getAuthority());
        });
        userResponseDto.setRolesAssigned(rolesList);
        return userResponseDto;
    }


}
