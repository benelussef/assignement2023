package ma.octo.assignement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDto {
  private BigDecimal montantTransfer;
  private Date dateExecution;
  private String nrCompteEmetteur;
  private String nrCompteBeneficiaire;
  private String motifTransfer;
}
