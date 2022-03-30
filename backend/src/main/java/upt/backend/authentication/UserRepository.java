package upt.backend.authentication;

import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface UserRepository extends MongoRepository<User,String>
{
    User findUserByUsername(@NonNull String username);
}
