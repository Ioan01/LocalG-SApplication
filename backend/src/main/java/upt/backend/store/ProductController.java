package upt.backend.store;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
            @RequestHeader("Token")String token,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam() String jsonString)
    {
        Product product = Product.productFromJson(jsonString);
        Optional<User> user = userService.getUser(tokenService.getAudience(token));

        AtomicReference<ResponseEntity<Product>> entity = new AtomicReference<>();

        user.ifPresentOrElse(user1 -> {
            product.sellerId = user1.getId();

            try{
                product.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));
            }
            catch (Exception e){
                product.setImage(null);
                //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not encode image, maximum image size is 1MB.");
            }

            entity.set(new ResponseEntity<>(productService.addProduct(product), HttpStatus.OK));
        },()-> {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Could not find user " + tokenService.getAudience(token));
        });

        return entity.get();
    }

    @GetMapping("/get-page")
    ResponseEntity<String> getProducts(
            @RequestHeader("Token")String token,
            @RequestBody()Map<String, Integer> request){
        try{
            if(!request.containsKey("page"))
                request.put("page", 0);
            if(!request.containsKey("size"))
                request.put("size", 2);
            Page<Product> pageProducts = productService.getPage(request.get("page"), request.get("size"));
//*
            JsonObject response = new JsonObject();
            response.add("products", gson.toJsonTree(pageProducts.getContent()));
            response.addProperty("currentPage", pageProducts.getNumber());
            response.addProperty("totalItems", pageProducts.getTotalElements());
            response.addProperty("totalPages", pageProducts.getTotalPages());
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);//*/
            /*
            Map<String, Object> response = new HashMap<>();
            response.put("products", pageProducts.getContent());
            response.put("currentPage", pageProducts.getNumber());
            response.put("totalItems", pageProducts.getTotalElements());
            response.put("totalPages", pageProducts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);//*/

        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
