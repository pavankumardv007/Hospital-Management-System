package com.example.demo.models;

public class OutPatient {

	private int OutPatientId;
	private int appointmentId;
	private String treatment;
	private String remarks;
	
	private Bill bill;

	public int getOutPatientId() {
		return OutPatientId;
	}

	public void setOutPatientId(int outPatientId) {
		OutPatientId = outPatientId;
	}

	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	
	
}
