package ca.ulaval.glo4003.domain.car;

import ca.ulaval.glo4003.api.car.dto.CarDTO;
import java.time.LocalDate;

public class CarValidator {

  public CarValidator() {}

  public void validate(CarDTO carDTO) {
    validateLicensePlate(carDTO.getLicensePlate());
    validateYear(carDTO.getYear());
  }

  private void validateLicensePlate(String licensePlate) {
    if (licensePlate.length() < 2 || licensePlate.length() > 7) {
      throw new InvalidCarException("Plaque d'immatriculation invalide");
    }
  }

  private void validateYear(int year) {
    if (LocalDate.now().getYear() + 1 <= year) {
      throw new InvalidCarException("Année de l'automobile invalide");
    }
  }
}
