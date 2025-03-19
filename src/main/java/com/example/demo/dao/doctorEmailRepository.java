package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.models.doctorEmail;


@Repository
public class doctorEmailRepository {

	@Autowired
	private JdbcTemplate jdbctemplate;
	
	public void createdoctorEmail(doctorEmail doctorEmail) {
		String sql = "INSERT INTO doctorEmail (doctorId, email) VALUES (?, ?)";
		jdbctemplate.update(sql, doctorEmail.getDoctorId(), doctorEmail.getEmail());
	}

	public void deletedoctorEmail(doctorEmail doctorEmail) {
		String sql = "DELETE FROM doctorEmail WHERE doctorId = ? AND email = ?";
		jdbctemplate.update(sql, doctorEmail.getDoctorId(), doctorEmail.getEmail());
	}

	public List<doctorEmail> getdoctorEmailByDoctorId(int doctorId) {
		String sql = "SELECT * FROM doctorEmail WHERE doctorId = ?";
		return jdbctemplate.query(sql, new BeanPropertyRowMapper<>(doctorEmail.class),new Object[] { doctorId });
	}
	
}
