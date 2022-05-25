package upt.backend.authentication;

import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import upt.backend.authentication.User;


public interface UserRepository extends MongoRepository<User,String>
{
    User findUserByUsername(@NonNull String username);
    User deleteUserByUsername(@NonNull String username);
}
