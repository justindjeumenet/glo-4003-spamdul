package ca.ulaval.glo4003.accounts.infrastructure;

import static ca.ulaval.glo4003.accesspasses.helpers.AccessPassBuilder.anAccessPass;
import static ca.ulaval.glo4003.accounts.helpers.AccountBuilder.anAccount;
import static ca.ulaval.glo4003.cars.helpers.LicensePlateMother.createLicensePlate;
import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.accesspasses.domain.AccessPass;
import ca.ulaval.glo4003.accesspasses.exceptions.NotFoundAccessPassException;
import ca.ulaval.glo4003.accounts.domain.Account;
import ca.ulaval.glo4003.accounts.domain.AccountId;
import ca.ulaval.glo4003.accounts.domain.AccountRepository;
import ca.ulaval.glo4003.accounts.exceptions.NotFoundAccountException;
import ca.ulaval.glo4003.cars.domain.LicensePlate;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class AccountRepositoryInMemoryTest {
  private AccountRepository accountRepository;

  private final LicensePlate licensePlate = createLicensePlate();
  private final AccessPass accessPass = anAccessPass().withLicensePlate(licensePlate).build();
  private final Account account =
      anAccount().withAccessPasses(Collections.singletonList(accessPass)).build();

  @Before
  public void setUp() {
    accountRepository = new AccountRepositoryInMemory();
  }

  @Test
  public void whenSavingAccount_thenReturnId() {
    AccountId accountId = accountRepository.save(account);

    assertThat(accountId).isSameInstanceAs(account.getId());
  }

  @Test
  public void whenSavingAccount_thenAccountCanBeFound() {
    accountRepository.save(account);
    Account foundAccount = accountRepository.get(account.getId());

    assertThat(foundAccount).isSameInstanceAs(account);
  }

  @Test(expected = NotFoundAccountException.class)
  public void givenNonExistentAccount_whenGettingAccount_thenThrowNotFoundAccountException() {
    accountRepository.get(account.getId());
  }

  @Test(expected = NotFoundAccessPassException.class)
  public void
      givenNoAccountWithAccessPass_whenGettingAccessPass_thenThrowNotFoundAccessPassException() {
    accountRepository.getAccessPass(accessPass.getCode());
  }

  @Test
  public void whenGettingAccessPass_thenGetAccessPass() {
    accountRepository.save(account);

    AccessPass foundAccessPass = accountRepository.getAccessPass(accessPass.getCode());

    assertThat(foundAccessPass).isSameInstanceAs(accessPass);
  }

  @Test(expected = NotFoundAccessPassException.class)
  public void
      givenLicensePlateAnNoAccount_whenGettingAccessPasses_thenThrowNotFoundAccessPassException() {
    accountRepository.getAccessPasses(licensePlate);
  }

  @Test
  public void givenLicensePlate_whenGettingAccessPasses_thenGetAccessPasses() {
    accountRepository.save(account);

    List<AccessPass> accessPasses = accountRepository.getAccessPasses(licensePlate);

    assertThat(accessPasses).hasSize(1);
    assertThat(accessPasses.get(0)).isSameInstanceAs(accessPass);
  }

  @Test
  public void whenUpdatingAccount_thenAccountIsUpdated() {
    accountRepository.save(account);
    Account updatedAccount = anAccount().withId(account.getId()).build();

    accountRepository.update(updatedAccount);
    Account foundAccount = accountRepository.get(account.getId());

    assertThat(foundAccount).isNotSameInstanceAs(account);
  }

  @Test(expected = NotFoundAccountException.class)
  public void givenNonExistentAccount_whenUpdatingAccount_thenThrowNotFoundAccountException() {
    accountRepository.update(account);
  }

  @Test
  public void whenUpdatingAccessPass_thenAccessPassIsUpdated() {
    accountRepository.save(account);
    AccessPass updatedAccessPass = anAccessPass().withCode(accessPass.getCode()).build();

    accountRepository.update(updatedAccessPass);
    AccessPass foundAccessPass = accountRepository.getAccessPass(accessPass.getCode());

    assertThat(foundAccessPass).isNotSameInstanceAs(accessPass);
  }

  @Test(expected = NotFoundAccessPassException.class)
  public void
      givenNonExistentAccessPass_whenUpdatingAccessPass_thenThrowNotFoundAccessPassException() {
    accountRepository.update(accessPass);
  }
}
