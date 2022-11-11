package ma.octo.assignement.service.implementation;

import lombok.AllArgsConstructor;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.UtilisateurDto;
import ma.octo.assignement.exceptions.UtilisateurNonExistantException;
import ma.octo.assignement.mapper.UtilisateurMapper;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.service.UtilisateurService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    public UtilisateurDto getUtilisateurByUsername(String username) throws UtilisateurNonExistantException {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username);
        UtilisateurDto utilisateurDto = UtilisateurMapper.entityToDto(utilisateur);
        if (utilisateurDto == null) {
            throw new UtilisateurNonExistantException();
        }
        return utilisateurDto;
    }

    @Override
    public Utilisateur AddUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public List<UtilisateurDto> loadAllUtilisateur() throws UtilisateurNonExistantException {
        List<Utilisateur> allUsers = utilisateurRepository.findAll();
        List<UtilisateurDto> response = allUsers.stream().map(UtilisateurMapper::entityToDto).collect(Collectors.toList());
        if (allUsers.isEmpty()) {
            throw new UtilisateurNonExistantException();
        } else {
            return response;
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return utilisateurRepository.existsUtilisateurByUsername(username);
    }
}
