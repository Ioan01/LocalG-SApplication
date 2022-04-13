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
    public String addProduct(@RequestHeader("Authorization")String token, @RequestBody Product product)
    {
        return userService.findUser(TokenService.getAudience(tokenService.extractJWTMap(token))).getUsername()
                    + " added " + product.getName() + " to the store, which costs $" + product.getPrice();
    }
}
