package upt.backend.store;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filter
{
    @With
    int page;
    @With
    int pageSize; // of page
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

}
