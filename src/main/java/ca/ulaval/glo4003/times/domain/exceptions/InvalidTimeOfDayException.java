package ca.ulaval.glo4003.times.domain.exceptions;

<<<<<<< HEAD
public class InvalidTimeOfDayException extends TimeException {
  private static final String ERROR = "Invalid time of day";
  private static final String DESCRIPTION = "Invalid time of day";

  public InvalidTimeOfDayException() {
    super(ERROR, DESCRIPTION);
=======
import ca.ulaval.glo4003.errors.domain.ErrorCode;
import ca.ulaval.glo4003.errors.domain.exceptions.ApplicationException;

public class InvalidTimeOfDayException extends ApplicationException {
  private static final String ERROR = "Invalid time of day";
  private static final String DESCRIPTION = "Time of day must be of format : %s";
  private static final ErrorCode CODE = ErrorCode.INVALID_REQUEST;
  private final String format;

  public InvalidTimeOfDayException(String format) {
    super(ERROR, DESCRIPTION, CODE);
    this.format = format;
  }

  @Override
  public String getDescription() {
    return String.format(DESCRIPTION, format);
>>>>>>> 0c9b47b0 ([FEATURE] Write time of day format)
  }
}
