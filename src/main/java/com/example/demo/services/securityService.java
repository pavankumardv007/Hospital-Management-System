package com.example.demo.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.config.MyUserDetails;
import com.example.demo.models.User;

@Transactional
@Service
public class securityService {
	
	public String findLoggedInUsername() {
		Object myUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (myUserDetails instanceof MyUserDetails) {
			return ((MyUserDetails) myUserDetails).getUser().getUsername();
		}

		return null;
	}

	public User findLoggedInUser() {
		Object myUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (myUserDetails instanceof MyUserDetails) {
			User user = ((MyUserDetails) myUserDetails).getUser();
			return user;
		}
		return null;
	}

}
