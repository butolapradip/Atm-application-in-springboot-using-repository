package com.atm.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

//@Component
//@EntityScan
public class Profile {
	@NotNull
	@Size(min = 2, message = "Name should have atleast 2 characters")
	private String name;
	// @NotNull
	@Size(min = 4, message = "email should have atleast 4 characters")
	@Email
	private String email;
	@Pattern(regexp="(^$|[0-9]{10})")
	@Size(min = 10, message = "mobile should have atleast 10 numbers")
	private String mobileNo;
	// @NotNull
	@Size(min = 8, message = "pancard should have atleast 8 characters")
	private String pancardNo;
	// @NotNull
	@Size(min = 10, message = "adharcardNo should have atleast 10 characters")
	private String adharcardNo;
	// @NotNull

	private String dob;
	private long accountNo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "Profile [name:" + name + ", email:" + email + ", mobileNo:" + mobileNo + ", pancardNo:" + pancardNo
				+ ", adharcardNo:" + adharcardNo + ", dob:" + dob + ", accountNo:" + accountNo + "]";
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPancardNo() {
		return pancardNo;
	}

	public void setPancardNo(String pancardNo) {
		this.pancardNo = pancardNo;
	}

	public String getAdharcardNo() {
		return adharcardNo;
	}

	public void setAdharcardNo(String adharcardNo) {
		this.adharcardNo = adharcardNo;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}
	public Profile()
	{
		
	}
	
	public Profile(String name,String email,String mobileNo,String pancardNo,String adharcardNo,String dob){
		this.name = name;
		this.email = email;
		this.mobileNo = mobileNo;
		this.pancardNo = pancardNo;
		this.adharcardNo = adharcardNo;
		this.dob = dob;
        
	}

}
