package upt.backend.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import net.minidev.json.annotate.JsonIgnore;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("store")
@Data

@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @JsonIgnore
    public String id;
    @With
    public String sellerId;
    @With
    private String name;
    @With
    private int price;
    @With
    private String description;
    @With
    private String type;
    @With
    private Binary image;

    //add binary image variable
}
