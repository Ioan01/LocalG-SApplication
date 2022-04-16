package upt.backend.user;

import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User,String>
{
    User findUserByUsername(@NonNull String username);
}
