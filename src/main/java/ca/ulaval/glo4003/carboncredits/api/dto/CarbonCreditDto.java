package ca.ulaval.glo4003.carboncredits.api.dto;

public class CarbonCreditDto {
  public double carbonCredits;

  @Override
  public String toString() {
    return String.format("CarbonCredits{carbonCredits='%s'}", carbonCredits);
  }
}
