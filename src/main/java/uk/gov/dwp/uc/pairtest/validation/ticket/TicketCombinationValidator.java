package uk.gov.dwp.uc.pairtest.validation.ticket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Arrays;
import java.util.List;
import org.jboss.logging.Logger;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.Type;
import uk.gov.dwp.uc.pairtest.validation.TicketRequestValidator;
import uk.gov.dwp.uc.pairtest.validation.ValidationState;

@ApplicationScoped
public class TicketCombinationValidator implements TicketRequestValidator {

  private static final String VALIDATION_ERROR_MESSAGE =
      "Child and Infant tickets must be accompanied by an Adult ticket";

  @Inject
  Logger log;

  /**
   * Validates Child and Infant tickets includes an Adult ticket in the request.
   *
   * @param accountId          the account ID of ticket request to be validated
   * @param ticketTypeRequests the ticket request to validate Child or Infant includes Adult ticket
   * @return ValidationState with status equals true if number of tickets is 0 or less, else
   * ValidationState with status of false and an error message
   */
  @Override
  public ValidationState validate(Long accountId, TicketTypeRequest... ticketTypeRequests) {
    List<Type> tickets = Arrays.stream(ticketTypeRequests).map(TicketTypeRequest::type).toList();
    log.infov(
        "Checking that Child and Infant tickets have an associated Adult ticket for request with account ID: {0}",
        accountId);
    if ((tickets.contains(Type.CHILD) || tickets.contains(Type.INFANT)) && !tickets.contains(
        Type.ADULT)) {
      log.errorv(
          "Child or Infant tickets does not have corresponding Adult ticket for request with account ID: {0}",
          accountId);
      return new ValidationState(false, VALIDATION_ERROR_MESSAGE);
    } else {
      log.infov(
          "All Child or Infant tickets have corresponding Adult ticket for request with account ID: {0}",
          accountId);
      return new ValidationState(true, null);
    }
  }

}
