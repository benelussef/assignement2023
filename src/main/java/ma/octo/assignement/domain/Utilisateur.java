package ma.octo.assignement.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UTILISATEUR")
public class Utilisateur implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(length = 10, nullable = false, unique = true)
  private String username;

  @Column(length = 10, nullable = false)
  private String gender;

  @Column(length = 60, nullable = false)
  private String lastName;                 //remplacer nomenclature lastname par lastName

  @Column(length = 60, nullable = false)
  private String firstName;               //remplacer nomenclature firstname par firstName

  @Temporal(TemporalType.DATE)
  private Date birthdate;

}
