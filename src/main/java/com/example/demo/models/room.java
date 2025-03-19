package com.example.demo.models;

public class room {
	
	private int RoomNo;
	private String RoomType; 
	private int CostPerDay;
	private int Capacity;
	
	public int getCapacity() {
		return Capacity;
	}
	
	public void setCapacity(int capacity) {
		Capacity = capacity;
	}
	
	public int getRoomNo() {
		return RoomNo;
	}
	public void setRoomNo(int roomNo) {
		RoomNo = roomNo;
	}
	public String getRoomType() {
		return RoomType;
	}
	public void setRoomType(String roomType) {
		RoomType = roomType;
	}
	public int getCostPerDay() {
		return CostPerDay;
	}
	public void setCostPerDay(int costPerDay) {
		CostPerDay = costPerDay;
	}
	
	

}
