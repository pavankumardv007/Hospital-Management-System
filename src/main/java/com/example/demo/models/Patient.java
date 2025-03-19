package com.example.demo.models;



public class Patient {
	

	private int patientId;
	private String first_name;
	private String last_name;
	private String gender;
	private String dateOfBirth;
	private String address;
	private String phoneNumber;
	
	
	private User user;
	
	
	


	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	@Override
	public String toString() {
		return "Patient [patientId=" + patientId + ", first_name=" + first_name + ", last_name=" + last_name
				+ ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", address=" + address + ", phoneNumber="
				+ phoneNumber + ", user=" + user + "]";
	}
	
}
