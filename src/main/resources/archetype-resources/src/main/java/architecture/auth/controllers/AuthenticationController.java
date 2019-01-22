#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.auth.controllers;

import ${package}.architecture.auth.dtos.CredentialsDto;
import ${package}.architecture.auth.dtos.UserDto;
import ${package}.architecture.auth.services.UserService;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final SessionRegistry sessionRegistry;

    private final UserService userService;

    @GetMapping("/auth/login")
    public ResponseEntity<UserDto> login() {
        ResponseEntity<UserDto> result;
        String username = SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase();
        UserDto userDto = userService.getUser(username);
        if (userDto != null && !userDto.getRoles().isEmpty()) {
            result = ResponseEntity.ok(userDto);
        } else {
            result = ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return result;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserDto> login(HttpServletRequest request, @RequestBody CredentialsDto credentialsDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentialsDto.getUsername(), credentialsDto.getPassword());
        try {
            Authentication auth = authenticationManager.authenticate(token);
            UserDto userDto = userService.getUser(credentialsDto.getUsername());
            if (userDto == null || userDto.getRoles().isEmpty()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
            SecurityContextHolder.getContext().setAuthentication(auth);
            sessionRegistry.registerNewSession(request.getSession().getId(), auth.getPrincipal());
            return ResponseEntity.ok(userDto);
        } catch (BadCredentialsException ex) {
            log.error("Bad credentials error", ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
