package com.sudarshan.authserver.security;

import com.sudarshan.authserver.model.User;
import com.sudarshan.authserver.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(MyUserDetails.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Initiating loaduser Procedure for "+ username);
        final User user = userRepository.findByusername(username);

        if(user == null) {
            logger.error("Null user found in DB "+ username);
            throw new UsernameNotFoundException("User "+ username + " not found");
        }
        logger.info("Found user "+ user.getUsername());
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
