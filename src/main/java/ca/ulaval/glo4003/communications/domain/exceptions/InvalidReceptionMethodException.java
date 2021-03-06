package ca.ulaval.glo4003.communications.domain.exceptions;

import ca.ulaval.glo4003.communications.domain.ReceptionMethod;
import ca.ulaval.glo4003.errors.domain.ErrorCode;
import ca.ulaval.glo4003.errors.domain.exceptions.ApplicationException;

public class InvalidReceptionMethodException extends ApplicationException {
  private static final String ERROR = "Invalid reception method";
  private static final String DESCRIPTION = "Reception method should be one of %s";
  private static final ErrorCode CODE = ErrorCode.INVALID_REQUEST;

  public InvalidReceptionMethodException() {
    super(ERROR, DESCRIPTION, CODE);
  }

  @Override
  public String getDescription() {
    return String.format(DESCRIPTION, enumerateValues(ReceptionMethod.class));
  }
}
