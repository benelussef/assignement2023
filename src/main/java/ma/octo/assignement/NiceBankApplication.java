package ma.octo.assignement;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.MoneyDepositDto;
import ma.octo.assignement.mapper.TransferMapper;
import ma.octo.assignement.service.CompteService;
import ma.octo.assignement.service.MoneyDepositService;
import ma.octo.assignement.service.TransferService;
import ma.octo.assignement.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class NiceBankApplication implements CommandLineRunner {
	@Autowired
	private CompteService compteService;
	@Autowired
	private UtilisateurService utilisateurService;
	@Autowired
	private TransferService transferService;

	@Autowired
	private MoneyDepositService moneyDepositService;


	public static void main(String[] args) {
		SpringApplication.run(NiceBankApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		Utilisateur utilisateur1 = new Utilisateur();
		utilisateur1.setUsername("user1");
		utilisateur1.setLastName("last1");
		utilisateur1.setFirstName("first1");
		utilisateur1.setGender("Male");
		utilisateurService.AddUtilisateur(utilisateur1);


		Utilisateur utilisateur2 = new Utilisateur();
		utilisateur2.setUsername("user2");
		utilisateur2.setLastName("last2");
		utilisateur2.setFirstName("first2");
		utilisateur2.setGender("Female");
		utilisateurService.AddUtilisateur(utilisateur2);

		Compte compte1 = new Compte();
		compte1.setNrCompte("010000A000001000");
		compte1.setRib("RIB1");
		compte1.setSolde(BigDecimal.valueOf(200000L));
		compte1.setUtilisateur(utilisateur1);
		compteService.AddCompte(compte1);

		Compte compte2 = new Compte();
		compte2.setNrCompte("010000B025001000");
		compte2.setRib("RIB2");
		compte2.setSolde(BigDecimal.valueOf(140000L));
		compte2.setUtilisateur(utilisateur2);
		compteService.AddCompte(compte2);

		Transfer v = new Transfer();
		v.setMontantTransfer(BigDecimal.TEN);
		v.setCompteBeneficiaire(compte2);
		v.setCompteEmetteur(compte1);
		v.setDateExecution(new Date());
		v.setMotifTransfer("Assignment 2021");

		transferService.createTransaction(TransferMapper.entityToDto(v));


		Transfer t = new Transfer();
		t.setMontantTransfer(BigDecimal.TEN);
		t.setCompteBeneficiaire(compte1);
		t.setCompteEmetteur(compte2);
		t.setDateExecution(new Date());
		t.setMotifTransfer("Assignment 2021");

		transferService.createTransaction(TransferMapper.entityToDto(t));

		MoneyDepositDto moneyDepositDto = MoneyDepositDto.builder()
				.nomPrenomEmetteur("benelfakir youssef")
				.nrCompteBeneficiaire("010000B025001000")
				.montant(BigDecimal.valueOf(100))
				.motifDeposit("Motif")
				.dateExecution(new Date())
				.build();

		moneyDepositService.createTransaction(moneyDepositDto);
	}
}
