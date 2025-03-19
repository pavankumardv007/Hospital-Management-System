package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.models.patientEmail;



@Repository
public class patientEmailRepository {

	@Autowired
	private JdbcTemplate jdbctemplate;
	
	public void createpatientEmail(patientEmail patientEmail) {
		String sql = "INSERT INTO patientEmail (patientId, email) VALUES (?, ?)";
		jdbctemplate.update(sql, patientEmail.getPatientId(), patientEmail.getEmail());
	}

	public void deletepatientEmail(patientEmail patientEmail) {
		String sql = "DELETE FROM patientEmail WHERE patientId = ? AND email = ?";
		jdbctemplate.update(sql, patientEmail.getPatientId(), patientEmail.getEmail());
	}

	public List<patientEmail> getpatientEmailBypatientId(int patientId) {
		String sql = "SELECT * FROM patientEmail WHERE patientId = ?";
		return jdbctemplate.query(sql, new BeanPropertyRowMapper<>(patientEmail.class),new Object[] { patientId });
	}
	
}
