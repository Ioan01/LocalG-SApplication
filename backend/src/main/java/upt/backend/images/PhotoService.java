package upt.backend.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;

@Service
public class PhotoService {
    @Autowired
    PhotoRepository photoRepository;

    public String addPhoto(String image64){
        Photo photo = new Photo();
        photo.setImage(Base64.getDecoder().decode(image64));
        photo.setHash(Arrays.hashCode(photo.getImage()));
        return photoRepository.save(photo).getId();
    }

    public Photo getPhotoById(String id){
        return photoRepository.getById(id);
    }

    public Photo getPhotoByB64(String imageB64){

        return photoRepository.getByHash(Arrays.hashCode(Base64.getDecoder().decode(imageB64)));
    }
}
