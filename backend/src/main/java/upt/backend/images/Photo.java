package upt.backend.images;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("images")
@Data

@AllArgsConstructor
@NoArgsConstructor
public class Photo {
    @Id
    String id;
    private byte[] image;
}
