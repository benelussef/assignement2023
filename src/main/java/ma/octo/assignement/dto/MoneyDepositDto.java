package ma.octo.assignement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyDepositDto {
    private BigDecimal montant;

    private Date dateExecution;

    private String nomPrenomEmetteur;

    private String nrCompteBeneficiaire;

    private String motifDeposit;
}
