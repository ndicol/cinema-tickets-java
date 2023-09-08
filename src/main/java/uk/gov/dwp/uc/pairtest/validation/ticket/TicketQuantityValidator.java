package uk.gov.dwp.uc.pairtest.validation.ticket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Arrays;
import org.jboss.logging.Logger;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.validation.TicketRequestValidator;
import uk.gov.dwp.uc.pairtest.validation.ValidationState;

@ApplicationScoped
public class TicketQuantityValidator implements TicketRequestValidator {

  private static final String VALIDATION_ERROR_MESSAGE = "Number of tickets per request should be 20 or less";
  private static final int MAXIMUM_NUMBER_OF_TICKETS = 20;

  @Inject
  Logger log;

  /**
   * Validates number of tickets per requests.
   *
   * @param accountId          The account ID of ticket request to be validated
   * @param ticketTypeRequests the ticket request to validate number of tickets
   * @return ValidationState with status equals true if number of tickets is 0 or less, else
   * ValidationState with status of false and an error message
   */
  @Override
  public ValidationState validate(Long accountId, TicketTypeRequest... ticketTypeRequests) {
    log.infov("Validating ticket quantity for request with account ID: {0}", accountId);
    if (isValidTicketQuantity(ticketTypeRequests)) {
      log.infov("Ticket quantity for request with account ID: {0} is valid", accountId);
      return new ValidationState(true, null);
    } else {
      log.errorv("Ticket quantity for request with account ID: {0} is not valid", accountId);
      return new ValidationState(false, VALIDATION_ERROR_MESSAGE);
    }
  }

  private boolean isValidTicketQuantity(TicketTypeRequest... ticketTypeRequests) {
    int numberOfTickets = Arrays.stream(ticketTypeRequests).mapToInt(TicketTypeRequest::noOfTickets)
        .sum();
    return MAXIMUM_NUMBER_OF_TICKETS >= numberOfTickets;
  }

}
