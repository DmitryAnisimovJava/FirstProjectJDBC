package com.anisimov.jdbc.starter.entity;

public class TicketEntity {

	private Integer id;
	private String passengerNo;
	private String passengerName;
	private String seatNo;
	private Integer flightId;
	private Integer cost;

	public TicketEntity(Integer id, String passengerNo, String passengerName, String seatNo, Integer flightId,
			Integer cost) {
		super();
		this.id = id;
		this.passengerNo = passengerNo;
		this.passengerName = passengerName;
		this.seatNo = seatNo;
		this.flightId = flightId;
		this.cost = cost;
	}

	public TicketEntity() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassengerNo() {
		return passengerNo;
	}

	public void setPassengerNo(String passengerNo) {
		this.passengerNo = passengerNo;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public Integer getFlightId() {
		return flightId;
	}

	public void setFlightId(Integer flightId) {
		this.flightId = flightId;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "TicketEntity [id=" + id + ", passengerNo=" + passengerNo + ", passengerName=" + passengerName
				+ ", seatNo=" + seatNo + ", flightId=" + flightId + ", cost=" + cost + "]";
	}

}
