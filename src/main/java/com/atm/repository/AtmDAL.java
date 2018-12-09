package com.atm.repository;

import java.util.List;

import com.atm.model.Account;
import com.atm.model.AccountResponse;
import com.atm.model.Profile;

public interface AtmDAL {
	List<Profile> getAllProfile();

	List<Account> getAllAccount();

	Profile addNewProfile(Profile profile);

	String checkBalance(long accountNo, int pin);

	String deposit(long accountNo, int pin, int amount);

	String withDraw(long accountNo, int pin, int amount);

	String updateProfile(String name, String email, String mobileNo, String pancardNo, String adharcardNo, String dob,
			int pin, long accountNo);

}
