package uk.gov.dwp.uc.pairtest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import org.jboss.logging.Logger;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.ticket.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.ticket.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.repository.TicketRepository;
import uk.gov.dwp.uc.pairtest.validation.TicketRequestValidator;
import uk.gov.dwp.uc.pairtest.domain.validation.ValidationState;

/**
 * TicketServiceImpl manages ticket purchasing.
 * <p>
 * (Note) implemented as per exercise. No REST resource provided as not specified by the exercise No
 * connection persistent medium added as not specified by the exercise. Repository jus statically
 * mimics find element for testing purposes.
 */
@ApplicationScoped
public class TicketServiceImpl implements TicketService {

  public static final String CALCULATE_TOTAL_TICKET_PRICE_ERROR = "Could not calculate total ticket price";
  /**
   * Should only have private methods other than the one below.
   */

  @Inject
  Logger logger;
  @Inject
  private Instance<TicketRequestValidator> ticketRequestValidatorList;

  @Inject
  private TicketPaymentService ticketPaymentService;

  @Inject
  private SeatReservationService seatReservationService;

  @Inject
  TicketRepository ticketRepository;

  /**
   * Purchase ticket and make reservation.
   *
   * @param accountId          The account ID of ticket request
   * @param ticketTypeRequests the ticket request
   * @throws InvalidPurchaseException thrown for errors during purchase
   */
  @Override
  public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests)
      throws InvalidPurchaseException {
    List<ValidationState> validationErrors = new ArrayList<>();
    for (TicketRequestValidator ticketRequestValidator : ticketRequestValidatorList) {
      ValidationState validationstate = ticketRequestValidator.validate(accountId,
          ticketTypeRequests);
      if (!validationstate.isValid()) {
        validationErrors.add(validationstate);
      }
    }
    if (!validationErrors.isEmpty()) {
      throw new InvalidPurchaseException(validationErrors.get(0).message()); // throws first error
      // but can push all errors if required by returning for example a Response with the error list
    }
    logger.infov("Calculating total price for request with accountId: {0}", accountId);
    BigDecimal totalTicketCost = calculateTotalTicketPrice(
        ticketTypeRequests); // for my own sanity, money calculations were done in BigDecimal
    logger.infov("Total ticket cost for accountId: {0} is £{1}", accountId, totalTicketCost);
    logger.infov("Calculating total number of seats for accountId: {0}", accountId);
    int totalSeatsToAllocate = calculateTotalSeatsToAllocate(ticketTypeRequests);
    logger.infov("{0} seats to be allocated for accountId: {1}", totalSeatsToAllocate, accountId);
    try {
      ticketPaymentService.makePayment(accountId,
          totalTicketCost.intValue()); // totalTicketCost converted to int as TicketPaymentService must not be modified
      seatReservationService.reserveSeat(accountId, totalSeatsToAllocate);
      logger.infov("Payments and seats reservation completed for request with accountId: {0}",
          accountId);
    } catch (Exception exception) {
      throw new InvalidPurchaseException(
          "Error processing purchase for customer ID: %s".formatted(accountId));
    }

  }

  private int calculateTotalSeatsToAllocate(TicketTypeRequest[] ticketTypeRequests) {
    return Arrays.stream(ticketTypeRequests)
        .filter(Predicate.not(request -> request.type().equals(Type.INFANT)))
        .mapToInt(TicketTypeRequest::noOfTickets).sum();
  }

  private BigDecimal calculateTotalTicketPrice(TicketTypeRequest... ticketTypeRequests) {
    return Arrays.stream(ticketTypeRequests).map(
            ticket -> new BigDecimal(ticket.noOfTickets()).multiply(
                ticketRepository.findPriceByTicketType(ticket.type()).price()))
        .reduce(BigDecimal::add).orElseThrow(() -> new InvalidPurchaseException(
            CALCULATE_TOTAL_TICKET_PRICE_ERROR));
  }

}
