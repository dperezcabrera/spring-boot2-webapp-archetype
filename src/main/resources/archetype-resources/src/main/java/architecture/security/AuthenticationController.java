#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.security;

import ${package}.architecture.dtos.CredentialsDto;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@Slf4j
@RestController
@RequestMapping(consumes = "application/json")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class AuthenticationController {

    private AuthenticationManager authenticationManager;

    private SessionRegistry sessionRegistry;
    
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<Void> login(HttpServletRequest request, @RequestBody CredentialsDto credentialsDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentialsDto.getUsername(), credentialsDto.getPassword());
        try {
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            sessionRegistry.registerNewSession(request.getSession().getId(), auth.getPrincipal());
            return ResponseEntity.ok().build();
        } catch (BadCredentialsException ex) {
            log.error("Bad credentials error", ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
