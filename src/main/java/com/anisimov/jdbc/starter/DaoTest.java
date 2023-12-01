package com.anisimov.jdbc.starter;

import com.anisimov.jdbc.starter.dao.TicketDao;
import com.anisimov.jdbc.starter.entity.TicketEntity;

public class DaoTest {

	public static void main(String[] args) {
		var ticketEntity = new TicketEntity();
		ticketEntity.setCost(2000);
		ticketEntity.setFlightId(3);
		ticketEntity.setPassengerName("Jopa");
		ticketEntity.setPassengerNo("B12242");
		ticketEntity.setSeatNo("A232");
		TicketDao.create(ticketEntity);
		System.out.println(TicketDao.delete(59));
	}

}
