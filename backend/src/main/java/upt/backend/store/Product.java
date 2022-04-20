package upt.backend.store;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import net.minidev.json.annotate.JsonIgnore;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

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
    @JsonIgnore
    private String image;
    @JsonIgnore
    private ArrayList<String> tags;

    public Product(String name, int price, String description, String type, String image, ArrayList<String> tags) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
        this.image = image;
        this.tags = tags;
    }

    public static Product productFromJson(String jsonString){
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(jsonString);
        return gson.fromJson(json, Product.class);
    }
}
