package upt.backend.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor
@Data
@With
public class DetailsChangeRequest
{
    String oldPassword;
    String newPassword;
    String newPhotoB64;


}
