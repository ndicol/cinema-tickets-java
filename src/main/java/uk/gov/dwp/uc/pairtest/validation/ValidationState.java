package uk.gov.dwp.uc.pairtest.validation;

/**
 * Immutable object holding the validation state of the request.
 *
 * @param isValid true if pass validation, else false
 * @param message holds error message if fail validation
 */
public record ValidationState(
    boolean isValid,
    String message
) {

}
