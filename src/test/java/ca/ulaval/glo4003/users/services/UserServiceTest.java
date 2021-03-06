package ca.ulaval.glo4003.users.services;

import static ca.ulaval.glo4003.accounts.helpers.AccountBuilder.anAccount;
import static ca.ulaval.glo4003.users.helpers.UserBuilder.aUser;
import static ca.ulaval.glo4003.users.helpers.UserDtoBuilder.aUserDto;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.accounts.domain.Account;
import ca.ulaval.glo4003.accounts.domain.AccountFactory;
import ca.ulaval.glo4003.accounts.domain.AccountRepository;
import ca.ulaval.glo4003.accounts.services.assemblers.AccountIdAssembler;
import ca.ulaval.glo4003.accounts.services.converters.AccountIdConverter;
import ca.ulaval.glo4003.users.domain.User;
import ca.ulaval.glo4003.users.services.assemblers.UserAssembler;
import ca.ulaval.glo4003.users.services.converters.UserConverter;
import ca.ulaval.glo4003.users.services.dto.AccountIdDto;
import ca.ulaval.glo4003.users.services.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
  @Mock private AccountRepository accountRepository;
  @Mock private AccountFactory accountFactory;
  @Mock private AccountIdConverter accountIdConverter;
  @Mock private AccountIdAssembler accountIdAssembler;
  @Mock private UserConverter userConverter;
  @Mock private UserAssembler userAssembler;
  @Mock private AccountIdDto accountIdDto;

  private UserService userService;

  private final Account account = anAccount().build();
  private final User user = aUser().build();
  private final UserDto userDto = aUserDto().build();

  @Before
  public void setUp() {
    userService =
        new UserService(
            accountRepository,
            accountFactory,
            accountIdConverter,
            accountIdAssembler,
            userConverter,
            userAssembler);

    when(userConverter.convert(userDto)).thenReturn(user);
    when(accountFactory.createAccount(user)).thenReturn(account);
    when(accountRepository.save(account)).thenReturn(account.getId());
    when(accountIdAssembler.assemble(account.getId())).thenReturn(accountIdDto);

    when(accountIdConverter.convert(account.getId().toString())).thenReturn(account.getId());
    when(accountRepository.get(account.getId())).thenReturn(account);
    when(userAssembler.assemble(account.getUser())).thenReturn(userDto);
  }

  @Test
  public void whenAddingUser_thenReturnAccountIdDto() {
    AccountIdDto receivedAccountIdDto = userService.addUser(userDto);

    assertThat(receivedAccountIdDto).isSameInstanceAs(accountIdDto);
  }

  @Test
  public void whenGettingUser_thenReturnUserDto() {
    UserDto receivedUserDto = userService.getUser(account.getId().toString());

    assertThat(receivedUserDto).isSameInstanceAs(userDto);
  }
}
