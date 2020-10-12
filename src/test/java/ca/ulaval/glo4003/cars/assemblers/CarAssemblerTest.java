package ca.ulaval.glo4003.cars.assemblers;

import static ca.ulaval.glo4003.accounts.helpers.AccountMother.createAccountId;
import static ca.ulaval.glo4003.cars.helpers.CarBuilderDtoBuilder.aCarDto;
import static ca.ulaval.glo4003.cars.helpers.LicensePlateMother.createLicensePlate;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.accounts.assemblers.AccountIdAssembler;
import ca.ulaval.glo4003.accounts.domain.AccountId;
import ca.ulaval.glo4003.cars.api.dto.CarDto;
import ca.ulaval.glo4003.cars.domain.Car;
import ca.ulaval.glo4003.cars.domain.LicensePlate;
import ca.ulaval.glo4003.cars.exceptions.InvalidCarYearException;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CarAssemblerTest {

  @Mock private LicensePlateAssembler licensePlateAssembler;
  @Mock private AccountIdAssembler accountIdAssembler;

  private CarAssembler carAssembler;

  private static final LicensePlate LICENSE_PLATE = createLicensePlate();
  private static final AccountId ACCOUNT_ID = createAccountId();
  private static final int INVALID_YEAR = 100000;

  private final CarDto carDto =
      aCarDto()
          .withLicensePlate(LICENSE_PLATE.toString())
          .withAccountId(ACCOUNT_ID.toString())
          .build();

  @Before
  public void setup() {
    carAssembler = new CarAssembler(licensePlateAssembler, accountIdAssembler);

    when(licensePlateAssembler.assemble(LICENSE_PLATE.toString())).thenReturn(LICENSE_PLATE);
    when(accountIdAssembler.assemble(ACCOUNT_ID.toString())).thenReturn(ACCOUNT_ID);
  }

  @Test
  public void whenAssembling_shouldCarWithLicensePlate() {
    Car car = carAssembler.create(carDto);

    Truth.assertThat(car.getLicensePlate().toString()).isEqualTo(carDto.licensePlate);
  }

  @Test
  public void whenAssembling_shouldCarWithAccountId() {
    Car car = carAssembler.create(carDto);

    Truth.assertThat(car.getAccountId().toString()).isEqualTo(carDto.accountId);
  }

  @Test
  public void whenAssembling_shouldCarWithManufacturer() {
    Car car = carAssembler.create(carDto);

    Truth.assertThat(car.getManufacturer()).isEqualTo(carDto.manufacturer);
  }

  @Test
  public void whenAssembling_shouldCarWithModel() {
    Car car = carAssembler.create(carDto);

    Truth.assertThat(car.getModel()).isEqualTo(carDto.model);
  }

  @Test
  public void whenAssembling_shouldCarWithYear() {
    Car car = carAssembler.create(carDto);

    Truth.assertThat(car.getYear()).isEqualTo(carDto.year);
  }

  @Test(expected = InvalidCarYearException.class)
  public void givenCarWithInvalidYear_whenAssembling_shouldThrowInvalidYearException() {
    CarDto carDto = aCarDto().withYear(INVALID_YEAR).build();

    carAssembler.create(carDto);
  }
}
