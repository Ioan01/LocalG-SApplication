package upt.backend.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;



@AllArgsConstructor
@NoArgsConstructor

public class ProductsEntity
{
    @JsonProperty
    private long totalItems;
    @JsonProperty
    private long totalPages;
    @JsonProperty
    private int currentPage;
    @JsonProperty
    private List<Product> products;



    public ProductsEntity(Page<Product> pageProducts)
    {
        products = pageProducts.getContent();
        currentPage = pageProducts.getNumber();
        totalItems = pageProducts.getTotalElements();
        totalPages = pageProducts.getTotalPages();
    }
}
