package ca.ulaval.glo4003.gates.helpers;

import static ca.ulaval.glo4003.gates.helpers.AccessStatusMother.createAccessStatus;

import ca.ulaval.glo4003.gates.services.dto.AccessStatusDto;

public class AccessStatusDtoBuilder {
  private String accessStatus = createAccessStatus().toString();

  private AccessStatusDtoBuilder() {}

  public static AccessStatusDtoBuilder anAccessStatusDto() {
    return new AccessStatusDtoBuilder();
  }

  public AccessStatusDtoBuilder withAccessStatus(String accessStatus) {
    this.accessStatus = accessStatus;
    return this;
  }

  public AccessStatusDto build() {
    AccessStatusDto accessStatusDto = new AccessStatusDto();
    accessStatusDto.accessStatus = accessStatus;
    return accessStatusDto;
  }
}
