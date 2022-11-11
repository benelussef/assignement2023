package ma.octo.assignement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.octo.assignement.domain.Utilisateur;

import java.math.BigDecimal;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteDto {
    private String nrCompte;
    private String rib;
    private BigDecimal solde;
    private Utilisateur utilisateur;
}
