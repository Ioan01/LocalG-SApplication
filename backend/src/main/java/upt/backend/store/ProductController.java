package upt.backend.store;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upt.backend.authentication.TokenService;
import upt.backend.authentication.User;
import upt.backend.authentication.UserService;

import java.security.Provider;
import java.util.Arrays;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    ProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(@RequestHeader("Token")String token, @RequestParam MultipartFile image, @RequestParam() String jsonString)
    {
        Product product = Product.productFromJson(jsonString);
        Optional<User> user = userService.getUser(tokenService.getAudience(token));
        if(user.isEmpty())
            return new ResponseEntity<>("Could not identify provider", HttpStatus.NOT_FOUND);

        try{
            product.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
        }

        product.sellerId = user.get().getId();
        productService.addProduct(product);

        return new ResponseEntity<String>(user.get().getUsername() + " is selling " + product.getName(), HttpStatus.OK);
    }
}
