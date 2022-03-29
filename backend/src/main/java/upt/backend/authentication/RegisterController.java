package upt.backend.authentication;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;

@RestController
public class RegisterController
{
    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    ResponseEntity<User> register(@RequestBody User client)
    {
        try
        {
            return new ResponseEntity(userRepository.save(client
                    .withPassword(Hashing.sha256().hashString(client.password,StandardCharsets.UTF_8).toString()))
                    ,HttpStatus.OK);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
