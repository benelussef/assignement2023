package ma.octo.assignement.service.implementation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.domain.util.EventType;
import ma.octo.assignement.dto.MoneyDepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.MoneyDepositNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.mapper.MoneyDepositMapper;
import ma.octo.assignement.repository.MoneyDepositRepository;
import ma.octo.assignement.service.AuditService;
import ma.octo.assignement.service.CompteService;
import ma.octo.assignement.service.MoneyDepositService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class MoneyDepositServiceImpl implements MoneyDepositService {
    public static final BigDecimal MONTANT_MAXIMAL = BigDecimal.valueOf(10000);
    public static final BigDecimal MONTANT_MINIMAL =BigDecimal.valueOf(10);
    private final MoneyDepositRepository moneyDepositRepository;
    private final AuditService auditService;
    private final CompteService compteService;
    @Override
    public List<MoneyDepositDto> loadAll() throws MoneyDepositNonExistantException {
        List<MoneyDeposit> allMoneyDeposit = moneyDepositRepository.findAll();
        List<MoneyDepositDto> response = allMoneyDeposit.stream().map(MoneyDepositMapper::entityToDto).collect(Collectors.toList());
        if (allMoneyDeposit.isEmpty()) {
            throw new MoneyDepositNonExistantException("Aucun deposit trouvé");
        } else {
            return response;
        }
    }
    public boolean ifMontantVide(BigDecimal b){
        return b.compareTo(BigDecimal.valueOf(0)) == 0 || b == null;
    }
    @Override
    public MoneyDepositDto createTransaction(MoneyDepositDto moneyDepositDto) throws TransactionException, CompteNonExistantException {
        if(moneyDepositDto.getNrCompteBeneficiaire() == null)
            throw new TransactionException("Pas de compte!!");

        Compte compteBeneficiaire = compteService.getCompteByNrCompte(moneyDepositDto.getNrCompteBeneficiaire());


        if (ifMontantVide(moneyDepositDto.getMontant())) {
            log.error("Montant vide");
            throw new TransactionException("Montant vide");
        }
        else if (moneyDepositDto.getMontant().compareTo(MONTANT_MINIMAL) < 0 ) {
            log.error("Montant minimal de MoneyDeposit non atteint");
            throw new TransactionException("Montant minimal de MoneyDeposit non atteint");
        }
        else if (moneyDepositDto.getMontant().compareTo(MONTANT_MAXIMAL) > 0 ) {
            log.error("Montant maximal de MoneyDeposit dépassé");
            throw new TransactionException("Montant maximal de MoneyDeposit dépassé");
        }

        if (moneyDepositDto.getMotifDeposit().length() == 0 ) {
            log.error("Motif vide");
            throw new TransactionException("Motif vide");
        }

        if (moneyDepositDto.getNomPrenomEmetteur() == null || moneyDepositDto.getNomPrenomEmetteur() .isEmpty()) {
            log.error("Le nom et le prenom de l'emetteur est vide");
            throw new TransactionException("Le nom et le prenom de l'emetteur est vide");
        }

        compteBeneficiaire.setSolde(compteBeneficiaire.getSolde().add(moneyDepositDto.getMontant()));

        MoneyDeposit MoneyDeposit = MoneyDepositMapper.dtoToEntity(moneyDepositDto);
        MoneyDeposit.setCompteBeneficiaire(compteBeneficiaire);
        MoneyDeposit.setDateExecution(new Date());
        moneyDepositRepository.save(MoneyDeposit);

        auditService.addAudit("MoneyDeposit depuis " + MoneyDeposit.getNomPrenomEmetteur() + " vers " + MoneyDeposit
                .getCompteBeneficiaire().getNrCompte() + " d'un montant de " + MoneyDeposit.getMontant()
                .toString(), EventType.DEPOSIT);



        return  MoneyDepositMapper.entityToDto(MoneyDeposit);
    }

    @Override
    public MoneyDeposit getMoneyDeposit(Long id) throws MoneyDepositNonExistantException {
        return moneyDepositRepository.findById(id).orElseThrow(() -> new MoneyDepositNonExistantException("Le Deposit n'existe pas"));
    }
}
