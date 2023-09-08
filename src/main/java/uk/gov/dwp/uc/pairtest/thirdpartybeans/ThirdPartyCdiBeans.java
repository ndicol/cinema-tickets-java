package uk.gov.dwp.uc.pairtest.thirdpartybeans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationService;
import thirdparty.seatbooking.SeatReservationServiceImpl;

/**
 * Registers third party beans for CDI.
 */
public class ThirdPartyCdiBeans {

  /**
   * Produces TicketPaymentService to be used by CDI.
   * <p>
   * Only added because thirdparty package cannot be modified and no implementation is requested for
   * the service. Might have sufficed to annotate TicketPaymentServiceImpl class with
   * @ApplicationScoped
   *
   * @return TicketPaymentService implemented by TicketPaymentServiceImpl
   */

  @Produces
  @ApplicationScoped
  public TicketPaymentService getTicketPaymentServiceBean() {
    return new TicketPaymentServiceImpl();
  }

  /**
   * Produces SeatReservationService to be used by CDI.
   * <p>
   * Only added because thirdparty package cannot be modified and no implementation is requested for
   * the service. Might have sufficed to annotate SeatReservationServiceImpl class with
   * @ApplicationScoped
   *
   * @return SeatReservationService implemented by SeatReservationServiceImpl
   */

  @Produces
  @ApplicationScoped
  public SeatReservationService getSeatReservationService() {
    return new SeatReservationServiceImpl();
  }

}
