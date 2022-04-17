package upt.backend.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController
{
    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;
    @GetMapping("/sayhello")
    public String hello(@RequestHeader("Authorization")String token)
    {
        return "hello " + userService.findUser(tokenService.getAudience(token)).getUsername();
    }
}
