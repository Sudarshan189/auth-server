package com.sudarshan.authserver.controller;

import com.sudarshan.authserver.dto.AuthTokenDto;
import com.sudarshan.authserver.dto.SuccessDto;
import com.sudarshan.authserver.dto.UserResponseDto;
import com.sudarshan.authserver.model.User;
import com.sudarshan.authserver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping(path = "/signup")
    public ResponseEntity<SuccessDto> signUpUser(@RequestBody(required = true) User user) {
        SuccessDto successDto = userService.signUp(user);
        logger.info("Executed method signUp "+ successDto.getMessage());
        ResponseEntity<SuccessDto> signUpResponseEntity = new ResponseEntity<SuccessDto>(successDto, HttpStatus.CREATED);
        return  signUpResponseEntity;
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<AuthTokenDto> signInUser(@RequestBody(required = true) User user) {
        AuthTokenDto authTokenDto = userService.signIn(user);
        logger.info("Executed method signIn for user "+ user.getUsername()+ " token is "+ authTokenDto.getToken());
        ResponseEntity<AuthTokenDto> signInResponseEntity = new ResponseEntity<AuthTokenDto>(authTokenDto, HttpStatus.OK);
        return  signInResponseEntity;
    }

    @GetMapping(path = "/search")
    public ResponseEntity<UserResponseDto> searchUser(@PathVariable(name = "username") String username) {
        UserResponseDto userResponseDto = userService.searchUserByUsername(username);
        logger.info("Execueted methods search user for user "+ username);
        return new ResponseEntity<UserResponseDto>(userResponseDto, HttpStatus.OK);
    }


}
