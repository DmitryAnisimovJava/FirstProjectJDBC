package com.anisimov.jdbc.starter.entity;

import java.time.LocalDateTime;

public record FlightEntity(Integer id, String flightNo, LocalDateTime departureDate, String departureAirportCode,
		LocalDateTime arrival_date, String arrivalAirportCode, Integer aircraftId, String status) {

}
