#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.arch.auth.controllers;

import ${package}.arch.auth.dtos.CredentialsDto;
import ${package}.arch.auth.dtos.UserDto;
import ${package}.arch.auth.services.UserService;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final SessionRegistry sessionRegistry;

    private final UserService userService;

    private final AuditorAware<String> auditorAware;

    @GetMapping("/obtain-my-profile")
    public UserDto obtainMyProfile() {
        return auditorAware.getCurrentAuditor().map(userService::getUser).get();
    }

    @PostMapping("/auth/login")
    public UserDto login(HttpServletRequest request, @RequestBody CredentialsDto credentialsDto) {
        UserDto userDto = userService.getUser(credentialsDto.getUsername());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentialsDto.getUsername(), credentialsDto.getPassword());
        Authentication auth = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        sessionRegistry.registerNewSession(request.getSession().getId(), auth.getPrincipal());
        return userDto;
    }
}
