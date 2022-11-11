package ma.octo.assignement.service;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.UtilisateurDto;
import ma.octo.assignement.exceptions.UtilisateurNonExistantException;

import java.util.List;

public interface UtilisateurService {
    UtilisateurDto getUtilisateurByUsername(String username) throws UtilisateurNonExistantException;
     Utilisateur AddUtilisateur(Utilisateur utilisateur);
    List<UtilisateurDto> loadAllUtilisateur() throws UtilisateurNonExistantException;
    boolean existsByUsername(String username);
}
