package ca.ulaval.glo4003.cars.assemblers;

import ca.ulaval.glo4003.cars.api.dto.CarDto;
import ca.ulaval.glo4003.cars.domain.Car;
import ca.ulaval.glo4003.cars.exceptions.InvalidCarYearException;
import java.time.LocalDate;

public class CarAssembler {

  private final LicensePlateAssembler licensePlateAssembler;

  public CarAssembler(LicensePlateAssembler licensePlateAssembler) {
    this.licensePlateAssembler = licensePlateAssembler;
  }

  public Car create(CarDto carDto) {
    validateYear(carDto.year);
    return new Car(
        carDto.manufacturer,
        carDto.model,
        carDto.year,
        licensePlateAssembler.assemble(carDto.licensePlate));
  }

  private void validateYear(int year) {
    int currentYear = LocalDate.now().getYear();
    if (currentYear + 1 <= year) {
      throw new InvalidCarYearException(currentYear);
    }
  }
}