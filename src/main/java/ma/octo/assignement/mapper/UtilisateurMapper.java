package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.UtilisateurDto;

public class UtilisateurMapper {
    private static UtilisateurDto utilisateurDto;
    private static Utilisateur utilisateur;

    //transferer Utilisateur a UtilisateurDto

    public static UtilisateurDto entityToDto(Utilisateur utilisateur) {
        utilisateurDto = UtilisateurDto.builder()
                .birthdate(utilisateur.getBirthdate())
                .firstName(utilisateur.getFirstName())
                .gender(utilisateur.getGender())
                .lastName(utilisateur.getLastName())
                .username(utilisateur.getUsername())
                .build();
        return utilisateurDto;
    }

    //transferer UtilisateurDto a Utilisateur
    public static Utilisateur dtoToEntity(UtilisateurDto utilisateurDto){
        utilisateur = Utilisateur.builder()
                .birthdate(utilisateurDto.getBirthdate())
                .firstName(utilisateurDto.getFirstName())
                .gender(utilisateurDto.getGender())
                .lastName(utilisateurDto.getLastName())
                .username(utilisateurDto.getUsername())
                .build();
        return utilisateur;
    }
}
