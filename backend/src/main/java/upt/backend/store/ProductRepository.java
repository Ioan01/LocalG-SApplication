package upt.backend.store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    Page<Product> findAllBy(Pageable pageable);
    
    @Query("{'tags': {$all: ?0}, 'name': {$regex: ?1}, 'price': {$gt: ?2}, 'price': {$lt: ?3}, 'type': {$eq: ?4}}")
    Page<Product> getByFilter(List<String> tags, String name, int minPrice, int maxPrice, String type, Pageable pageable);

    @Query("{'name': {$regex: ?0}, 'price': {$gt: ?1}, 'price': {$lt: ?2}, 'type': {$eq: ?3}}")
    Page<Product> getByFilterNoTags(String name, int minPrice, int maxPrice, String type, Pageable pageable);

    Product removeByIdAndSellerId(String id, String sellerId);
}
