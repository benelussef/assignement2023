package ma.octo.assignement.service;

import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.exceptions.TransferNonExistantException;

import java.util.List;

public interface TransferService {
    List<TransferDto> loadAll() throws TransferNonExistantException;
    Transfer createTransaction(TransferDto TransferDto) throws TransactionException, CompteNonExistantException, SoldeDisponibleInsuffisantException;
    Transfer getTransfer(Long id) throws TransferNonExistantException;
}
