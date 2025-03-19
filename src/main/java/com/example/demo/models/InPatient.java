package com.example.demo.models;


public class InPatient {
	
	private int InPatientId;
	private int appointmentId;
	private String AdmissionDate;
	private String DischargeDate;
	private int advance;
	private String treatment;
	
	private room room;
	private Bill bill;
	
	public int getInPatientId() {
		return InPatientId;
	}
	public void setInPatientId(int inPatientId) {
		InPatientId = inPatientId;
	}
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	
	public String getAdmissionDate() {
		return AdmissionDate;
	}
	public void setAdmissionDate(String admissionDate) {
		AdmissionDate = admissionDate;
	}
	public String getDischargeDate() {
		return DischargeDate;
	}
	public void setDischargeDate(String dischargeDate) {
		DischargeDate = dischargeDate;
	}
	
	public int getAdvance() {
		return advance;
	}
	public void setAdvance(int advance) {
		this.advance = advance;
	}
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	public room getRoom() {
		return room;
	}
	public void setRoom(room room) {
		this.room = room;
	}
	public Bill getBill() {
		return bill;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	
}
