package uk.gov.dwp.uc.pairtest.domain;

/**
 * Immutable TicketTypeRequest Object.
 * <p>
 * Number of tickets and their types.
 */
public record TicketTypeRequest(
    Type type,
    int noOfTickets

) { // Change to Java record (from ver 16) as it's immutable, native to java and without boilerplate codes

}
