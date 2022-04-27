package upt.backend.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestController
public class RegisterController
{
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService jwt;

    @PostMapping("/register")
    ResponseEntity<User> register(@RequestBody User client)
    {
        try
        {
            return new ResponseEntity(userService.addUser(client).withPassword(null)
                    ,HttpStatus.OK);
        }
        catch (org.springframework.dao.DuplicateKeyException e)
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Username already exists");
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    /// ONLY FOR DEVELOPMENT VERSIONS
    @GetMapping("/deleteAll")
    ResponseEntity<String> deleteAll(@RequestHeader("Authorization") String token )
    {
        try
        {
            String user = jwt.getAudience(token);
            userService.deleteUsers();
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Bad token provided");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
