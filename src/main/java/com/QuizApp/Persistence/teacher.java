package com.QuizApp.Persistence;

public class teacher {

	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String module;
	private String password;
	private boolean isActivated;
	public teacher() {}
	
	public teacher(String firstName, String lastName, String phoneNumber,String md, String password, boolean isActivated) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.module =md;
		this.password = password;
		this.isActivated = isActivated;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActivated() {
		return isActivated;
	}
	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}
	@Override
	public String toString() {
		return "teacher [firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber
				+ ", password=" + password + ", isActivated=" + isActivated + "]";
	}
	
	
	
}
