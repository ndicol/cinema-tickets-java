package uk.gov.dwp.uc.pairtest.validation.ticket;

import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.Type;

@QuarkusTest
class TicketCombinationValidatorTest {

  private static final String VALIDATION_ERROR_MESSAGE =
      "Child and Infant tickets must be accompanied by an Adult ticket";

  @Inject
  TicketCombinationValidator ticketCombinationValidator;


  @ParameterizedTest
  @EnumSource(value = Type.class, names = {"CHILD", "INFANT"})
  void givenChildOrInfantTicketTypeWithAdultShouldReturnValidState(Type ticketType) {
    TicketTypeRequest[] ticketTypeRequest = {new TicketTypeRequest(Type.ADULT, 2),
        new TicketTypeRequest(ticketType, 2)};
    assertThat(ticketCombinationValidator.validate(100L, ticketTypeRequest))
        .extracting("isValid", "message")
        .containsExactly(true, null);
  }

  @ParameterizedTest
  @EnumSource(value = Type.class, names = {"CHILD", "INFANT"})
  void givenChildOrInfantTicketTypeWithNoAdultShouldReturnInvalidState(Type ticketType) {
    TicketTypeRequest[] ticketTypeRequest = {new TicketTypeRequest(Type.CHILD, 2),
        new TicketTypeRequest(ticketType, 2)};
    assertThat(ticketCombinationValidator.validate(100L, ticketTypeRequest))
        .extracting("isValid", "message")
        .containsExactly(false, VALIDATION_ERROR_MESSAGE);
  }

  @ParameterizedTest
  @EnumSource(value = Type.class, names = {"CHILD", "INFANT"})
  void givenAllAdultTicketsTypeWithNoAdultShouldReturnValidState(Type ticketType) {
    TicketTypeRequest[] ticketTypeRequest = {new TicketTypeRequest(Type.ADULT, 2),
        new TicketTypeRequest(ticketType, 2)};
    assertThat(ticketCombinationValidator.validate(100L, ticketTypeRequest))
        .extracting("isValid", "message")
        .containsExactly(true, null);
  }

}
