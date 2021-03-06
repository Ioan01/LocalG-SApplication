package upt.backend.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import net.minidev.json.annotate.JsonIgnore;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document("store")
@Data

@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @JsonIgnore
    public String id;
    @JsonIgnore
    public String sellerId;
    @With
    private String name;
    @With
    private int price;
    @With
    private String description;
    @With
    private String type;
    private String image;
    private ArrayList<String> tags;
    private Date time;

    public Product(String name, int price, String description, String type, ArrayList<String> tags) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
        this.tags = tags;
    }
}
