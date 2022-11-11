package ma.octo.assignement.web.controller;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.dto.CompteDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.mapper.CompteMapper;
import ma.octo.assignement.service.CompteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comptes")
public class CompteController {

    private CompteService compteService;

    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }
    @PostMapping
    public ResponseEntity<Compte> saveCompte(@RequestBody CompteDto compteDto){
        return new ResponseEntity<>(compteService.AddCompte(CompteMapper.dtoToEntity(compteDto)), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<CompteDto>> getAllCompte() throws CompteNonExistantException {
        return ResponseEntity.ok(compteService.loadAllCompte());
    }
    @GetMapping("/{NrCompte}")
    public ResponseEntity<Compte> getCompteByNrCompte(@PathVariable String NrCompte) throws CompteNonExistantException {
        return ResponseEntity.ok(compteService.getCompteByNrCompte(NrCompte));
    }
}
