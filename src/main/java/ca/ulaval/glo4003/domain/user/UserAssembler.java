package ca.ulaval.glo4003.domain.user;

import ca.ulaval.glo4003.api.user.dto.UserDto;
import ca.ulaval.glo4003.domain.time.CustomDate;
import ca.ulaval.glo4003.domain.time.CustomDateAssembler;
import ca.ulaval.glo4003.domain.time.exception.InvalidDateException;
import ca.ulaval.glo4003.domain.user.exception.InvalidBirthDateException;
import ca.ulaval.glo4003.domain.user.exception.InvalidNameException;

public class UserAssembler {
  private final CustomDateAssembler customDateAssembler;

  public UserAssembler(CustomDateAssembler customDateAssembler) {
    this.customDateAssembler = customDateAssembler;
  }

  public User assemble(UserDto userDto) {
    CustomDate birthDate;

    validateNotNull(userDto);

    try {
      birthDate = customDateAssembler.assemble(userDto.birthDate);
    } catch (InvalidDateException exception) {
      throw new InvalidBirthDateException();
    }

    if (birthDate.isFuture()) throw new InvalidBirthDateException();

    return new User(userDto.name, birthDate, Sex.get(userDto.sex));
  }

  public UserDto assemble(User user) {
    UserDto userDto = new UserDto();
    userDto.name = user.getName();
    userDto.birthDate = user.getBirthDate().toString();
    userDto.sex = user.getSex().name().toLowerCase();

    return userDto;
  }

  private void validateNotNull(UserDto userDto) {
    if (userDto.name == null) {
      throw new InvalidNameException();
    } else if (userDto.birthDate == null) {
      throw new InvalidBirthDateException();
    }
  }
}
