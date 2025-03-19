package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Bill;
import com.example.demo.models.InPatient;
import com.example.demo.models.room;

@Repository
public class InPatientRepository {

	@Autowired
	private JdbcTemplate jdbctemplate;
	public void createInPatient(InPatient inPatient) {
		String sql = "INSERT INTO InPatient (InPatientId, appointmentId, AdmissionDate, DischargeDate, advance, treatment, RoomNo, BillId) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbctemplate.update(sql, inPatient.getInPatientId(), inPatient.getAppointmentId(), inPatient.getAdmissionDate(),inPatient.getDischargeDate()
				, inPatient.getAdvance(), inPatient.getTreatment(), inPatient.getRoom().getRoomNo(), inPatient.getBill().getBillId());
	}
	
	public void updateInPatient(InPatient inPatient) {
		String sql = "UPDATE InPatient SET  AdmissionDate = ?, DischargeDate = ?, advance = ?, treatment = ?, RoomNo = ?, BillId = ?  WHERE InPatientId = ?";
		jdbctemplate.update(sql, inPatient.getAdmissionDate(),inPatient.getDischargeDate(), inPatient.getAdvance(), 
				inPatient.getTreatment(), inPatient.getRoom().getRoomNo(), inPatient.getBill().getBillId(), inPatient.getInPatientId());
	}
	
	
	public List<InPatient> getAllInPatient() {
		String sql = "SELECT * FROM room JOIN InPatient ON InPatient.RoomNo = room.RoomNo JOIN Bill ON InPatient.BillId = Bill.BillId";
		return jdbctemplate.query(sql,new RowMapper<InPatient>() {

			public InPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
				InPatient inPatient = (new BeanPropertyRowMapper<>(InPatient.class)).mapRow(rs, rowNum);
				room room = (new BeanPropertyRowMapper<>(room.class)).mapRow(rs, rowNum);
				Bill bill = (new BeanPropertyRowMapper<>(Bill.class)).mapRow(rs, rowNum);
				inPatient.setRoom(room);
				inPatient.setBill(bill);
				return inPatient;
			}

		});
	}
	

	public InPatient getInPatientByInPatientId(int InPatientId) {
		String sql = "SELECT * FROM room JOIN InPatient ON InPatient.RoomNo = room.RoomNo JOIN Bill ON InPatient.BillId = Bill.BillId WHERE InPatientId = ?";
		return jdbctemplate.queryForObject(sql,new RowMapper<InPatient>() {

			public InPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
				InPatient inPatient = (new BeanPropertyRowMapper<>(InPatient.class)).mapRow(rs, rowNum);
				room room = (new BeanPropertyRowMapper<>(room.class)).mapRow(rs, rowNum);
				Bill bill = (new BeanPropertyRowMapper<>(Bill.class)).mapRow(rs, rowNum);
				inPatient.setRoom(room);
				inPatient.setBill(bill);
				return inPatient;
			}

		}, new Object[] {InPatientId});
	}
	
	
	public InPatient getInPatientByappointmentId(int appointmentId) {
		
		try {
			String sql = "SELECT * FROM room NATURAL JOIN InPatient NATURAL JOIN Bill WHERE InPatient.appointmentId = ?";
			return jdbctemplate.queryForObject(sql,new RowMapper<InPatient>() {

				public InPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
					InPatient inPatient = (new BeanPropertyRowMapper<>(InPatient.class)).mapRow(rs, rowNum);
					room room = (new BeanPropertyRowMapper<>(room.class)).mapRow(rs, rowNum);
					Bill bill = (new BeanPropertyRowMapper<>(Bill.class)).mapRow(rs, rowNum);
					inPatient.setRoom(room);
					inPatient.setBill(bill);
					return inPatient;
				}

			}, new Object[] {appointmentId});
			
		} catch (Exception e) {
			return null;
		}
		
	}
	
	
    
	
	public void deleteInPatient(int InPatientId) {
		try {
			String sql = "DELETE FROM InPatient WHERE InPatientId = ?";
			jdbctemplate.update(sql, InPatientId);
		} catch (Exception e) {
			
		}
	}
}
