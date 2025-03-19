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
import com.example.demo.models.appointment;
import com.example.demo.models.doctor;


@Repository
public class appointmentRepository {

	@Autowired
	private JdbcTemplate jdbctemplate;
	
	public void createappointment(appointment appointment) {
		String sql = "INSERT INTO appointment (patientId, doctorId, appointmentDate, appointmentTime, message) VALUES (?, ?, ?, ?, ?)";
		jdbctemplate.update(sql, appointment.getPatient().getPatientId(), appointment.getDoctor().getDoctorId(), appointment.getAppointmentDate(),
				appointment.getAppointmentTime(), appointment.getMessage());
		}
	
	
	public void updateappointment(appointment appointment) {
		String sql = "UPDATE appointment SET doctorId = ?, appointmentDate = ?, appointmentTime = ?, message = ? WHERE appointmentId = ?";
		jdbctemplate.update(sql, appointment.getDoctor().getDoctorId(), appointment.getAppointmentDate(),
				appointment.getAppointmentTime(), appointment.getMessage(), appointment.getAppointmentId());
	}
	
	
	public appointment getvalidateappointment(int doctorId, String date, String time) {
		
		try {
			String sql = "SELECT * FROM appointment WHERE  doctorId = ? AND appointmentDate = ? AND appointmentTime = ?";

			return jdbctemplate.queryForObject(sql, new BeanPropertyRowMapper<>(appointment.class), new Object[] {doctorId,date,time});
			
		} catch (Exception e) {
			return null;
		}
	}
	
	

