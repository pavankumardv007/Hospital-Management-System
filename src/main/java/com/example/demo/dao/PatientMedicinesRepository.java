package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PatientMedicinesRepository {

	@Autowired
	private JdbcTemplate jdbctemplate;
	
}
