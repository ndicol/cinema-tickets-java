package uk.gov.dwp.uc.pairtest.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TicketTypeRequestTest {

  @Test
  void shouldConstructImmutableTicketTypeRequest() {
    TicketTypeRequest ticketTypeRequest = new TicketTypeRequest(Type.ADULT, 3);
    assertThat(ticketTypeRequest)
        .extracting("type", "noOfTickets")
        .containsExactly(Type.ADULT, 3);
  }
}