	public List<appointment> getAllPatientspreviousAppointments(String date, int patientId) {
		String sql = "SELECT appointment.appointmentId,doctor.doctorId,doctor.first_name,doctor.last_name,Patient.patientId,appointment.appointmentDate, appointment.appointmentTime,appointment.registeredTime,appointment.message  FROM appointment JOIN doctor ON appointment.doctorId = doctor.doctorId  JOIN Patient ON appointment.patientId = Patient.patientId WHERE appointment.appointmentDate <= ? AND Patient.patientId = ?";
		return jdbctemplate.query(sql, new RowMapper<appointment>() {

			public appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
				Patient patient = (new BeanPropertyRowMapper<>(Patient.class)).mapRow(rs, rowNum);
				doctor doctor = (new BeanPropertyRowMapper<>(doctor.class)).mapRow(rs, rowNum);
				appointment appointment = (new BeanPropertyRowMapper<>(appointment.class)).mapRow(rs, rowNum);
				appointment.setPatient(patient);
				appointment.setDoctor(doctor);
				return appointment;
			}

		}, new Object[] { date,patientId});
	}
	
	
	
	public List<appointment> getAllAppointments() {
		String sql = "SELECT appointment.appointmentId,doctor.doctorId,patient.patientId,appointment.appointmentDate, appointment.appointmentTime,appointment.registeredTime,appointment.message  FROM appointment JOIN doctor ON appointment.doctorId = doctor.doctorId  JOIN Patient ON appointment.patientId = Patient.patientId";
		return jdbctemplate.query(sql, new RowMapper<appointment>() {

			public appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
				Patient patient = (new BeanPropertyRowMapper<>(Patient.class)).mapRow(rs, rowNum);
				doctor doctor = (new BeanPropertyRowMapper<>(doctor.class)).mapRow(rs, rowNum);
				appointment appointment = (new BeanPropertyRowMapper<>(appointment.class)).mapRow(rs, rowNum);
				appointment.setPatient(patient);
				appointment.setDoctor(doctor);
				return appointment;
			}

		});
	}
	
	
	public List<appointment> getAlldoctorpreviousAppointments(String date, int doctorId) {
		String sql = "SELECT appointment.appointmentId,doctor.doctorId,doctor.first_name,doctor.last_name,Patient.patientId,Patient.first_name,Patient.last_name,appointment.appointmentDate, appointment.appointmentTime,appointment.registeredTime,appointment.message  FROM appointment JOIN doctor ON appointment.doctorId = doctor.doctorId  JOIN Patient ON appointment.patientId = Patient.patientId WHERE appointment.appointmentDate <= ? AND doctor.doctorId = ?";
		return jdbctemplate.query(sql, new RowMapper<appointment>() {

			public appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
				Patient patient = (new BeanPropertyRowMapper<>(Patient.class)).mapRow(rs, rowNum);
				doctor doctor = (new BeanPropertyRowMapper<>(doctor.class)).mapRow(rs, rowNum);
				appointment appointment = (new BeanPropertyRowMapper<>(appointment.class)).mapRow(rs, rowNum);
				appointment.setPatient(patient);
				appointment.setDoctor(doctor);
				return appointment;
			}

		}, new Object[] { date, doctorId});
	}
	
	
	
	public List<appointment> getAllpatientupcomingAppointments(String date,int patientId) {
		
		String sql = "SELECT appointment.appointmentId,doctor.doctorId,doctor.first_name,doctor.last_name,Patient.patientId,appointment.appointmentDate, appointment.appointmentTime,appointment.registeredTime,appointment.message  FROM appointment JOIN doctor ON appointment.doctorId = doctor.doctorId  JOIN Patient ON appointment.patientId = Patient.patientId WHERE appointment.appointmentDate > ? AND Patient.patientId = ?";
		return jdbctemplate.query(sql, new RowMapper<appointment>() {

			public appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
				Patient patient = (new BeanPropertyRowMapper<>(Patient.class)).mapRow(rs, rowNum);
				doctor doctor = (new BeanPropertyRowMapper<>(doctor.class)).mapRow(rs, rowNum);
				appointment appointment = (new BeanPropertyRowMapper<>(appointment.class)).mapRow(rs, rowNum);
				appointment.setPatient(patient);
				appointment.setDoctor(doctor);
				return appointment;
			}

		}, new Object[] {date,patientId});
	}
	
	
	public List<appointment> getAlldoctorupcomingAppointments(String date, int doctorId) {
		String sql = "SELECT appointment.appointmentId,doctor.doctorId,doctor.first_name,doctor.last_name,Patient.patientId,Patient.first_name,Patient.last_name,appointment.appointmentDate, appointment.appointmentTime,appointment.registeredTime,appointment.message  FROM appointment JOIN doctor ON appointment.doctorId = doctor.doctorId  JOIN Patient ON appointment.patientId = Patient.patientId WHERE appointment.appointmentDate > ? AND doctor.doctorId = ?";
		return jdbctemplate.query(sql, new RowMapper<appointment>() {

			public appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
				Patient patient = (new BeanPropertyRowMapper<>(Patient.class)).mapRow(rs, rowNum);
				doctor doctor = (new BeanPropertyRowMapper<>(doctor.class)).mapRow(rs, rowNum);
				appointment appointment = (new BeanPropertyRowMapper<>(appointment.class)).mapRow(rs, rowNum);
				appointment.setPatient(patient);
				appointment.setDoctor(doctor);
				return appointment;
			}

		}, new Object[] { date, doctorId});
	}
	
	
	public appointment getappointment(int appointmentId) {
		
		try {
			String sql = "SELECT * FROM appointment NATURAL JOIN doctor WHERE appointmentId = ?";

			return jdbctemplate.queryForObject(sql, new RowMapper<appointment>() {

				public appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
					doctor doctor = (new BeanPropertyRowMapper<>(doctor.class)).mapRow(rs, rowNum);
					appointment appointment = (new BeanPropertyRowMapper<>(appointment.class)).mapRow(rs, rowNum);
					appointment.setDoctor(doctor);
					return appointment;
				}

			}, new Object[] {appointmentId});

		} catch (Exception e) {
			return null;
		}
		
	}
	
	
	
	public void deleteappointment(int appointmentId) {
		String sql = "DELETE FROM appointment WHERE appointmentId = ?";
		jdbctemplate.update(sql, appointmentId);
	}
	
}
