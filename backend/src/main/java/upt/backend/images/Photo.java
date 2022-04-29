package upt.backend.images;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("images")
@Data

@AllArgsConstructor
@NoArgsConstructor
public class Photo {
    @Id
    String id;

    @Indexed(unique = true)
    int hash;

    private byte[] image;
}
