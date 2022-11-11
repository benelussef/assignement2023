package ma.octo.assignement.web.controller;

import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.dto.MoneyDepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.MoneyDepositNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.mapper.MoneyDepositMapper;
import ma.octo.assignement.service.MoneyDepositService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deposits")
public class MoneyDepositController {
    private MoneyDepositService moneyDepositService;

    public MoneyDepositController(MoneyDepositService moneyDepositService) {
        this.moneyDepositService = moneyDepositService;
    }

    @GetMapping("/getAllDeposits")
    public ResponseEntity<List<MoneyDepositDto>> loadAll() throws MoneyDepositNonExistantException {
        List<MoneyDepositDto> response = moneyDepositService.loadAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/executeDeposit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MoneyDepositDto> createTransaction(@RequestBody MoneyDepositDto moneyDepositDto)
            throws CompteNonExistantException, TransactionException {
        MoneyDepositDto response = moneyDepositService.createTransaction(moneyDepositDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoneyDepositDto> getMoneyDeposit(@PathVariable Long id) throws MoneyDepositNonExistantException {
        MoneyDeposit moneyDeposit  = moneyDepositService.getMoneyDeposit(id);
        MoneyDepositDto response = MoneyDepositMapper.entityToDto(moneyDeposit);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
