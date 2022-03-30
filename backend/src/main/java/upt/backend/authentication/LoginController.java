package upt.backend.authentication;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController
{
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JWT jwt;

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody User user)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
                user.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        String token = jwt.generateToken(userDetails);

        return new ResponseEntity<String>(token, HttpStatus.OK);
    }
}
