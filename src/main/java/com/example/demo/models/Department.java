package com.example.demo.models;


public class Department {
	
	private int departmentId;
	private String departmentName;
	private int managerId;
	private String manager_start_date;
	
	
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	
	public void setStringDepartmentId(String departmentId) {
		Integer stringtoInteger = Integer.parseInt(departmentId);
        int stringInt = stringtoInteger.intValue();
		this.departmentId = stringInt;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public String getManager_start_date() {
		return manager_start_date;
	}
	public void setManager_start_date(String manager_start_date) {
		this.manager_start_date = manager_start_date;
	}
		

}
