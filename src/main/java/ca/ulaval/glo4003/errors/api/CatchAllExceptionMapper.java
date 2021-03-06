package ca.ulaval.glo4003.errors.api;

import ca.ulaval.glo4003.errors.services.dto.ErrorDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CatchAllExceptionMapper implements ExceptionMapper<Exception> {

  private static final String BAD_REQUEST_ERROR = "Bad request";
  private static final String BAD_REQUEST_DESCRIPTION = "Request cannot be interpreted";

  private static final String INTERNAL_SERVER_ERROR = "Unexpected error";
  private static final String INTERNAL_SERVER_DESCRIPTION = "An unexpected error has occurred";

  @Override
  public Response toResponse(Exception exception) {
    Response.Status responseStatus;
    ErrorDto errorDto = new ErrorDto();

    if (exception instanceof WebApplicationException) {
      responseStatus = Response.Status.BAD_REQUEST;
      errorDto.error = BAD_REQUEST_ERROR;
      errorDto.description = BAD_REQUEST_DESCRIPTION;
    } else {
      responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
      errorDto.error = INTERNAL_SERVER_ERROR;
      errorDto.description = INTERNAL_SERVER_DESCRIPTION;
    }

    return Response.status(responseStatus)
        .entity(errorDto)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }
}
