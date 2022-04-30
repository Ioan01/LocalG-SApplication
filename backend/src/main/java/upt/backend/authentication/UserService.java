package upt.backend.authentication;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import upt.backend.config.YAMLConfig;
import upt.backend.images.Photo;
import upt.backend.images.PhotoService;
import upt.backend.user.DetailsChangeRequest;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private YAMLConfig yamlConfig;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PhotoService photoService;



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

    public void deleteUsers()
    {
        if (yamlConfig.getEnvironment().equals("development"))
            userRepository.deleteAll();
    }

    public User updateUser(User user, DetailsChangeRequest request)
    {
        if (request.getNewPassword() != null)
        {

            System.out.println(passwordEncoder.encode(request.getOldPassword()));
            System.out.println(user.getPassword());

            try
            {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),request.getOldPassword()));
            }
            catch (Exception e)
            {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Old password doesn't match");
            }



            try
            {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),request.getNewPassword()));
                user.setPassword(null);
            }
            catch (Exception e)
            {
                user = user.withPassword(passwordEncoder.encode(request.getNewPassword()));
            }
            if (user.getPassword()== null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"New password cannot equal your new password");

        }
        if (request.getNewPhotoB64() != null)
        {
            String photoID = photoService.addPhoto(request.getNewPhotoB64());
            user = user.withImageID(photoID);
        }

        return userRepository.save(user);
    }
}
