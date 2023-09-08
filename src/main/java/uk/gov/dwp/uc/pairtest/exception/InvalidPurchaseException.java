package uk.gov.dwp.uc.pairtest.exception;

/**
 * Exception thrown on processing purchase.
 */
public class InvalidPurchaseException extends RuntimeException {

  public InvalidPurchaseException(String errorMessage) {
    super(errorMessage);
  }

}
