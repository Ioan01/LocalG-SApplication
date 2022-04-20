package upt.backend.store;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Filter
{
    @With
    int page;
    @With
    int size; // of page
    @With
    String name;
    @With
    ArrayList<String> tags;
    @With
    int minPrice;
    @With
    int maxPrice;
    @With
    String type;

    public Filter(int page, int size, String name, ArrayList<String> tags, int minPrice, int maxPrice, String type) {
        this.page = page;
        this.size = size;
        this.name = name;
        this.tags = tags;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.type = type;
    }
}
