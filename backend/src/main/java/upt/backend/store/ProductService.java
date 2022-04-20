package upt.backend.store;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product addProduct(@NonNull Product product){
        return productRepository.save(product);
    }

    public Page<Product> getPage(int page, int size){
        Pageable paging = PageRequest.of(page, size);

        return productRepository.findAllBy(paging);
    }

    public Page<Product> getFilteredPage(Filter filter)
    {
        Pageable paging = PageRequest.of(filter.getPage(), filter.getSize());

        return productRepository.getByFilter(
               filter.getTags(), filter.getName(), filter.getMinPrice(), filter.getMaxPrice(), filter.getType(), paging);

    }
}
