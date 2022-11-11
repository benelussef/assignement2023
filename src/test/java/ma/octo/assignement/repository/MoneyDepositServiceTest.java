package ma.octo.assignement.repository;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.MoneyDepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.MoneyDepositNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.mapper.MoneyDepositMapper;
import ma.octo.assignement.service.AuditService;
import ma.octo.assignement.service.CompteService;
import ma.octo.assignement.service.MoneyDepositService;
import ma.octo.assignement.service.implementation.AuditServiceImpl;
import ma.octo.assignement.service.implementation.CompteServiceImpl;
import ma.octo.assignement.service.implementation.MoneyDepositServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MoneyDepositServiceTest {
    @Mock
    private MoneyDepositRepository moneyDepositRepository;

    @Mock
    private CompteServiceImpl compteService;

    @Mock
    private AuditServiceImpl auditService;

    @InjectMocks
    private MoneyDepositServiceImpl moneyDepositService;

    private final Date now = new Date();

    @Test
    void canGetMoneyDeposit() {
        // given
        Long id = 1L;
        MoneyDepositDto moneyDepositDto = MoneyDepositDto.builder()
                .nomPrenomEmetteur("benelfakir youssef")
                .montant(BigDecimal.valueOf(10))
                .motifDeposit("Motif")
                .dateExecution(now)
                .nrCompteBeneficiaire("Nr1").build();

        MoneyDeposit moneyDeposit = MoneyDeposit.builder()
                .compteBeneficiaire(new Compte())
                .motifDeposit("Motif")
                .nomPrenomEmetteur("benelfakir youssef")
                .dateExecution(now)
                .montant(BigDecimal.valueOf(10)).build();

        try(MockedStatic<MoneyDepositMapper> mockStatic = Mockito.mockStatic(MoneyDepositMapper.class)) {
            //when
            mockStatic.when((MockedStatic.Verification) MoneyDepositMapper.entityToDto(moneyDeposit)).thenReturn(moneyDepositDto);
            when(moneyDepositRepository.findById(id)).thenReturn(Optional.of(moneyDeposit));

            MoneyDepositDto result = MoneyDepositMapper.entityToDto(moneyDepositService.getMoneyDeposit(id));

            //then
            assertEquals("benelfakir youssef", result.getNomPrenomEmetteur());
            assertEquals(BigDecimal.TEN, result.getMontant());
            assertEquals("Motif", result.getMotifDeposit());
            assertEquals("Nr1", result.getNrCompteBeneficiaire());
            assertEquals(now, result.getDateExecution());

        } catch (MoneyDepositNonExistantException e) {
            e.printStackTrace();
        }
    }
    @Test
    void getMoneyDepositWillThrowExceptionWhenIdNotFound() {
        // given
        Long id = 1L;
        MoneyDepositDto moneyDepositDto = MoneyDepositDto.builder()
                .nomPrenomEmetteur("benelfakir youssef")
                .montant(BigDecimal.TEN)
                .motifDeposit("Motif")
                .dateExecution(now)
                .nrCompteBeneficiaire("Nr1").build();

        MoneyDeposit moneyDeposit = MoneyDeposit.builder()
                .compteBeneficiaire(new Compte())
                .motifDeposit("Motif")
                .nomPrenomEmetteur("benelfakir youssef")
                .dateExecution(now)
                .montant(BigDecimal.TEN).build();

        try(MockedStatic<MoneyDepositMapper> mockStatic = Mockito.mockStatic(MoneyDepositMapper.class)) {
            //when
            mockStatic.when((MockedStatic.Verification) MoneyDepositMapper.entityToDto(moneyDeposit)).thenReturn(moneyDepositDto);
            when(moneyDepositRepository.findById(id)).thenReturn(Optional.empty());

            //then
            assertThatThrownBy( () -> moneyDepositService.getMoneyDeposit(id))
                    .isInstanceOf(MoneyDepositNonExistantException.class);
        }
    }
    @Test
    void createTransactionWillThrowWhenMontantIsNull(){
        // given
        MoneyDepositDto moneyDepositDto = MoneyDepositDto.builder()
                .nomPrenomEmetteur("benelfakir youssef")
                .montant(BigDecimal.ZERO)
                .motifDeposit("Motif")
                .dateExecution(now)
                .nrCompteBeneficiaire("Nr1").build();

        // when
        // then
        assertThatThrownBy( () -> moneyDepositService.createTransaction(moneyDepositDto))
                .isInstanceOf(TransactionException.class)
                .hasMessageContaining("Montant vide");
    }
    @Test
    void createTransactionWillThrowWhenMontantIsGreaterThanMontantMax() {
        // given
        MoneyDepositDto moneyDepositDto = MoneyDepositDto.builder()
                .nomPrenomEmetteur("benelfakir youssef")
                .montant(new BigDecimal("100001"))
                .motifDeposit("Motif")
                .dateExecution(now)
                .nrCompteBeneficiaire("Nr1").build();
        // when
        // then
        assertThatThrownBy( () -> moneyDepositService.createTransaction(moneyDepositDto))
                .isInstanceOf(TransactionException.class)
                .hasMessageContaining("Montant maximal de MoneyDeposit dépassé");
    }
    @Test
    void createTransactionWillThrowWhenMontantIsLessThanMontantMin() {
        // given
        MoneyDepositDto moneyDepositDto = MoneyDepositDto.builder()
                .nomPrenomEmetteur("benelfakir youssef")
                .montant(new BigDecimal("9"))
                .motifDeposit("Motif")
                .dateExecution(now)
                .nrCompteBeneficiaire("Nr1").build();

        // when
        // then
        assertThatThrownBy( () -> moneyDepositService.createTransaction(moneyDepositDto))
                .isInstanceOf(TransactionException.class)
                .hasMessageContaining("Montant minimal de MoneyDeposit non atteint");
    }
    @Test
    void canGetmoneyDepositWhenmoneyDepositisAdded() {
        // given
        Long id = 1L;
        MoneyDepositDto moneyDepositDto = MoneyDepositDto.builder()
                .nomPrenomEmetteur("benelfakir youssef")
                .montant(BigDecimal.TEN)
                .motifDeposit("Motif")
                .dateExecution(now)
                .nrCompteBeneficiaire("Nr1").build();

        MoneyDeposit moneyDeposit = MoneyDeposit.builder()
                .compteBeneficiaire(new Compte())
                .motifDeposit("Motif")
                .nomPrenomEmetteur("benelfakir youssef")
                .dateExecution(now)
                .montant(BigDecimal.TEN).build();

        Compte compte = Compte.builder()
                .nrCompte("010000B025001000")
                .rib("RIB2")
                .solde(BigDecimal.valueOf(140000L))
                .utilisateur(new Utilisateur()).build();

        try(MockedStatic<MoneyDepositMapper> mockStatic = Mockito.mockStatic(MoneyDepositMapper.class)) {
            //when
            mockStatic.when((MockedStatic.Verification) MoneyDepositMapper.entityToDto(moneyDeposit)).thenReturn(moneyDepositDto);
            mockStatic.when((MockedStatic.Verification) MoneyDepositMapper.dtoToEntity(moneyDepositDto)).thenReturn(moneyDeposit);
            when(moneyDepositRepository.save(moneyDeposit)).thenReturn(moneyDeposit);
            when(compteService.getCompteByNrCompte(anyString())).thenReturn(compte);
            MoneyDepositDto result = moneyDepositService.createTransaction(moneyDepositDto);

            //then
            assertEquals("benelfakir youssef", result.getNomPrenomEmetteur());
            assertEquals(BigDecimal.TEN, result.getMontant());
            assertEquals("Motif", result.getMotifDeposit());
            assertEquals("Nr1", result.getNrCompteBeneficiaire());
            assertEquals(now, result.getDateExecution());

        } catch (TransactionException | CompteNonExistantException e) {
            e.printStackTrace();
        }


    }
}
