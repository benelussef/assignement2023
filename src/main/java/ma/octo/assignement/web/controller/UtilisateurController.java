package ma.octo.assignement.web.controller;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.UtilisateurDto;
import ma.octo.assignement.exceptions.UtilisateurNonExistantException;
import ma.octo.assignement.mapper.UtilisateurMapper;
import ma.octo.assignement.service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {
    private UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/saveUtilisateur")
    public ResponseEntity<Utilisateur> saveUtilisateur(@RequestBody UtilisateurDto utilisateurDto){
        return new ResponseEntity<>(utilisateurService.AddUtilisateur(UtilisateurMapper.dtoToEntity(utilisateurDto)), HttpStatus.CREATED);
    }

    @GetMapping("/allUtiilisateurs")
    public ResponseEntity<List<UtilisateurDto>> getAllUtilisateurs() throws UtilisateurNonExistantException {
        return ResponseEntity.ok(utilisateurService.loadAllUtilisateur());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UtilisateurDto> getUtilisateurByUsername(@PathVariable String username) throws UtilisateurNonExistantException {
        return ResponseEntity.ok(utilisateurService.getUtilisateurByUsername(username));
    }
}
