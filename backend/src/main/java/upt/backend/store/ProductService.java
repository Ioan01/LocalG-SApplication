package upt.backend.store;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import upt.backend.images.PhotoService;

import java.util.Date;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PhotoService photoService;

    public Product addProduct(@NonNull Product product){
        if(!product.getImage().isEmpty())
            product.setImage(photoService.addPhoto(product.getImage()));
        product.setTime(new Date());
        return productRepository.save(product);
    }

    public Page<Product> getPage(int page, int size){
        Pageable paging = PageRequest.of(page, size);

        return productRepository.findAllBy(paging);
    }

    public Page<Product> getFilteredPage(Filter filter)
    {
        Pageable paging = PageRequest.of(filter.getPage(), filter.getSize());

        if(filter.getTags().isEmpty())
            return productRepository.getByFilterNoTags(
                    filter.getName(), filter.getMinPrice(), filter.getMaxPrice(), filter.getType(), paging);
        else
            return productRepository.getByFilter(
               filter.getTags(), filter.getName(), filter.getMinPrice(), filter.getMaxPrice(), filter.getType(), paging);

    }
}