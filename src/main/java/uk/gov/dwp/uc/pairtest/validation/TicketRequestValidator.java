package uk.gov.dwp.uc.pairtest.validation;


import uk.gov.dwp.uc.pairtest.domain.ticket.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.validation.ValidationState;

public interface TicketRequestValidator {

  ValidationState validate(Long accountId, TicketTypeRequest... ticketTypeRequests);

}
