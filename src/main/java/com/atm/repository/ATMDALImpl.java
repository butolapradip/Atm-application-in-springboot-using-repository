package com.atm.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.atm.model.Account;
import com.atm.model.Profile;

@Component
public class ATMDALImpl implements AtmDAL {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private MongoOperations mongoOperation;
	@Autowired
	Account account;
	@Autowired
	AccountRepository accountRepository;

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
	public String checkBalance(long accountNo, int pin)

	{
		Query query = new Query();
		query.addCriteria(Criteria.where("accountNo").is(accountNo));
		if (mongoTemplate.findOne(query, Account.class) != null) {
			Query pinNo = new Query();
			Account account = mongoTemplate.findOne(query, Account.class);
			int DBpin = account.getPin();
			System.out.println(DBpin);
			if (pin == DBpin) {
				int bal = account.getBalance();
				return "Balanace :" + bal;
			}
			return "wrong pin";
		}
		return "wrong account No";

	}

	@Override
	public String deposit(long accountNo, int pin, int amount) {
		Query query = new Query();
		query.addCriteria(Criteria.where("accountNo").is(accountNo));
		if (mongoTemplate.findOne(query, Account.class) != null) {
			Query pinNo = new Query();
			Account account = mongoTemplate.findOne(query, Account.class);
			int DBpin = account.getPin();
			System.out.println(DBpin);
			if (pin == DBpin) {
				int bal = account.getBalance();
				System.out.println(bal);
				int totalBalance = account.getBalance() + amount;
				account.setBalance(totalBalance);
				System.out.println("set by setter method" + account.getBalance());
				mongoTemplate.updateFirst(query, Update.update("balance", totalBalance), Account.class);
				return "Balanace :" + totalBalance;
			}
			return "wrong pin";
		}
		return "wrong account No";

	}

	@Override
	public String withDraw(long accountNo, int pin, int amount) {
		Query query = new Query();
		query.addCriteria(Criteria.where("accountNo").is(accountNo));
		if (mongoTemplate.findOne(query, Account.class) != null) {
			Query pinNo = new Query();
			Account account = mongoTemplate.findOne(query, Account.class);
			int DBpin = account.getPin();
			System.out.println(DBpin);
			if (pin == DBpin) {
				int bal = account.getBalance();
				System.out.println(bal);
				if (amount > account.getBalance()) {
					return "amount entered is more the balance";
				}
				System.out.println("ammount : " + amount);
				int totalBalance = bal - amount;

				account.setBalance(totalBalance);
				System.out.println("set by setter method" + account.getBalance());
				mongoTemplate.updateFirst(query, Update.update("balance", totalBalance), Account.class);
				return "Balanace :" + totalBalance;
			}
			return "wrong pin";
		}
		return "wrong account No";

	}

	public String updateProfile(String name, String email, String mobileNo, String pancardNo, String adharcardNo,
			String dob, int pin, long accountNo) {
		Query query = new Query();
		query.addCriteria(Criteria.where("accountNo").is(accountNo));
		if (mongoTemplate.findOne(query, Account.class) != null) {
			Query pinNo = new Query();
			Account account = mongoTemplate.findOne(query, Account.class);
			int DBpin = account.getPin();
			System.out.println(DBpin);
			if (pin == DBpin) {
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
		return "wrong account No";
	}
}
