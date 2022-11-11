package ma.octo.assignement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDto {
    private String username;

    private String gender;

    private String lastName;

    private String firstName;

    private Date birthdate;

}
