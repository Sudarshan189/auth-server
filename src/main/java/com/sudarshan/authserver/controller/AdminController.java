package com.sudarshan.authserver.controller;

import com.sudarshan.authserver.dto.UserResponseDto;
import com.sudarshan.authserver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;


    @GetMapping("/user/{username}")
    public ResponseEntity<UserResponseDto> searchUserByUsername(@PathVariable(name = "username") String username) {
        UserResponseDto userResponseDto = userService.searchUserByUsername(username);
        logger.info("Executed searchUserByUsername for user "+ username);
        return new ResponseEntity<UserResponseDto>(userResponseDto, HttpStatus.OK);
    }

}
