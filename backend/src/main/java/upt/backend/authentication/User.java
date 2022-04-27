package upt.backend.authentication;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document("users")
@Data

@AllArgsConstructor
@NoArgsConstructor
public class User
{
    @Id
    @JsonIgnore
    public String id;


    @Indexed(unique = true)
    @With
    private String username;
    @With
    private String password;

    @With
    private String role;

    @JsonIgnore
    private int money;

    public User(String username, String password,String role)
    {
        this.username = username;
        this.password = password;
        this.role = role;
        money = 0;
    }
}
