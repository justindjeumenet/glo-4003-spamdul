package ca.ulaval.glo4003.times.assemblers;

import ca.ulaval.glo4003.times.api.dto.DateTimeDto;
import ca.ulaval.glo4003.times.domain.CustomDateTime;
import ca.ulaval.glo4003.times.exceptions.InvalidDateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CustomDateTimeAssembler {
  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

  public CustomDateTime assemble(DateTimeDto dateTimeDto) {
    LocalDateTime localDateTime;

    try {
      localDateTime = LocalDateTime.parse(dateTimeDto.dateTime, FORMATTER);
    } catch (DateTimeParseException exception) {
      throw new InvalidDateTimeException();
    }

    return new CustomDateTime(localDateTime);
  }
}
