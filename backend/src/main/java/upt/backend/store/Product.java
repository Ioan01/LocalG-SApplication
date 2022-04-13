package upt.backend.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import net.minidev.json.annotate.JsonIgnore;
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
    private String name;
    @With
    private float price;

    //add binary image variable
}
