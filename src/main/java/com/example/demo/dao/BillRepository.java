package com.example.demo.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Bill;
import com.example.demo.models.LabReport;
import com.example.demo.models.Patient;
import com.example.demo.models.appointment;
import com.example.demo.models.doctor;

@Repository
public class BillRepository {

	@Autowired
	private JdbcTemplate jdbctemplate;
	
	public void createBill(Bill bill) {
		String sql = "INSERT INTO Bill (BillId, appointmentId, patientId, AppointmentFee, LabCharge, MedicineCharge, TreatmentFee, RoomCharge, TotalAmount) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbctemplate.update(sql, bill.getBillId(), bill.getAppointmentId(), bill.getPatientId(), bill.getAppointmentFee(), 
				bill.getLabCharge(), bill.getMedicineCharge(),  bill.getTreatmentFee(), bill.getRoomCharge(), bill.getTotalAmount());
	}
	
	public void updateBill(Bill bill) {
		String sql = "UPDATE Bill SET  appointmentId = ?, patientId = ?, AppointmentFee = ?, LabCharge = ?, MedicineCharge = ?, TreatmentFee = ?, RoomCharge = ?, TotalAmount = ?  WHERE BillId = ?";
		jdbctemplate.update(sql,  bill.getAppointmentId(), bill.getPatientId(), bill.getAppointmentFee(), bill.getLabCharge(), 
				bill.getMedicineCharge(),  bill.getTreatmentFee(), bill.getRoomCharge(), bill.getTotalAmount(), bill.getBillId());
	}
	
	
	public List<Bill> getAllBills() {
		String sql = "SELECT * FROM Bill";
		return jdbctemplate.query(sql,new BeanPropertyRowMapper<>(Bill.class));
	}
	
	public List<Bill> getAllpatientBills(int patientId) {
		String sql = "SELECT * FROM Bill WHERE patientId = ?";
		return jdbctemplate.query(sql,new BeanPropertyRowMapper<>(Bill.class), new Object[] {patientId});
	}
	
	
	public Bill getBillByBillId(String BillId) {
		String sql = "SELECT * FROM Bill WHERE BillId = ?";
		return jdbctemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Bill.class), new Object[] {BillId});
	}
	
	
	
	public void deleteBill(String BillId) {
		try {
			String sql = "DELETE FROM Bill WHERE BillId = ?";
			jdbctemplate.update(sql, BillId);
		} catch (Exception e) {
			
		}
	}
	
	
	
	
	
	
}
