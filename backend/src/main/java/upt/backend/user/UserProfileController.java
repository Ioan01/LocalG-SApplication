package upt.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upt.backend.security.TokenService;

@RestController
@RequestMapping("/user")
public class UserProfileController
{
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@RequestHeader("Authorization")String token)
    {

        return new ResponseEntity<User>(userService.findUser(tokenService.getUserName(token)).withPassword(""), HttpStatus.OK);
    }

}
