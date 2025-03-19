package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.models.User;


@Repository
public class UserRepository {
	
	@Autowired
	private JdbcTemplate jdbctemplate;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void createUser(User user) {

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		String sql = "INSERT INTO User (username, password, role) VALUES (?, ?, ?)";

		jdbctemplate.update(sql, user.getUsername(), user.getPassword(), user.getRole());
	}
	
	public User getUser(String username) {
		String sql = "SELECT * FROM User WHERE username = ?";

		return jdbctemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), new Object[] { username });
	}
	
	public void updateUser(User user) {

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		String sql = "UPDATE User SET password = ?, isAccountNonLocked = ? WHERE username = ?";

		jdbctemplate.update(sql, user.getPassword(), user.getIsAccountNonLocked(), user.getUsername());

	}
	
	
    public User getvalidateuser(String username) {
		
		try {
			String sql = "SELECT * FROM User WHERE  username = ?";

			return jdbctemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), new Object[] {username});
			
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public void deleteUser(String username) {
		String sql = "DELETE FROM User WHERE username = ?";

		jdbctemplate.update(sql, username);
	}
	
	

}
