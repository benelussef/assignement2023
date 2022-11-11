package ma.octo.assignement.service.implementation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.domain.util.EventType;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.exceptions.TransferNonExistantException;
import ma.octo.assignement.mapper.TransferMapper;
import ma.octo.assignement.repository.TransferRepository;
import ma.octo.assignement.service.AuditService;
import ma.octo.assignement.service.CompteService;
import ma.octo.assignement.service.TransferService;
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
public class TransferServiceImpl implements TransferService {

    public static final BigDecimal MONTANT_MAXIMAL = BigDecimal.valueOf(10000);
    public static final BigDecimal MONTANT_MINIMAL = BigDecimal.valueOf(10);

    private final TransferRepository transferRepository;
    private final CompteService compteService;
    private final AuditService auditService;

    @Override
    public List<TransferDto> loadAll() throws TransferNonExistantException {
        List<Transfer> allTransfers = transferRepository.findAll();
        List<TransferDto> response = allTransfers.stream().map(TransferMapper::entityToDto).collect(Collectors.toList());
        if (allTransfers.isEmpty()) {
            throw new TransferNonExistantException("Aucun transfer trouvé");
        } else {
            return response;
        }
    }

    @Override
    public Transfer createTransaction(TransferDto TransferDto) throws TransactionException, CompteNonExistantException, SoldeDisponibleInsuffisantException {
        Compte compteEmetteur = compteService.getCompteByNrCompte(TransferDto.getNrCompteEmetteur());
        Compte compteBeneficiaire = compteService.getCompteByNrCompte(TransferDto.getNrCompteBeneficiaire());
        if (TransferDto.getMontantTransfer() == null || TransferDto.getMontantTransfer().compareTo(BigDecimal.valueOf(0)) == 0) {
            log.error("Montant vide !");
            throw new TransactionException("Montant vide !");
        }
        else if (TransferDto.getMontantTransfer().compareTo( MONTANT_MINIMAL ) < 0 ) {
            log.error("Montant minimal de Transfer non atteint");
            throw new TransactionException("Montant minimal de Transfer non atteint");
        }
        else if (TransferDto.getMontantTransfer().compareTo(MONTANT_MAXIMAL) > 0) {
            log.error("Montant maximal de Transfer dépassé");
            throw new TransactionException("Montant maximal de Transfer dépassé");
        }
        if (TransferDto.getMotifTransfer().length() == 0 ) {
            log.error("Motif vide");
            throw new TransactionException("Motif vide");
        }
        if (compteEmetteur.getSolde().compareTo(TransferDto.getMontantTransfer()) < 0 ) {
            log.error("Solde insuffisant !!");
            throw new SoldeDisponibleInsuffisantException("Solde insuffisant !!");
        }

        compteEmetteur.setSolde(compteEmetteur.getSolde().subtract(TransferDto.getMontantTransfer()));
        compteBeneficiaire.setSolde(compteBeneficiaire.getSolde().add(TransferDto.getMontantTransfer()));

        Transfer Transfer = TransferMapper.dtoToEntity(TransferDto);
        Transfer.setDateExecution(new Date());
        Transfer.setCompteBeneficiaire(compteBeneficiaire);
        Transfer.setCompteEmetteur(compteEmetteur);
        Transfer.setMontantTransfer(Transfer.getMontantTransfer());
        Transfer.setMotifTransfer(TransferDto.getMotifTransfer());

        transferRepository.save(Transfer);
        auditService.addAudit("Transfer depuis " + Transfer.getCompteEmetteur().getNrCompte()+ " vers " + Transfer
                .getCompteBeneficiaire().getNrCompte() + " d'un montant de " + Transfer.getMontantTransfer()
                .toString(), EventType.TRANSFER);
        return Transfer;
    }

    @Override
    public Transfer getTransfer(Long id) throws TransferNonExistantException {
        return transferRepository.findById(id).orElseThrow(() -> new TransferNonExistantException("Le Transfer n'existe pas"));
    }
}
