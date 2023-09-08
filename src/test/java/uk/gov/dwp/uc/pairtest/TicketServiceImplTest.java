package uk.gov.dwp.uc.pairtest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

@QuarkusTest
class TicketServiceImplTest {

  private static final Long ACCOUNT_ID = 100L;

  @Inject
  TicketServiceImpl ticketService;

  @InjectMock
  private TicketPaymentService ticketPaymentService;
  @InjectMock
  private SeatReservationService seatReservationService;

  ArgumentCaptor<Long> accountIdCaptor;
  ArgumentCaptor<Integer> totalPaymentCaptor;
  ArgumentCaptor<Integer> totalSeatsCaptor;


  @BeforeEach
  void setUp() {
    accountIdCaptor = ArgumentCaptor.forClass(Long.class);
    totalPaymentCaptor = ArgumentCaptor.forClass(Integer.class);
    totalSeatsCaptor = ArgumentCaptor.forClass(Integer.class);
  }

  @Test
  void givenValidTicketRequestsShouldPurchaseTickets() {
    TicketTypeRequest[] ticketTypeRequests = {new TicketTypeRequest(Type.ADULT, 4),
        new TicketTypeRequest(Type.CHILD, 4), new TicketTypeRequest(Type.INFANT, 4)};

    ticketService.purchaseTickets(ACCOUNT_ID, ticketTypeRequests);

    verify(ticketPaymentService, times(1)).makePayment(accountIdCaptor.capture(),
        totalPaymentCaptor.capture());
    assertThat(accountIdCaptor.getValue()).isEqualTo(ACCOUNT_ID);
    assertThat(totalPaymentCaptor.getValue()).isEqualTo(120);

    verify(seatReservationService, times(1)).reserveSeat(accountIdCaptor.capture(),
        totalSeatsCaptor.capture());
    assertThat(accountIdCaptor.getValue()).isEqualTo(ACCOUNT_ID);
    assertThat(totalSeatsCaptor.getValue()).isEqualTo(8);
  }

  @Test
  void givenInvalidTicketRequestShouldThrowInvalidPurchaseExceptionN() {
    TicketTypeRequest[] ticketTypeRequests = {new TicketTypeRequest(Type.CHILD, 5)};

    assertThatThrownBy(() -> ticketService.purchaseTickets(ACCOUNT_ID, ticketTypeRequests))
        .isInstanceOf(InvalidPurchaseException.class);

    verifyNoInteractions(ticketPaymentService);
    verifyNoInteractions(seatReservationService);
  }


}