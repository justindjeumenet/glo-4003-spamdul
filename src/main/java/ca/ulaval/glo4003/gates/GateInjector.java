package ca.ulaval.glo4003.gates;

import ca.ulaval.glo4003.accesspasses.services.AccessPassService;
import ca.ulaval.glo4003.cars.assemblers.LicensePlateAssembler;
import ca.ulaval.glo4003.gates.api.GateResource;
import ca.ulaval.glo4003.gates.api.GateResourceImplementation;
import ca.ulaval.glo4003.gates.assemblers.DayOfWeekAssembler;
import ca.ulaval.glo4003.gates.services.GateService;
import ca.ulaval.glo4003.parkings.assemblers.AccessStatusAssembler;

public class GateInjector {

  public GateResource createGateResource(AccessPassService accessPassService) {
    DayOfWeekAssembler dayOfWeekAssembler = new DayOfWeekAssembler();
    AccessStatusAssembler accessStatusAssembler = new AccessStatusAssembler();
    LicensePlateAssembler licensePlateAssembler = new LicensePlateAssembler();
    GateService gateService =
        new GateService(
            accessPassService, dayOfWeekAssembler, accessStatusAssembler, licensePlateAssembler);

    return new GateResourceImplementation(gateService);
  }
}
