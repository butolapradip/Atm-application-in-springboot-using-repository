
package com.atm.model;

public class AccountResponse {

	private String balance;

	public AccountResponse(String s) {
		this.balance = s;
	}

	public String getResponse() {
		return balance;
	}

	public void setResponse(String balance) {
		this.balance = balance;
	}

	public AccountResponse() {

	}

}
