package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.dto.CompteDto;

public class CompteMapper {
    private static CompteDto compteDto;
    private static Compte compte;

    //transferer Compte a CompteDto
    public static CompteDto entityToDto(Compte compte) {
        compteDto = CompteDto.builder()
                .nrCompte(compte.getNrCompte())
                .rib(compte.getRib())
                .solde(compte.getSolde())
                .utilisateur(compte.getUtilisateur())
                .build();
        return compteDto;
    }
    //transferer CompteDto a Compte
    public static Compte dtoToEntity(CompteDto compteDto){
        compte = Compte.builder()
                .nrCompte(compteDto.getNrCompte())
                .rib(compteDto.getRib())
                .utilisateur(compteDto.getUtilisateur())
                .solde(compteDto.getSolde())
                .build();
        return compte;
    }
}
