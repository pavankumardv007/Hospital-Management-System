package com.example.demo.models;

import java.sql.Timestamp;
import java.util.Date;

public class appointment {
	
	private int appointmentId;
	private String appointmentDate;
	private String appointmentTime;
	private Timestamp registeredTime;
	private String message;
	
	private doctor doctor;
	private Patient patient;
	
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	
	public String getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	
	public String getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	public Timestamp getRegisteredTime() {
		return registeredTime;
	}
	public void setRegisteredTime(Timestamp registeredTime) {
		this.registeredTime = registeredTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(doctor doctor) {
		this.doctor = doctor;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	
	

}
