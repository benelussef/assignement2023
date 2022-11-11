package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.TransferDto;

public class TransferMapper {

    private static TransferDto transferDto;
    private static Transfer transfer;


    //transferer transfer au transferDto
    public static TransferDto entityToDto(Transfer transfer) {
        transferDto = TransferDto.builder()
                .nrCompteBeneficiaire(transfer.getCompteBeneficiaire().getNrCompte())
                .nrCompteEmetteur(transfer.getCompteEmetteur().getNrCompte())
                .dateExecution(transfer.getDateExecution())
                .montantTransfer(transfer.getMontantTransfer())
                .motifTransfer(transfer.getMotifTransfer())
                .build();

        return transferDto;

    }

    //transferer transferDto au transfer
    public static Transfer dtoToEntity(TransferDto transferDto){

        Compte compteBeneficiaire = Compte.builder().nrCompte(transferDto.getNrCompteBeneficiaire()).build();
        Compte compteEmetteur = Compte.builder().nrCompte(transferDto.getNrCompteEmetteur()).build();
        transfer = Transfer.builder()
                .compteBeneficiaire(compteBeneficiaire)
                .compteEmetteur(compteEmetteur)
                .dateExecution(transferDto.getDateExecution())
                .montantTransfer(transferDto.getMontantTransfer())
                .motifTransfer(transferDto.getMotifTransfer())
                .build();
        return transfer;
    }
}
