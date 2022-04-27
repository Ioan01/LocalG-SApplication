package upt.backend.images;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface PhotoRepository extends MongoRepository<Photo, String> {
    Photo getById(String id);
}
