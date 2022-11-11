package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.dto.MoneyDepositDto;

public class MoneyDepositMapper {
    private static MoneyDepositDto moneyDepositDto;
    private static MoneyDeposit moneyDeposit;

    //transferer MoneyDeposit au MoneyDepositDto
    public static MoneyDepositDto entityToDto(MoneyDeposit moneyDeposit) {
        moneyDepositDto = MoneyDepositDto.builder()
                .motifDeposit(moneyDeposit.getMotifDeposit())
                .nrCompteBeneficiaire(moneyDeposit.getCompteBeneficiaire().getNrCompte())
                .dateExecution(moneyDeposit.getDateExecution())
                .montant(moneyDeposit.getMontant())
                .nomPrenomEmetteur(moneyDeposit.getNomPrenomEmetteur())
                .build();
        return moneyDepositDto;
    }

    //transferer MoneyDeposit au MoneyDepositDto
    public static MoneyDeposit dtoToEntity(MoneyDepositDto moneyDepositDto){
        Compte compteBeneficiaire = Compte.builder().nrCompte(moneyDepositDto.getNrCompteBeneficiaire()).build();

        moneyDeposit = MoneyDeposit.builder()
                .motifDeposit(moneyDepositDto.getMotifDeposit())
                .compteBeneficiaire(compteBeneficiaire)
                .dateExecution(moneyDepositDto.getDateExecution())
                .montant(moneyDepositDto.getMontant())
                .nomPrenomEmetteur(moneyDepositDto.getNomPrenomEmetteur())
                .build();
        return moneyDeposit;
    }
}
