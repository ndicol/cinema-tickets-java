package uk.gov.dwp.uc.pairtest.domain.products;

import java.math.BigDecimal;
import uk.gov.dwp.uc.pairtest.domain.ticket.Type;

/**
 * Immutable pojo to hold ticket info.
 *
 * @param ticketType The type of ticket
 * @param price      The cost of the ticket
 */
public record Ticket(
    Type ticketType,
    BigDecimal price
) {

}
