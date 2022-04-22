package upt.backend.store;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import upt.backend.authentication.TokenService;
import upt.backend.authentication.User;
import upt.backend.authentication.UserService;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    ProductService productService;

    @Autowired
    Gson gson;

    @PostMapping("/add")
    ResponseEntity<Product> addProduct(
            @RequestHeader("Authorization")String token,
            @RequestBody Product product)
    {
        Optional<User> user = userService.getUser(tokenService.getAudience(token));
        AtomicReference<ResponseEntity<Product>> entity = new AtomicReference<>();

        user.ifPresentOrElse(user1 -> {
            product.sellerId = user1.getId();
            entity.set(new ResponseEntity<>(productService.addProduct(product), HttpStatus.OK));
        },()-> {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Could not find user " + tokenService.getAudience(token));
        });

        return entity.get();
    }

    @GetMapping("/get-page")
    ResponseEntity<ProductsEntity> getProducts(@RequestParam int page,@RequestParam int pageSize){
        try{

            Page<Product> pageProducts = productService.getPage(page, pageSize);
//*
            ProductsEntity products = new ProductsEntity(pageProducts);

            return new ResponseEntity<>(products, HttpStatus.OK);//*/

        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-filtered-page")
    ResponseEntity<ProductsEntity> getFilteredProducts(
            @RequestBody Filter request){
        try{
            //HANDLE NULL PARAMETERS
            //HANDLE NO TAGS (in productrepo)
            Page<Product> pageProducts = productService.getFilteredPage(request);
            ProductsEntity products = new ProductsEntity(pageProducts);
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}


