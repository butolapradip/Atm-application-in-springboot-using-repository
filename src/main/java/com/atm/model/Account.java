package com.atm.model;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document(collection = "account")
@Component
public class Account {

	private long accountNo;
	@Size(min = 4, message = "Name should have atleast 4 characters")
	private int pin;
	private int balance;

	public long getAccountNo() {
		return accountNo;
	}

	public Account() {

	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public Account(long accountNo, int pin) {
		super();
		this.accountNo = accountNo;
		this.pin = pin;
	}

	public Account(int balance) {
		// super();
		this.balance = balance;
	}
}
