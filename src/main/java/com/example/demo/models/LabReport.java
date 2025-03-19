package com.example.demo.models;

import java.util.Date;

public class LabReport {

	private int appointmentId;
	private String date;
	private String LabTest1;
	private String LabTest2;
	private String LabResult;
	private int amount;
	private String feedback;
	
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getLabTest1() {
		return LabTest1;
	}
	public void setLabTest1(String labTest1) {
		LabTest1 = labTest1;
	}
	public String getLabTest2() {
		return LabTest2;
	}
	public void setLabTest2(String labTest2) {
		LabTest2 = labTest2;
	}
	
	public String getLabResult() {
		return LabResult;
	}
	public void setLabResult(String labResult) {
		LabResult = labResult;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
