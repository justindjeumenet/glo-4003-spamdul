package ca.ulaval.glo4003.domain.user;

import ca.ulaval.glo4003.api.contact.dto.UserDto;
import ca.ulaval.glo4003.domain.account.Account;
import ca.ulaval.glo4003.domain.account.AccountValidationError;
import ca.ulaval.glo4003.domain.user.userEnum.CommunicationMethod;
import ca.ulaval.glo4003.domain.user.userEnum.Sex;

public class UserAssembler {

  public User create(UserDto userDto) throws AccountValidationError {
    return new User(
        userDto.name,
        new CustomDate(userDto.birthDate),
        Sex.get(userDto.sex),
        userDto.age,
        CommunicationMethod.get(userDto.preferredCommunicationMethod),
        userDto.postalCode);
  }

  public UserDto create(Account account) {
    return this.create(account.getUser());
  }

  public UserDto create(User user) {
    UserDto userDto = new UserDto();
    userDto.name = user.getName();
    userDto.birthDate = user.getBirthDate().toString();
    userDto.sex = user.getSex().name().toLowerCase();
    userDto.age = user.getAge();
    userDto.preferredCommunicationMethod =
        user.getPreferredCommunicationMethod().name().toLowerCase();
    userDto.postalCode = user.getPostalCode();

    return userDto;
  }
}
