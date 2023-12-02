package com.anisimov.jdbc.starter;

import java.util.Iterator;

import com.anisimov.jdbc.starter.dao.TicketDao;
import com.anisimov.jdbc.starter.dto.DtoTicket;
import com.anisimov.jdbc.starter.entity.TicketEntity;

public class DaoTest {

	public static void main(String[] args) {
		var find = TicketDao.getInstance().find(new DtoTicket(3, 1, "B2", 250, null));
		System.out.println(find);
	}

	private static void getAll() {
		var findAllColumns = TicketDao.getInstance().findAllColumns();
		for (Iterator iterator = findAllColumns.iterator(); iterator.hasNext();) {
			TicketEntity ticketEntity = (TicketEntity) iterator.next();
			System.out.println(ticketEntity);
		}
	}

	private static void updateTest() {
		var findById = TicketDao.getInstance().findById(2);
		System.out.println(findById);
		findById.ifPresent(ticket -> {
			ticket.setCost(188);
			TicketDao.getInstance().update(ticket, ticket.getId());
		});
	}

	private static void createAndUpdateTest() {
		var ticketEntity = new TicketEntity();
		ticketEntity.setCost(2000);
		ticketEntity.setFlightId(3);
		ticketEntity.setPassengerName("Jopa");
		ticketEntity.setPassengerNo("B12242");
		ticketEntity.setSeatNo("A232");
		TicketDao.getInstance().create(ticketEntity);
		System.out.println(TicketDao.getInstance().delete(59));
	}

}
