//bean class to send account details in JSON format
package com.atm.model;

public class AccountDetails {
	public AccountDetails(long accountNo, int pin) {
		super();
		this.accountNo = accountNo;
		this.pin = pin;
	}

	public AccountDetails() {

	}

	private long accountNo;
	private int pin;
	private int balance;

	public long getAccountNo() {
		return accountNo;
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
		return "AccountDetails [accountNo=" + accountNo + ", pin=" + pin + ", balance=" + balance + "]";
	}

}
