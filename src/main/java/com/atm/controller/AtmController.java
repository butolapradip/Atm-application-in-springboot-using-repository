package com.atm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atm.model.Account;
import com.atm.model.AccountDetails;
import com.atm.model.AccountResponse;
import com.atm.model.Profile;
import com.atm.repository.AccountRepository;
import com.atm.repository.AtmDAL;
import com.atm.repository.UserRepository;
import com.atm.service.AccountGenerate;

@RestController
public class AtmController {
	@Autowired
	AccountGenerate generate;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	Account account;
	@Autowired
	AtmDAL atmdal;

	public AtmController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public AtmController(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public AtmController() {

	}

	@PostMapping(value = "/register")
	public ResponseEntity<AccountDetails> addNewUsers(@Valid @RequestBody Profile profile) {
		long accountNo = generate.accountGenerate();
		int pin = generate.pinGenerate();
		System.out.println(accountNo);
		profile.setAccountNo(accountNo);
		userRepository.save(profile);
		account.setAccountNo(accountNo);
		account.setPin(pin);
		accountRepository.save(account);
		return new ResponseEntity<AccountDetails>(new AccountDetails(account.getAccountNo(), account.getPin()),
				HttpStatus.OK);
	}

	@GetMapping(value = "/account")
	public List<Account> getAllAccount() {
		return accountRepository.findAll();
	}

	@PostMapping("/deposit-amount")
	public ResponseEntity<AccountResponse> amountDeposit(@RequestBody Account deposit) {
		String balance = atmdal.deposit(deposit.getAccountNo(), deposit.getPin(), deposit.getBalance());
		return new ResponseEntity<>(new AccountResponse(balance), HttpStatus.OK);

	}

	// @GetMapping("/withdraw-amount")
	// public ResponseEntity<AccountResponse> amountDraw(@RequestBody Account
	// withDraw) {
	// String balance = atmdal.withDraw(withDraw.getAccountNo(),
	// withDraw.getPin(),withDraw.getBalance());
	// return new ResponseEntity<AccountResponse>(new AccountResponse(balance),
	// HttpStatus.OK);
//}
//	@GetMapping("/withdraw-amount/account-no/{accountNo}/amount/{amount}")
	@GetMapping("/withdraw-amount/amount/{amount}")
	public ResponseEntity<AccountResponse> amountDraw(@RequestHeader int pin, @RequestHeader long accountNo,
			@PathVariable int amount) {
		String balance = atmdal.withDraw(accountNo, pin, amount);
		return new ResponseEntity<>(new AccountResponse(balance), HttpStatus.OK);

	}

	@PutMapping("/update-profile")
	public @ResponseBody ResponseEntity<String> updateProfile(@RequestBody Profile profile, @RequestHeader int pin,
			@RequestHeader long accountNo) {
		String status = atmdal.updateProfile(profile.getName(), profile.getEmail(), profile.getMobileNo(),
				profile.getPancardNo(), profile.getAdharcardNo(), profile.getDob(), pin, accountNo);

		System.out.println(status.toString());
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@GetMapping(value = "/check-balance/account-no/{accountNo}")

	public ResponseEntity<AccountResponse> getAccount(@Valid @RequestHeader int pin,
			@PathVariable("accountNo") long accountNo) {
		String balance = atmdal.checkBalance(accountNo, pin);
		return new ResponseEntity<>(new AccountResponse(balance), HttpStatus.OK);
	}

}
