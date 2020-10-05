package ca.ulaval.glo4003.parkings.domain;

public class ParkingArea {
  private final ParkingAreaCode code;

  public ParkingArea(ParkingAreaCode code) {
    this.code = code;
  }

  public ParkingAreaCode getCode() {
    return code;
  }
}
