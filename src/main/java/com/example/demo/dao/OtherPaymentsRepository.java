package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Department;
import com.example.demo.models.OtherPayments;
import com.example.demo.models.SalaryPayments;
import com.example.demo.models.doctor;

@Repository
public class OtherPaymentsRepository {

	@Autowired
	private JdbcTemplate jdbctemplate;
	
	public void createOtherPayments(OtherPayments otherPayments) {
		String sql = "INSERT INTO OtherPayments (PaymentId, departmentId, amount, PaidTo, reason) VALUES ( ?, ?, ?, ?, ?)";
		jdbctemplate.update(sql, otherPayments.getPaymentId(), otherPayments.getDepartment().getDepartmentId(), otherPayments.getAmount(), otherPayments.getPaidTo(), otherPayments.getReason());
	}
	
	public void updateOtherPayments(OtherPayments otherPayments) {
		
		String sql = "UPDATE OtherPayments SET departmentId = ?, amount = ?, PaidTo = ?, reason = ?  WHERE PaymentId = ?";
		jdbctemplate.update(sql,  otherPayments.getDepartment().getDepartmentId(), otherPayments.getAmount(), otherPayments.getPaidTo(), otherPayments.getReason(), otherPayments.getPaymentId());
	}
	
	
	public List<OtherPayments> getAllOtherPayments() {
		String sql = "SELECT * FROM OtherPayments NATURAL JOIN Department";
		return jdbctemplate.query(sql, new RowMapper<OtherPayments>() {

			public OtherPayments mapRow(ResultSet rs, int rowNum) throws SQLException {
				OtherPayments OtherPayments = (new BeanPropertyRowMapper<>(OtherPayments.class)).mapRow(rs, rowNum);
				Department Department = (new BeanPropertyRowMapper<>(Department.class)).mapRow(rs, rowNum);
				OtherPayments.setDepartment(Department);
				return OtherPayments;
			}

		});
	}
	
	
	
	public OtherPayments getAllPaymentsbypaymentId(String PaymentId) {
		String sql = "SELECT * FROM OtherPayments NATURAL JOIN Department WHERE PaymentId = ?";
		return jdbctemplate.queryForObject(sql, new RowMapper<OtherPayments>() {

			public OtherPayments mapRow(ResultSet rs, int rowNum) throws SQLException {
				OtherPayments OtherPayments = (new BeanPropertyRowMapper<>(OtherPayments.class)).mapRow(rs, rowNum);
				Department Department = (new BeanPropertyRowMapper<>(Department.class)).mapRow(rs, rowNum);
				OtherPayments.setDepartment(Department);
				return OtherPayments;
			}

		}, new Object[] { PaymentId });
	}
	
	
	
	public void deleteOtherPayments(String PaymentId) {
		String sql = "DELETE FROM OtherPayments WHERE PaymentId = ?";
		jdbctemplate.update(sql, PaymentId);
	}
	
}
