package ma.octo.assignement.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DEP")
public class MoneyDeposit {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(precision = 16, scale = 2, nullable = false)
  private BigDecimal montant;         //replacer nomenclature Montant par montant

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateExecution;

  @Column
  private String nomPrenomEmetteur;   //replacer nomenclature nom_prenom_emetteur par NomPrenomEmetteut

  @ManyToOne
  private Compte compteBeneficiaire;

  @Column(length = 200)
  private String motifDeposit;

}
