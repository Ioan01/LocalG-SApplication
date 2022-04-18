package upt.backend.store;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public Product addProduct(@NonNull Product product){
        return productRepository.save(product);
    }

}
