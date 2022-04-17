package upt.backend.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import upt.backend.authentication.TokenService;
import upt.backend.authentication.User;
import upt.backend.authentication.UserService;

@RestController
public class ProductController {
    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(@RequestHeader("Token")String token, @RequestBody Product product)
    {
        return new ResponseEntity<String>(tokenService.getAudience(token) + " asked to add a product", HttpStatus.OK);
    }
}
