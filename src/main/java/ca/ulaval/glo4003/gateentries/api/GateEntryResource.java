package ca.ulaval.glo4003.gateentries.api;

import ca.ulaval.glo4003.gateentries.api.dto.DayOfWeekDto;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/gateEntry")
public interface GateEntryResource {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/validateAccessPass/{code}")
  Response validateAccessPass(DayOfWeekDto dayOfWeekDto, @PathParam("code") String accessPassCode);
}