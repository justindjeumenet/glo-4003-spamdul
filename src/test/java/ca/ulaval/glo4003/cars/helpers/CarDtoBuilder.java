package ca.ulaval.glo4003.cars.helpers;

import static ca.ulaval.glo4003.cars.helpers.CarMother.*;
import static ca.ulaval.glo4003.cars.helpers.LicensePlateMother.createLicensePlate;

import ca.ulaval.glo4003.cars.services.dto.CarDto;

public class CarDtoBuilder {
  private String licensePlate = createLicensePlate().toString();
  private String manufacturer = createManufacturer();
  private String model = createModel();
  private int year = createYear();
  private String consumptionTypes = createConsumptionTypeOtherThanZeroPollution().toString();

  private CarDtoBuilder() {}

  public static CarDtoBuilder aCarDto() {
    return new CarDtoBuilder();
  }

  public CarDtoBuilder withLicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
    return this;
  }

  public CarDtoBuilder withYear(int year) {
    this.year = year;
    return this;
  }

  public CarDtoBuilder withManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
    return this;
  }

  public CarDtoBuilder withModel(String model) {
    this.model = model;
    return this;
  }

  public CarDto build() {
    CarDto carDto = new CarDto();
    carDto.licensePlate = licensePlate;
    carDto.manufacturer = manufacturer;
    carDto.model = model;
    carDto.year = year;
    carDto.consumptionType = consumptionTypes;
    return carDto;
  }
}
