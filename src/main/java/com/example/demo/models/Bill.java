package com.example.demo.models;

import java.sql.Timestamp;

public class Bill {
	
	private String BillId;
	private Timestamp PaymentTime;
	private int appointmentId; 
	private int patientId; 
	private int AppointmentFee;
	private int LabCharge;
	private int MedicineCharge; 
	private int TreatmentFee; 
	private int RoomCharge;
	private int TotalAmount;
	
	public String getBillId() {
		return BillId;
	}
	public void setBillId(String billId) {
		BillId = billId;
	}
	public Timestamp getPaymentTime() {
		return PaymentTime;
	}
	public void setPaymentTime(Timestamp paymentTime) {
		PaymentTime = paymentTime;
	}
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public int getAppointmentFee() {
		return AppointmentFee;
	}
	public void setAppointmentFee(int appointmentFee) {
		AppointmentFee = appointmentFee;
	}
	public int getLabCharge() {
		return LabCharge;
	}
	public void setLabCharge(int labCharge) {
		LabCharge = labCharge;
	}
	public int getMedicineCharge() {
		return MedicineCharge;
	}
	public void setMedicineCharge(int medicineCharge) {
		MedicineCharge = medicineCharge;
	}
	public int getTreatmentFee() {
		return TreatmentFee;
	}
	public void setTreatmentFee(int treatmentFee) {
		TreatmentFee = treatmentFee;
	}
	public int getRoomCharge() {
		return RoomCharge;
	}
	public void setRoomCharge(int roomCharge) {
		RoomCharge = roomCharge;
	}
	public int getTotalAmount() {
		return TotalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		TotalAmount = totalAmount;
	}
	
	

}
