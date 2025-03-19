package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.models.LabReport;
import com.example.demo.models.Patient;
import com.example.demo.models.User;
import com.example.demo.models.appointment;
import com.example.demo.models.doctor;


@Repository
public class LabReportRepository {

	@Autowired
	private JdbcTemplate jdbctemplate;
	
	public void createLabReport(LabReport labReport) {
		String sql = "INSERT INTO LabReport (appointmentId, date, LabTest1, LabTest2, LabResult, amount, feedback) VALUES (?, ?, ?, ?, ?, ?, ?)";
		jdbctemplate.update(sql, labReport.getAppointmentId(), labReport.getDate(), labReport.getLabTest1(),labReport.getLabTest2(),
				labReport.getLabResult(), labReport.getAmount(), labReport.getFeedback());
		}
	
	public void updateLabReport(LabReport labReport) {
		String sql = "UPDATE LabReport SET  date = ?, LabTest1 = ?, LabTest2 = ?, LabResult = ?, amount = ?, feedback = ? WHERE appointmentId = ?";
		jdbctemplate.update(sql, labReport.getDate(), labReport.getLabTest1(), labReport.getLabTest2(),
				labReport.getLabResult(), labReport.getAmount(),labReport.getFeedback(), labReport.getAppointmentId());
	}
	
	
	public List<LabReport> getAllLabReports() {
		String sql = "SELECT * FROM LabReport";
		return jdbctemplate.query(sql,new BeanPropertyRowMapper<>(LabReport.class));
	}
	
	
	public LabReport getLabReportByAppointmentId(int appointmentId) {
		try {
			String sql = "SELECT * FROM LabReport WHERE appointmentId = ?";
			return jdbctemplate.queryForObject(sql,new BeanPropertyRowMapper<>(LabReport.class), new Object[] { appointmentId });
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public List<LabReport> getAlldoctorLabReports(int doctorId) {
		String sql = "SELECT * FROM LabReport NATURAL JOIN appointment WHERE appointment.doctorId = ?";
		return jdbctemplate.query(sql,new BeanPropertyRowMapper<>(LabReport.class), new Object[] { doctorId });
	}
	
	public List<LabReport> getAllpatientLabReports(int patientId) {
		String sql = "SELECT * FROM LabReport NATURAL JOIN appointment WHERE appointment.patientId = ?";
		return jdbctemplate.query(sql,new BeanPropertyRowMapper<>(LabReport.class), new Object[] {patientId});
	}
	
	public void deleteLabReport(int appointmentId) {
		String sql = "DELETE FROM LabReport WHERE appointmentId = ?";
		jdbctemplate.update(sql, appointmentId);
	}
}
