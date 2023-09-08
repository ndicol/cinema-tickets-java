package uk.gov.dwp.uc.pairtest.validation.account;

import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@QuarkusTest
class AccountIdValidatorTest {

  private static final String VALIDATION_ERROR_MESSAGE = "Account ID is not valid";
  @Inject
  TicketRequestIdValidator accountIdValidator;

  @ParameterizedTest
  @ValueSource(longs = {1L, 2L, 100L, 400L})
  void givenAccountIdGreaterThanZeroShouldBeValid(Long accountId) {
    assertThat(accountIdValidator.validate(accountId))
        .extracting("isValid", "message")
        .containsExactly(true, null);
  }

  @ParameterizedTest
  @ValueSource(longs = {0L, -1L, -13L})
  void givenAccountIdLessThanOrEqualToZeroShouldBeValid(Long accountId) {
    assertThat(accountIdValidator.validate(accountId))
        .extracting("isValid", "message")
        .containsExactly(false, VALIDATION_ERROR_MESSAGE);
  }

}
