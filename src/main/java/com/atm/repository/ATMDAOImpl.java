package com.atm.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.atm.exception.AtmException;
import com.atm.model.Account;
import com.atm.model.Profile;
import com.atm.service.AccountService;
import com.mongodb.MongoException;

//DAO class 
@Component
public class ATMDAOImpl implements AtmDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	AccountService process;

	@Override
	public List<Profile> getAllProfile() {
		return mongoTemplate.findAll(Profile.class);
	}

	@Override
	public List<Account> getAllAccount() {
		return mongoTemplate.findAll(Account.class);
	}

	@Override
	public Profile addNewProfile(Profile profile) {
		mongoTemplate.save(profile);
		return profile;
	}

	@Override
	public String checkBalance(long accountNo, int pin) throws AtmException {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("accountNo").is(accountNo));
			if (mongoTemplate.findOne(query, Account.class) != null) {
				Account account = mongoTemplate.findOne(query, Account.class);
				if (pin == account.getPin()) {
					return "Balanace :" + account.getBalance();
				}
				return "wrong pin";
			}
		} catch (MongoException ex) {
			throw new AtmException("Mongo exception :Exception occurred  while connecting to Mongo");
		} catch (Exception e) {
			throw new AtmException("exception occur while connecting to mongo port");
		}
		return "wrong account No";
	}

	@Override
	public String deposit(long accountNo, int pin, int amount) throws AtmException {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("accountNo").is(accountNo));
			Account account = mongoTemplate.findOne(query, Account.class);
			if (account != null) {
				if (pin == account.getPin()) {
					int totalBalance = account.getBalance() + amount;
					account.setBalance(totalBalance);
					mongoTemplate.updateFirst(query, Update.update("balance", totalBalance), Account.class);
					return "Balanace :" + totalBalance;
				}
				return "wrong pin";
			}
		} catch (MongoException ex) {
			throw new AtmException("Mongo exception :Exception occurred  while connecting to Mongo");
		} catch (Exception e) {
			throw new AtmException("exception occur while connecting to mongo port");
		}

		return "wrong account No";
	}

	@Override
	public String withDraw(long accountNo, int pin, int amount) throws AtmException {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("accountNo").is(accountNo));
			Account account = mongoTemplate.findOne(query, Account.class);
			if (account != null) {
				if (pin == account.getPin()) {
					int bal = account.getBalance();
					if (amount > bal) {
						return "amount entered is more the balance";
					}
					int totalBalance = bal - amount;
					account.setBalance(totalBalance);
					mongoTemplate.updateFirst(query, Update.update("balance", totalBalance), Account.class);
					return "Balanace :" + totalBalance;
				}
				return "wrong pin";
			}
		} catch (MongoException ex) {
			throw new AtmException("Mongo exception :Exception occurred  while connecting to Mongo");
		} catch (Exception e) {
			throw new AtmException("exception occur while connecting to mongo port");
		}
		return "wrong account No";
	}

	public String updateProfile(String name, String email, String mobileNo, String pancardNo, String adharcardNo,
			String dob, int pin, long accountNo) throws AtmException {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("accountNo").is(accountNo));
			Account account = mongoTemplate.findOne(query, Account.class);
			if (account != null) {
				if (pin == account.getPin()) {
					Update update = new Update();
					update.set("name", name);
					update.set("email", email);
					update.set("mobileNo", mobileNo);
					update.set("pancardNo", pancardNo);
					update.set("dob", dob);
					mongoTemplate.updateFirst(query, update, Profile.class);
					Profile profile = mongoTemplate.findOne(query, Profile.class);

					return "Profile updated sucess " + profile;
				}
				return "wrong pin";
			}
		} catch (MongoException ex) {
			throw new AtmException("Mongo exception :Exception occurred  while connecting to Mongo");
		} catch (Exception e) {
			throw new AtmException("exception occur while connecting to mongo port");
		}
		return "wrong account No";
	}

	public Account setAccountDetails(long accountNo) throws AtmException {
		Account accounts = null;
		try {
			int pin = process.pinGenerate();
			Account account = new Account();
			account.setAccountNo(accountNo);
			account.setPin(pin);
			accountRepository.save(account);
			Query query = new Query();
			query.addCriteria(Criteria.where("accountNo").is(accountNo));
			Account accountDetails = mongoTemplate.findOne(query, Account.class);
			accounts = accountDetails;
		} catch (MongoException ex) {
			throw new AtmException("Mongo exception :Exception occurred  while connecting to Mongo");
		} catch (Exception e) {
			throw new AtmException("exception occur while connecting to mongo port");
		}
		System.out.println(accounts.toString());
		return accounts;
	}
}
