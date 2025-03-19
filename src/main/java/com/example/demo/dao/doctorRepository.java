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
import com.example.demo.models.User;
import com.example.demo.models.doctor;

@Repository
public class doctorRepository {
	
	@Autowired
	private JdbcTemplate jdbctemplate;
	
	public void createdoctor(doctor doctor) {

		String sql = "INSERT INTO doctor (doctorId, first_name, last_name,  gender, dateOfBirth, address, phoneNumber, specialization, qualification, salary, departmentId, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		jdbctemplate.update(sql, doctor.getDoctorId(), doctor.getFirst_name(), doctor.getLast_name(), doctor.getGender(), doctor.getDateOfBirth(),
				doctor.getAddress(), doctor.getPhoneNumber(), doctor.getSpecialization(), doctor.getQualification(),
				doctor.getSalary(), doctor.getDepartment().getDepartmentId(), doctor.getUser().getUsername());
	}
	
	public void updatedoctor(doctor doctor) {
		String sql = "UPDATE doctor SET first_name = ?, last_name = ?, gender = ?, dateOfBirth = ?, address = ?, phoneNumber = ?, specialization = ?, qualification = ?, salary = ?,departmentId = ?, username = ? WHERE doctorId = ?";
		jdbctemplate.update(sql, doctor.getFirst_name(), doctor.getLast_name(), doctor.getGender(), doctor.getDateOfBirth(),
				doctor.getAddress(), doctor.getPhoneNumber(), doctor.getSpecialization(), doctor.getQualification(),
				doctor.getSalary(), doctor.getDepartment().getDepartmentId(), doctor.getUser().getUsername(), doctor.getDoctorId());
	}
	
	
	public List<doctor> getAlldoctors() {
		String sql = "SELECT * FROM User NATURAL JOIN doctor NATURAL JOIN Department";
		return jdbctemplate.query(sql, new RowMapper<doctor>() {

			public doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(rs, rowNum);
				doctor doctor = (new BeanPropertyRowMapper<>(doctor.class)).mapRow(rs, rowNum);
				Department department = (new BeanPropertyRowMapper<>(Department.class)).mapRow(rs, rowNum);
				doctor.setUser(user);
				doctor.setDepartment(department);
				return doctor;
			}

		});
	}
	
	
	public doctor getdoctorByUsername(String username) {
		String sql = "SELECT * FROM User NATURAL JOIN doctor NATURAL JOIN Department WHERE username = ?";
		return jdbctemplate.queryForObject(sql, new RowMapper<doctor>() {

			public doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(rs, rowNum);
				doctor doctor = (new BeanPropertyRowMapper<>(doctor.class)).mapRow(rs, rowNum);
				Department department = (new BeanPropertyRowMapper<>(Department.class)).mapRow(rs, rowNum);
				doctor.setUser(user);
				doctor.setDepartment(department);
				return doctor;
			}

		},new Object[] { username });
	}
	
	
	
	public void deletedoctor(int doctorId) {
		String sql = "DELETE FROM doctor WHERE doctorId = ?";

		jdbctemplate.update(sql, doctorId);
	}

}
