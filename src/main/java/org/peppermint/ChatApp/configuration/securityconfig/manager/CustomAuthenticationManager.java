package org.peppermint.ChatApp.configuration.securityconfig.manager;

import lombok.AllArgsConstructor;
import org.peppermint.ChatApp.model.User;
import org.peppermint.ChatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CustomAuthenticationManager implements AuthenticationManager {
    @Autowired
    private UserService userService;
    private BCryptPasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userService.findUserByUsername(authentication.getName());
        if (!encoder.matches(authentication.getCredentials().toString(), user.getPassword())) throw new BadCredentialsException("You provided an incorrect password!");
        return new UsernamePasswordAuthenticationToken(authentication.getName(), user.getPassword());
    }
}
