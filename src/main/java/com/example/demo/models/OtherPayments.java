package com.example.demo.models;

import java.sql.Timestamp;

public class OtherPayments {

	private String PaymentId;
	private Timestamp PaymentTime; 
	private int amount; 
	private String PaidTo; 
	private String reason;
	
	private Department department;

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

	public String getPaidTo() {
		return PaidTo;
	}

	public void setPaidTo(String paidTo) {
		PaidTo = paidTo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	
}
