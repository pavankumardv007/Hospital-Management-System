package com.example.demo.models;

public class User {
	
	
	private String username;
	private String password;
	private String role;
	private Boolean isAccountNonLocked;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Boolean getIsAccountNonLocked() {
		return isAccountNonLocked;
	}
	public void setIsAccountNonLocked(Boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", role=" + role + ", isAccountNonLocked="
				+ isAccountNonLocked + "]";
	}
	
	
	

}
