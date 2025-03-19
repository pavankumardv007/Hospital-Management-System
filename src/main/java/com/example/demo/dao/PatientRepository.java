package com.example.demo.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Patient;
import com.example.demo.models.User;


@Repository
public class PatientRepository {
	
	@Autowired
	private JdbcTemplate jdbctemplate;
	
	public void createPatient(Patient patient) {

		String sql = "INSERT INTO Patient (first_name, last_name,  gender, dateOfBirth, address, phoneNumber, username) VALUES (?, ?, ?, ?, ?, ?, ?)";

		jdbctemplate.update(sql, patient.getFirst_name(), patient.getLast_name(), patient.getGender(), patient.getDateOfBirth(),
				patient.getAddress(), patient.getPhoneNumber(), patient.getUser().getUsername());
	}
	
	public void updatePatient(Patient patient) {
		String sql = "UPDATE Patient SET first_name = ?, last_name = ?, gender = ?, dateOfBirth = ?, address = ?, phoneNumber = ?, username = ? WHERE patientId = ?";
		jdbctemplate.update(sql,patient.getFirst_name(), patient.getLast_name(), patient.getGender(), patient.getDateOfBirth(),
				patient.getAddress(), patient.getPhoneNumber(), patient.getUser().getUsername(), patient.getPatientId());
	}
	
	
	public List<Patient> getAllpatients() {
		String sql = "SELECT * FROM User NATURAL JOIN Patient";
		return jdbctemplate.query(sql, new RowMapper<Patient>() {

			public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(rs, rowNum);
				Patient patient = (new BeanPropertyRowMapper<>(Patient.class)).mapRow(rs, rowNum);
				patient.setUser(user);
				return patient;
			}

		});
	}
	
	
	public Patient getPatientByUsername(String username) {
		String sql = "SELECT * FROM Patient WHERE username = ?";
		return jdbctemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Patient.class),new Object[] { username });
	}
	
	
	
	public void deletePatient(int patientId) {
		String sql = "DELETE FROM Patient WHERE patientId = ?";

		jdbctemplate.update(sql, patientId);
	}

}
