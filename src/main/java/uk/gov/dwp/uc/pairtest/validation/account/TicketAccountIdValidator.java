package uk.gov.dwp.uc.pairtest.validation.account;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import uk.gov.dwp.uc.pairtest.domain.ticket.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.validation.TicketRequestValidator;
import uk.gov.dwp.uc.pairtest.domain.validation.ValidationState;

/**
 * Validation of account ID.
 */
@ApplicationScoped
public class TicketAccountIdValidator implements TicketRequestValidator {

  @Inject
  Logger log;
  private static final String VALIDATION_ERROR_MESSAGE = "Account ID is not valid";

  /**
   * Validates account of ticket request.
   *
   * @param accountId          The ID of account to validate
   * @param ticketTypeRequests the ticket request for purchase
   * @return ValidationState with status equals true if account is valid, or ValidationState with
   * status equals false and error message if account is invalid
   */
  @Override
  public ValidationState validate(Long accountId, TicketTypeRequest... ticketTypeRequests) {
    log.infov("Validating accountId: {0}", accountId);
    if (null != accountId && 0 < accountId) {
      log.infov("accountId: {0} is valid", accountId);
      return new ValidationState(true, null);
    } else {
      log.errorv("accountId: {0} is invalid", accountId);
      return new ValidationState(false, VALIDATION_ERROR_MESSAGE);
    }
  }

}
