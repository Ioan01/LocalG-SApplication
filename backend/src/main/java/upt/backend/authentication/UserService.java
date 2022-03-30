package upt.backend.authentication;

import com.google.common.hash.Hashing;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.Yaml;
import upt.backend.config.YAMLConfig;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService
{
    @Autowired
    UserRepository userRepository;

    @Autowired
    YAMLConfig yamlConfig;

    @Autowired
    PasswordEncoder passwordEncoder;

    private void testCredentials(@NonNull  String username,@NonNull String password,@NonNull String role)
    {
        int passwordLength = password.length();
        if (passwordLength > yamlConfig.getMaxPassword())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password too long");
        if (passwordLength < yamlConfig.getMinPassword())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password too short");
        int usernameLength = username.length();
        if (usernameLength > yamlConfig.getMaxUser())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username too long");
        if (usernameLength < yamlConfig.getMinUser())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username too short");

        if (Arrays.stream(yamlConfig.getRoles()).noneMatch(p -> p.equals(role)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid role provided");
    }

    public User addUser(@NonNull User user)
    {
        testCredentials(user.getUsername(), user.getPassword(),user.getRole());

        @NonNull User finalUser = user
                .withPassword(passwordEncoder.encode(user.getPassword()));


        return userRepository.save(finalUser);
    }

    public Optional<User>getUser(@NonNull String username)
    {
        return Optional.ofNullable(userRepository.findUserByUsername(username));
    }

    public User findUser(String username)
    {
        return userRepository.findUserByUsername(username);
    }
}
