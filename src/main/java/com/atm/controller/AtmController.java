//controller class for rest mapping
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

import com.atm.exception.AtmException;
import com.atm.model.Account;
import com.atm.model.AccountDetails;
import com.atm.model.AccountResponse;
import com.atm.model.Profile;
import com.atm.repository.AccountRepository;
import com.atm.repository.AtmDAO;
import com.atm.repository.UserRepository;
import com.atm.service.AccountService;

@RestController
public class AtmController {
	@Autowired
	AccountService generate;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	AtmDAO atmdao;

	public AtmController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public AtmController(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public AtmController() {
	}

	@PostMapping(value = "/register")
	public ResponseEntity<AccountDetails> addNewUsers(@Valid @RequestBody Profile profile)throws AtmException {
		long accountNo = generate.accountGenerate();
		profile.setAccountNo(accountNo);
		userRepository.save(profile);
		Account userAccount = atmdao.setAccountDetails(profile.getAccountNo());
		return new ResponseEntity<AccountDetails>(new AccountDetails(userAccount.getAccountNo(), userAccount.getPin()),
				HttpStatus.OK);
	}

	@GetMapping(value = "/account")
	public List<Account> getAllAccount() {
		return accountRepository.findAll();
	}

	@PostMapping("/deposit-amount/amount/{amount}")
	public ResponseEntity<AccountResponse> amountDeposit(@Valid @RequestHeader int pin, @RequestHeader long accountNo,
			@PathVariable int amount)throws AtmException {
		if (amount <= 0) {
			String msg = "enter positive amount";
			return new ResponseEntity<>(new AccountResponse(msg), HttpStatus.OK);
		}
		String balance = atmdao.deposit(accountNo, pin, amount);
		return new ResponseEntity<>(new AccountResponse(balance), HttpStatus.OK);

	}

	@GetMapping("/withdraw-amount/amount/{amount}")
	public ResponseEntity<AccountResponse> amountDraw(@Valid @RequestHeader int pin, @RequestHeader long accountNo,
			@PathVariable int amount)throws AtmException {
		if (amount <= 0) {
			String msg = "enter positive amount";
			return new ResponseEntity<>(new AccountResponse(msg), HttpStatus.OK);
		}
		String balance = atmdao.withDraw(accountNo, pin, amount);
		return new ResponseEntity<>(new AccountResponse(balance), HttpStatus.OK);

	}

	@PutMapping("/update-profile")
	public @ResponseBody ResponseEntity<String> updateProfile(@Valid @RequestBody Profile profile,
			@Valid @RequestHeader int pin, @RequestHeader long accountNo)throws AtmException {
		String status = atmdao.updateProfile(profile.getName(), profile.getEmail(), profile.getMobileNo(),
				profile.getPancardNo(), profile.getAdharcardNo(), profile.getDob(), pin, accountNo);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@GetMapping(value = "/check-balance/account-no/{accountNo}")

	public ResponseEntity<AccountResponse> getAccount(@Valid @RequestHeader int pin,
			@PathVariable("accountNo") long accountNo) throws AtmException{
		String balance = atmdao.checkBalance(accountNo, pin);
		return new ResponseEntity<>(new AccountResponse(balance), HttpStatus.OK);
	}

}
