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
import com.example.demo.models.Patient;
import com.example.demo.models.SalaryPayments;
import com.example.demo.models.appointment;
import com.example.demo.models.doctor;

@Repository
public class SalaryPaymentsRepository {

	@Autowired
	private JdbcTemplate jdbctemplate;
	
	
	public void createSalaryPayments(SalaryPayments salaryPayments) {
		String sql = "INSERT INTO SalaryPayments (PaymentId, doctorId, amount, month) VALUES ( ?, ?, ?, ?)";
		jdbctemplate.update(sql, salaryPayments.getPaymentId(), salaryPayments.getDoctor().getDoctorId(), salaryPayments.getAmount(), salaryPayments.getMonth());
	}
	
	public void updateSalaryPayments(SalaryPayments salarypayment) {
		
		String sql = "UPDATE SalaryPayments SET doctorId = ?, amount = ?, month = ?  WHERE PaymentId = ?";
		jdbctemplate.update(sql,  salarypayment.getDoctor().getDoctorId(), salarypayment.getAmount(), salarypayment.getMonth(), 
				salarypayment.getPaymentId());
	}
	
	
	public List<SalaryPayments> getAllSalaryPayments() {
		String sql = "SELECT * FROM SalaryPayments NATURAL JOIN doctor";
		return jdbctemplate.query(sql, new RowMapper<SalaryPayments>() {

			public SalaryPayments mapRow(ResultSet rs, int rowNum) throws SQLException {
				SalaryPayments SalaryPayments = (new BeanPropertyRowMapper<>(SalaryPayments.class)).mapRow(rs, rowNum);
				doctor doctor = (new BeanPropertyRowMapper<>(doctor.class)).mapRow(rs, rowNum);
				SalaryPayments.setDoctor(doctor);
				return SalaryPayments;
			}

		});
	}
	
	
	public List<SalaryPayments> getAlldoctorSalaryPayments(int doctorId) {
		String sql = "SELECT * FROM SalaryPayments NATURAL JOIN doctor WHERE doctorId = ?";
		return jdbctemplate.query(sql, new RowMapper<SalaryPayments>() {

			public SalaryPayments mapRow(ResultSet rs, int rowNum) throws SQLException {
				SalaryPayments SalaryPayments = (new BeanPropertyRowMapper<>(SalaryPayments.class)).mapRow(rs, rowNum);
				doctor doctor = (new BeanPropertyRowMapper<>(doctor.class)).mapRow(rs, rowNum);
				SalaryPayments.setDoctor(doctor);
				return SalaryPayments;
			}

		},  new Object[] {doctorId});
	}
	
	
	public SalaryPayments getSalaryPaymentsByPaymentId(String PaymentId) {
		String sql = "SELECT * FROM SalaryPayments NATURAL JOIN doctor WHERE PaymentId = ?";
		return jdbctemplate.queryForObject(sql,new RowMapper<SalaryPayments>() {

			public SalaryPayments mapRow(ResultSet rs, int rowNum) throws SQLException {
				SalaryPayments SalaryPayments = (new BeanPropertyRowMapper<>(SalaryPayments.class)).mapRow(rs, rowNum);
				doctor doctor = (new BeanPropertyRowMapper<>(doctor.class)).mapRow(rs, rowNum);
				SalaryPayments.setDoctor(doctor);
				return SalaryPayments;
			}

		}, new Object[] {PaymentId});
	}
	
	
	public void deleteSalaryPayments(String PaymentId) {
		String sql = "DELETE FROM SalaryPayments WHERE PaymentId = ?";
		jdbctemplate.update(sql, PaymentId);
	}
}
