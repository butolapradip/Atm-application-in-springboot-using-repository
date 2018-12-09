package com.atm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atm.repository.UserRepository;

@Component
public class AccountGenerate {

	private long accountNo;
	private int pin;
	@Autowired
	private UserRepository userRepository;

	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

	public AccountGenerate(long accountNo, int pin) {
		super();
		this.accountNo = accountNo;
		this.pin = pin;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public AccountGenerate() {

	}

	public int pinGenerate() {
		pin = (int) (Math.random() * 9000) + 1000;
		return pin;
	}

	public long accountGenerate() {

		// get number of record from database
		long count = userRepository.count();
		System.out.println(count);
		accountNo = 10000000000001l + count;
		return accountNo;
	}

}
