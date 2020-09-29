package ca.ulaval.glo4003.api.communication;

import ca.ulaval.glo4003.domain.communication.exception.CommunicationException;
import ca.ulaval.glo4003.domain.communication.exception.EmailSendingFailedException;
import ca.ulaval.glo4003.domain.communication.exception.InvalidEmailAddressException;
import com.google.common.truth.Truth;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;

public class CommunicationExceptionMapperTest {

  private CommunicationExceptionMapper communicationExceptionMapper;

  @Before
  public void setUp() {
    communicationExceptionMapper = new CommunicationExceptionMapper();
  }

  @Test
  public void givenInvalidEmailAddressException_whenResponding_thenStatusIsBadRequest() {
    CommunicationException communicationException = new InvalidEmailAddressException();

    Response response = communicationExceptionMapper.toResponse(communicationException);

    Truth.assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
  }

  @Test
  public void givenEmailSendingFailedException_whenResponding_thenStatusIsInternalServerError() {
    CommunicationException communicationException = new EmailSendingFailedException();

    Response response = communicationExceptionMapper.toResponse(communicationException);

    Truth.assertThat(response.getStatus())
        .isEqualTo(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
  }
}
