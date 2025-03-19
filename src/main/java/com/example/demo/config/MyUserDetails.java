package com.example.demo.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.models.User;

public class MyUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	private User user;
	
	public MyUserDetails(User user) {
		this.user = user;
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<GrantedAuthority> grantList = new ArrayList<>();
		if (user == null) {
			return grantList;
		}
		GrantedAuthority authority = new SimpleGrantedAuthority(this.user.getRole());
		grantList.add(authority);
		return grantList;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.getIsAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
