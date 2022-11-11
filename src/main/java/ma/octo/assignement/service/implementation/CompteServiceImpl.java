package ma.octo.assignement.service.implementation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.dto.CompteDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.mapper.CompteMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.service.CompteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CompteServiceImpl implements CompteService {
    private final CompteRepository compteRepository;

    @Override
    public Compte getCompteByNrCompte(String nrCompte) throws CompteNonExistantException {
        Compte compte = compteRepository.findByNrCompte(nrCompte);
        if (compte == null) {
            throw new CompteNonExistantException();
        }
        else {
            return compte;
        }
    }

    @Override
    public Compte AddCompte(Compte compte) {
        return compteRepository.save(compte);
    }

    @Override
    public List<CompteDto> loadAllCompte() throws CompteNonExistantException {
        List<Compte> comptes = compteRepository.findAll();
        List<CompteDto> response = comptes.stream().map(CompteMapper::entityToDto).collect(Collectors.toList());
        if (comptes.isEmpty()) {
            throw new CompteNonExistantException();
        }
        else {
            return response;
        }
    }
}
