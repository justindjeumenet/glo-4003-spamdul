package ca.ulaval.glo4003.initiatives.services.dto;

public class InitiativeAvailableAmountDto {
  public double availableAmount;

  @Override
  public String toString() {
    return String.format("availableAmount{availableAmount='%s'}", availableAmount);
  }
}
