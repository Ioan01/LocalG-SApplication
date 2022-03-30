package upt.backend.authentication;

import com.google.common.hash.Hashing;
import com.mongodb.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;

@RestController
public class RegisterController
{
    @Autowired
    UserService userService;

    @PostMapping("/register")
    ResponseEntity<User> register(@RequestBody User client)
    {
        try
        {
            return new ResponseEntity(userService.addUser(client)
                    ,HttpStatus.OK);
        }
        catch (DuplicateKeyException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username already exists");
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
