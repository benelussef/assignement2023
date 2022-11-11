package ma.octo.assignement.web.controller;

import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.exceptions.TransferNonExistantException;
import ma.octo.assignement.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("/allTransfers")
    public List<TransferDto> getAllTransfers() throws TransferNonExistantException {
        return transferService.loadAll();
    }

    @PostMapping("/createTransaction")
    @ResponseStatus(HttpStatus.CREATED)
    public Transfer createTransaction(@RequestBody TransferDto transferDto)
            throws SoldeDisponibleInsuffisantException, CompteNonExistantException, TransactionException {
        return transferService.createTransaction(transferDto);
    }
    @GetMapping("/{id}")
    public Transfer getTransfer(@PathVariable Long id) throws TransferNonExistantException {
        return transferService.getTransfer(id);
    }
}
