package ca.ulaval.glo4003.users.services.converters;

import ca.ulaval.glo4003.times.domain.CustomDate;
import ca.ulaval.glo4003.times.domain.exceptions.InvalidDateException;
import ca.ulaval.glo4003.times.services.converters.CustomDateConverter;
import ca.ulaval.glo4003.users.domain.Sex;
import ca.ulaval.glo4003.users.domain.User;
import ca.ulaval.glo4003.users.domain.exceptions.FutureBirthDateException;
import ca.ulaval.glo4003.users.domain.exceptions.InvalidBirthDateException;
import ca.ulaval.glo4003.users.domain.exceptions.InvalidNameException;
import ca.ulaval.glo4003.users.services.dto.UserDto;

public class UserConverter {
  private static final String BIRTH_DATE_FORMAT = "dd-MM-yyyy";
  private final CustomDateConverter customDateConverter;

  public UserConverter() {
    this(new CustomDateConverter());
  }

  public UserConverter(CustomDateConverter customDateConverter) {
    this.customDateConverter = customDateConverter;
  }

  public User convert(UserDto userDto) {
    CustomDate birthDate;

    validateNotNull(userDto);

    try {
      birthDate = customDateConverter.convert(userDto.birthDate);
    } catch (InvalidDateException exception) {
      throw new InvalidBirthDateException(BIRTH_DATE_FORMAT);
    }

    if (birthDate.isFuture()) throw new FutureBirthDateException();

    return new User(userDto.name, birthDate, Sex.get(userDto.sex));
  }

  private void validateNotNull(UserDto userDto) {
    if (userDto.name == null) {
      throw new InvalidNameException();
    } else if (userDto.birthDate == null) {
      throw new InvalidBirthDateException(BIRTH_DATE_FORMAT);
    }
  }
}
