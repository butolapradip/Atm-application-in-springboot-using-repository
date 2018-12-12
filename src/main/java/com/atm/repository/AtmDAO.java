package com.atm.repository;
//DAO interface 
import java.util.List;

import com.atm.exception.AtmException;
import com.atm.model.Account;
import com.atm.model.Profile;

public interface AtmDAO {
	List<Profile> getAllProfile();

	List<Account> getAllAccount();

	Profile addNewProfile(Profile profile)throws AtmException;

	String checkBalance(long accountNo, int pin)throws AtmException;

	String deposit(long accountNo, int pin, int amount)throws AtmException;

	String withDraw(long accountNo, int pin, int amount)throws AtmException;

	String updateProfile(String name, String email, String mobileNo, String pancardNo, String adharcardNo, String dob,
			int pin, long accountNo)throws AtmException;

	Account setAccountDetails(long accountNo)throws AtmException;
}
