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
public class DepartmentRepository {
	
	@Autowired
	private JdbcTemplate jdbctemplate;
	
	public void createDepartment(Department department) { 
		String sql = "INSERT INTO Department (departmentId, departmentName, managerId, manager_start_date) VALUES (?, ?, ?, ?)";
		jdbctemplate.update(sql, department.getDepartmentId(), department.getDepartmentName(), department.getManagerId(), department.getManager_start_date());
		}
	
	public void updateDepartment(Department department) {
		String sql = "UPDATE Department SET departmentName = ?, managerId = ?, manager_start_date= ? WHERE departmentId = ?";
		jdbctemplate.update(sql, department.getDepartmentName(), department.getManagerId(),
				department.getManager_start_date(), department.getDepartmentId());
	}
	
	
	public List<doctor> getAllDepartments() {
		String sql = "SELECT Department.departmentId, Department.departmentName, Department.managerId, Department.manager_start_date, doctor.doctorId, doctor.first_name, doctor.last_name  FROM Department JOIN doctor ON doctor.doctorId = Department.managerId";
		return jdbctemplate.query(sql, new RowMapper<doctor>() {

			public doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
				Department department = (new BeanPropertyRowMapper<>(Department.class)).mapRow(rs, rowNum);
				doctor doctor = (new BeanPropertyRowMapper<>(doctor.class)).mapRow(rs, rowNum);
				doctor.setDepartment(department);
				return doctor;
			}

		});
	}
	
	
	public Department getdepartmentbyDepartmentId(int departmentId) {
		String sql = "SELECT * FROM Department WHERE departmentId = ?";
		return jdbctemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Department.class), new Object[] { departmentId });
	}
	
	public void deleteDepartment(int departmentId) {
		try {
			String sql = "DELETE FROM Department WHERE departmentId = ?";
			jdbctemplate.update(sql, departmentId);
		} catch (Exception e) {
			
		}
	}

}
