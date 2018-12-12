package com.atm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atm.repository.UserRepository;

//implement AccountProcess Interface
@Component
public class AccountServicelmpl implements AccountService {

	@Autowired
	private UserRepository userRepository;

	public int pinGenerate() {
		int pin = (int) (Math.random() * 9000) + 1000;
		return pin;
	}

	public long accountGenerate() {
		long count = userRepository.count();
		long accountNo = 10000000000001l + count;
		return accountNo;
	}

}
