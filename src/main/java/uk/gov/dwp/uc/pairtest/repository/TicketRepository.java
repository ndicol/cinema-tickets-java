package uk.gov.dwp.uc.pairtest.repository;

import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.List;
import uk.gov.dwp.uc.pairtest.domain.ticket.Type;
import uk.gov.dwp.uc.pairtest.domain.products.Ticket;

/**
 * Ticket repository with static sample tickets.
 */
@ApplicationScoped
public class TicketRepository {

  static List<Ticket> ticketProducts = List.of(new Ticket(Type.INFANT, BigDecimal.ZERO),
      new Ticket(Type.CHILD, BigDecimal.TEN),
      new Ticket(Type.ADULT,
          BigDecimal.valueOf(20))); //The tickets should be coming from a product database
  // or some other persistable layer, but I'm just hard-cording test values here just for simplicity

  public Ticket findPriceByTicketType(Type type) {
    // convenient method. Actual implementation mar require querying DB
    return ticketProducts.stream()
        .filter(ticket -> ticket.ticketType().equals(type))
        .findFirst()
        .orElse(null);
  }
}
