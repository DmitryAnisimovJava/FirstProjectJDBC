package com.anisimov.jdbc.starter.dto;

public record DtoTicket(int limit, int offset, String seatNo, Integer cost, String passengerName) {
}
