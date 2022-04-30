package upt.backend.images;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;

@RestController
public class PhotoController {
    @Autowired
    PhotoService photoService;

    @PostMapping("/developer/add-image")
    @ResponseBody
    ResponseEntity<String> addImage(@RequestBody String image64){
        try{
            return new ResponseEntity<>(photoService.addPhoto(image64), HttpStatus.OK);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(
            value = "/image",
            produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody byte[] getImage(@RequestParam("id") String id){
        Photo photo = photoService.getPhotoById(id);
        return photo.getImage();
    }
}
