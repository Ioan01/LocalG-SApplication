package upt.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import upt.backend.authentication.TokenService;
import upt.backend.authentication.User;
import upt.backend.authentication.UserService;
import upt.backend.store.Product;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
        return new ResponseEntity<User>(userService.findUser(tokenService.getAudience(token)).withPassword(""), HttpStatus.OK);
    }

    @RequestMapping(value = "/update",consumes = "application/json",method = RequestMethod.POST)
    public ResponseEntity<User>updateProfile(@RequestHeader String authorization,@RequestBody DetailsChangeRequest request)
    {
        Optional<User> user = userService.getUser(tokenService.getAudience(authorization));


        AtomicReference<User> newUser = new AtomicReference<>();

        user.ifPresentOrElse((a)-> newUser.set(userService.updateUser(a, request)),
                ()-> {throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);});

        return new ResponseEntity<>(newUser.get().withPassword(null),HttpStatus.OK);
    }

    @PostMapping("/remove")
    ResponseEntity<User> removeProduct(
            @RequestHeader("Authorization")String token)
    {
        User deleted = userService.removeUser(tokenService.getAudience(token));
        if(deleted != null)
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        else
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Product does not exist or is not yours.");
    }

}
