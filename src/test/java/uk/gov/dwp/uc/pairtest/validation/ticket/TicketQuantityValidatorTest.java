package uk.gov.dwp.uc.pairtest.validation.ticket;

import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.Type;

@QuarkusTest
class TicketQuantityValidatorTest {

  private static final String VALIDATION_ERROR_MESSAGE = "Number of tickets per request should be 20 or less";
  @Inject
  TicketQuantityValidator ticketQuantityValidator;


  @ParameterizedTest
  @ValueSource(ints = {1, 3, 20})
  void givenValidNumberOfTicketsPerRequestShouldReturnValidState(int numberOfTickets) {
    TicketTypeRequest ticketTypeRequest = new TicketTypeRequest(Type.ADULT, numberOfTickets);
    assertThat(ticketQuantityValidator.validate(100L, ticketTypeRequest))
        .extracting("isValid", "message")
        .containsExactly(true, null);
  }

  @ParameterizedTest
  @ValueSource(ints = {21, 25, 45})
  void givenInvalidNumberOfTicketsPerRequestShouldReturnValidState(int numberOfTickets) {
    TicketTypeRequest ticketTypeRequest = new TicketTypeRequest(Type.ADULT, numberOfTickets);
    assertThat(ticketQuantityValidator.validate(100L, ticketTypeRequest))
        .extracting("isValid", "message")
        .containsExactly(false, VALIDATION_ERROR_MESSAGE);
  }

}
