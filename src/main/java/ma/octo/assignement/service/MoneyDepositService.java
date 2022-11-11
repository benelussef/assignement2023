package ma.octo.assignement.service;

import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.dto.MoneyDepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.MoneyDepositNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;

import java.util.List;

public interface MoneyDepositService {
    List<MoneyDepositDto> loadAll() throws MoneyDepositNonExistantException;
    MoneyDepositDto createTransaction(MoneyDepositDto MoneyDepositDto) throws TransactionException, CompteNonExistantException;
    MoneyDeposit getMoneyDeposit(Long id) throws MoneyDepositNonExistantException;
}
