package com.example.demo.models;

import java.sql.Timestamp;

public class SalaryPayments {
	
	private String PaymentId;
	private Timestamp PaymentTime;
	private int amount;
	private String month;
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	private doctor doctor;

	public String getPaymentId() {
		return PaymentId;
	}

	public void setPaymentId(String paymentId) {
		PaymentId = paymentId;
	}

	public Timestamp getPaymentTime() {
		return PaymentTime;
	}

	public void setPaymentTime(Timestamp paymentTime) {
		PaymentTime = paymentTime;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(doctor doctor) {
		this.doctor = doctor;
	}
	
	

}
