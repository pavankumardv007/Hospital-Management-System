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
import com.example.demo.models.OutPatient;
import com.example.demo.models.room;

@Repository
public class OutPatientRepository {

	@Autowired
	private JdbcTemplate jdbctemplate;
	
	
	public void createOutPatient(OutPatient outPatient) {
		String sql = "INSERT INTO OutPatient (OutPatientId, appointmentId, treatment, remarks, BillId) VALUES ( ?, ?, ?, ?, ?)";
		jdbctemplate.update(sql, outPatient.getOutPatientId(), outPatient.getAppointmentId(), outPatient.getTreatment(),outPatient.getRemarks()
				, outPatient.getBill().getBillId());
	}
	
	public void updateOutPatient(OutPatient outPatient) {
		String sql = "UPDATE OutPatient SET treatment = ?, remarks = ?, BillId = ?  WHERE OutPatientId = ?";
		jdbctemplate.update(sql, outPatient.getTreatment(), outPatient.getRemarks()
				, outPatient.getBill().getBillId(), outPatient.getOutPatientId());
	}
	
	
	public List<OutPatient> getAllOutPatients() {
		String sql = "SELECT * FROM OutPatient JOIN Bill ON OutPatient.BillId = Bill.BillId";
		return jdbctemplate.query(sql,new RowMapper<OutPatient>() {

			public OutPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
				OutPatient outPatient = (new BeanPropertyRowMapper<>(OutPatient.class)).mapRow(rs, rowNum);
				Bill bill = (new BeanPropertyRowMapper<>(Bill.class)).mapRow(rs, rowNum);
				outPatient.setBill(bill);
				return outPatient;
			}

		});
	}
	

	public OutPatient getOutPatientByOutPatientId(int OutPatientId) {
		String sql = "SELECT * FROM OutPatient JOIN Bill ON OutPatient.BillId = Bill.BillId WHERE OutPatientId = ?";
		return jdbctemplate.queryForObject(sql,new RowMapper<OutPatient>() {

			public OutPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
				OutPatient outPatient = (new BeanPropertyRowMapper<>(OutPatient.class)).mapRow(rs, rowNum);
				Bill bill = (new BeanPropertyRowMapper<>(Bill.class)).mapRow(rs, rowNum);
				outPatient.setBill(bill);
				return outPatient;
			}

		}, new Object[] {OutPatientId});
	}
	
	
	public OutPatient getOutPatientByappointmentId(int appointmentId) {
		String sql = "SELECT * FROM OutPatient NATURAL JOIN Bill WHERE OutPatient.appointmentId = ?";
		return jdbctemplate.queryForObject(sql,new RowMapper<OutPatient>() {

			public OutPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
				OutPatient outPatient = (new BeanPropertyRowMapper<>(OutPatient.class)).mapRow(rs, rowNum);
				Bill bill = (new BeanPropertyRowMapper<>(Bill.class)).mapRow(rs, rowNum);
				outPatient.setBill(bill);
				return outPatient;
			}

		}, new Object[] {appointmentId});
	}
	
	public void deleteOutPatient(int OutPatientId) {
		try {
			String sql = "DELETE FROM OutPatient WHERE OutPatientId = ?";
			jdbctemplate.update(sql, OutPatientId);
		} catch (Exception e) {
			
		}
	}
}
