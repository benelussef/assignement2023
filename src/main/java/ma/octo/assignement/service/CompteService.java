package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.dto.CompteDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;

import java.util.List;

public interface CompteService {
    Compte getCompteByNrCompte(String nrCompte) throws CompteNonExistantException;
    Compte AddCompte(Compte compte);
    List<CompteDto> loadAllCompte() throws CompteNonExistantException;

}
