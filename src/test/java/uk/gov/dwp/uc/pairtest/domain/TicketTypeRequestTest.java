package uk.gov.dwp.uc.pairtest.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.ticket.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.ticket.Type;

class TicketTypeRequestTest {

  @Test
  void shouldConstructImmutableTicketTypeRequest() {
    TicketTypeRequest ticketTypeRequest = new TicketTypeRequest(Type.ADULT, 3);
    assertThat(ticketTypeRequest)
        .extracting("type", "noOfTickets")
        .containsExactly(Type.ADULT, 3);
  }
}
