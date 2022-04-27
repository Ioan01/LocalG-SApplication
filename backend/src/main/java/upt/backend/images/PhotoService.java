package upt.backend.images;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class PhotoService {
    @Autowired
    PhotoRepository photoRepository;

    public String addPhoto(String image64){
        Photo photo = new Photo();
        photo.setImage(Base64.getDecoder().decode(image64));
        return photoRepository.save(photo).getId();
    }

    public Photo getPhoto(String id){
        return photoRepository.getById(id);
    }
}
