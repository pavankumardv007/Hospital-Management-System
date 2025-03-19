package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import com.example.demo.models.room;


@Repository
public class roomRepository {

	@Autowired
	private JdbcTemplate jdbctemplate;
	
	public void createroom(room room) {
		String sql = "INSERT INTO room (RoomNo, RoomType, CostPerDay, Capacity) VALUES (?, ?, ?, ?)";
		jdbctemplate.update(sql, room.getRoomNo(), room.getRoomType(), room.getCostPerDay(),room.getCapacity());
	}
	
	public void updateroom(room room) {

		String sql = "UPDATE room SET RoomType = ?, CostPerDay = ?, Capacity = ? WHERE RoomNo = ?";

		jdbctemplate.update(sql, room.getRoomType(), room.getCostPerDay(), room.getCapacity(), room.getRoomNo());

	}

	public void deleteroom(int RoomNo) {
		
		try {
			String sql = "DELETE FROM room WHERE RoomNo = ?";
			jdbctemplate.update(sql, RoomNo);
		} catch (Exception e) {
			
		}
	}

	public List<room> getAllrooms() {
		String sql = "SELECT * FROM room";
		return jdbctemplate.query(sql, new BeanPropertyRowMapper<>(room.class));
	}
	
	public room getroombyRoomNo(int RoomNo) {
		String sql = "SELECT * FROM room WHERE RoomNo = ?";
		return jdbctemplate.queryForObject(sql, new BeanPropertyRowMapper<>(room.class), new Object[] { RoomNo });
	}
	
}
