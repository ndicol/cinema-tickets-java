package uk.gov.dwp.uc.pairtest.exception;

/**
 * Exception thrown on purchase processing error.
 */
public class InvalidPurchaseException extends RuntimeException {

  public InvalidPurchaseException(String errorMessage) {
    super(errorMessage);
  }

}
