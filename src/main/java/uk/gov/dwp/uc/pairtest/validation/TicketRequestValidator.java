package uk.gov.dwp.uc.pairtest.validation;


import jakarta.enterprise.context.ApplicationScoped;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

public interface TicketRequestValidator {

  ValidationState validate(Long accountId, TicketTypeRequest... ticketTypeRequests);

}
