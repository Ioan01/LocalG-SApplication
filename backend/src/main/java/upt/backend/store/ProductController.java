package upt.backend.store;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import upt.backend.authentication.TokenService;
import upt.backend.authentication.User;
import upt.backend.authentication.UserService;

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

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestHeader("Token")String token, @RequestParam MultipartFile image, @RequestParam() String jsonString)
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
                //throw new ResponseStatusException("Image malformed",HttpStatus.BAD_REQUEST);
            }

            entity.set(new ResponseEntity<>(productService.addProduct(product), HttpStatus.OK));;
        },()-> {throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);});
        return entity.get();
    }
}
