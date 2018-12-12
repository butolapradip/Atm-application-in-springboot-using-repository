package com.atm.model;

import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document(collection = "account")
@Component
@Scope("prototype")
public class Account {

	private long accountNo;
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

	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", pin=" + pin + ", balance=" + balance + "]";
	}

	public Account(long accountNo, int pin) {
		super();
		this.accountNo = accountNo;
		this.pin = pin;
	}

	public Account(int balance) {
		this.balance = balance;
	}
}
