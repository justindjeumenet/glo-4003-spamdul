package ca.ulaval.glo4003.accesspasses.service;

import static ca.ulaval.glo4003.accesspasses.helpers.AccessPassBuilder.anAccessPass;
import static ca.ulaval.glo4003.accesspasses.helpers.AccessPassCodeDtoBuilder.anAccessPassCodeDto;
import static ca.ulaval.glo4003.accesspasses.helpers.AccessPassDtoBuilder.anAccessPassDto;
import static ca.ulaval.glo4003.accesspasses.helpers.AccessPassTypeBuilder.anAccessPassType;
import static ca.ulaval.glo4003.accounts.helpers.AccountBuilder.anAccount;
import static ca.ulaval.glo4003.cars.helpers.CarBuilder.aCar;
import static ca.ulaval.glo4003.cars.helpers.LicensePlateMother.createLicensePlate;
import static ca.ulaval.glo4003.funds.helpers.BillMother.createBillId;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.accesspasses.api.dto.AccessPassCodeDto;
import ca.ulaval.glo4003.accesspasses.api.dto.AccessPassDto;
import ca.ulaval.glo4003.accesspasses.assembler.AccessPassAssembler;
import ca.ulaval.glo4003.accesspasses.assembler.AccessPassCodeAssembler;
import ca.ulaval.glo4003.accesspasses.domain.*;
import ca.ulaval.glo4003.accesspasses.services.AccessPassService;
import ca.ulaval.glo4003.accounts.domain.Account;
import ca.ulaval.glo4003.accounts.services.AccountService;
import ca.ulaval.glo4003.cars.domain.Car;
import ca.ulaval.glo4003.cars.domain.ConsumptionType;
import ca.ulaval.glo4003.cars.domain.LicensePlate;
import ca.ulaval.glo4003.cars.services.CarService;
import ca.ulaval.glo4003.funds.domain.BillId;
import ca.ulaval.glo4003.funds.services.BillService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccessPassServiceTest {
  @Mock private AccessPassAssembler accessPassAssembler;
  @Mock private AccessPassFactory accessPassFactory;
  @Mock private CarService carService;
  @Mock private AccessPassTypeRepository accessPassTypeRepository;
  @Mock private BillService billService;
  @Mock private AccountService accountService;
  @Mock private AccessPassRepository accessPassRepository;
  @Mock private AccessPassCodeAssembler accessPassCodeAssembler;

  private AccessPassService accessPassService;

  private static final LicensePlate LICENSE_PLATE = createLicensePlate();
  private final Account account = anAccount().build();
  private final Car car = aCar().build();
  private final AccessPassType zeroPollutionAccessPassType = anAccessPassType().build();
  private final AccessPassType notZeroPollutionAccessPassType = anAccessPassType().build();
  private final BillId zeroPollutionBillId = createBillId();
  private final BillId notZeroPollutionBillId = createBillId();
  private final AccessPassCodeDto accessPassCodeDto = anAccessPassCodeDto().build();
  private AccessPassDto accessPassDto = anAccessPassDto().build();
  private AccessPass accessPass = anAccessPass().build();

  @Before
  public void setUp() {
    accessPassService =
        new AccessPassService(
            accessPassAssembler,
            accessPassFactory,
            carService,
            accessPassTypeRepository,
            accountService,
            billService,
            accessPassRepository,
            accessPassCodeAssembler);

    when(accessPassCodeAssembler.assemble(accessPass.getCode().toString()))
        .thenReturn(accessPass.getCode());
    when(accessPassRepository.get(accessPass.getCode())).thenReturn(accessPass);
  }

  @Test
  public void whenAddingAccessPass_thenAddNotZeroPollutionBillToAccount() {
    givenAccessPassDtoWithLicensePlate(LICENSE_PLATE);

    accessPassService.addAccessPass(accessPassDto, account.getId().toString());

    verify(accountService)
        .addAccessCodeToAccount(account.getId(), accessPass.getCode(), notZeroPollutionBillId);
  }

  // TODO : This test is flaky, it must be solved
  /*
  @Test
  public void givenNoLicensePlate_whenAddingAccessPass_thenAddZeroPollutionBillToAccount() {
    givenAccessPassDtoWithLicensePlate(null);

    accessPassService.addAccessPass(accessPassDto, account.getId().toString());

    verify(accountService)
        .addAccessCodeToAccount(account.getId(), accessPass.getCode(), zeroPollutionBillId);
  }
  */

  @Test
  public void whenAddingAccessPass_thenReturnAccessPassCode() {
    givenAccessPassDtoWithLicensePlate(LICENSE_PLATE);

    AccessPassCodeDto receivedAccessPassCodeDto =
        accessPassService.addAccessPass(accessPassDto, account.getId().toString());

    assertThat(receivedAccessPassCodeDto).isSameInstanceAs(accessPassCodeDto);
  }

  @Test
  public void whenGettingAccessPass_thenReturnAccessPassFromRepository() {
    AccessPass receivedAccessPass =
        accessPassService.getAccessPass(accessPass.getCode().toString());

    assertThat(receivedAccessPass).isSameInstanceAs(accessPass);
  }

  private void givenAccessPassDtoWithLicensePlate(LicensePlate licensePlate) {
    String stringLicensePlate = licensePlate == null ? null : licensePlate.toString();
    accessPassDto = anAccessPassDto().withLicensePlate(stringLicensePlate).build();
    accessPass = anAccessPass().withLicensePlate(licensePlate).build();

    setUpMocks();
  }

  private void setUpMocks() {
    when(accessPassAssembler.assemble(accessPassDto, account.getId().toString()))
        .thenReturn(accessPass);
    when(accountService.getAccount(account.getId().toString())).thenReturn(account);
    when(carService.getCar(accessPass.getLicensePlate())).thenReturn(car);
    when(accessPassFactory.create(accessPass)).thenReturn(accessPass);
    when(accessPassTypeRepository.findByConsumptionType(ConsumptionType.ZERO_POLLUTION))
        .thenReturn(zeroPollutionAccessPassType);
    when(accessPassTypeRepository.findByConsumptionType(car.getConsumptionType()))
        .thenReturn(notZeroPollutionAccessPassType);
    when(billService.addBillForAccessCode(
            zeroPollutionAccessPassType.getFeeForPeriod(AccessPeriod.ONE_DAY),
            accessPass.getCode(),
            car.getConsumptionType()))
        .thenReturn(zeroPollutionBillId);
    when(billService.addBillForAccessCode(
            notZeroPollutionAccessPassType.getFeeForPeriod(AccessPeriod.ONE_DAY),
            accessPass.getCode(),
            car.getConsumptionType()))
        .thenReturn(notZeroPollutionBillId);
    when(accessPassRepository.save(accessPass)).thenReturn(accessPass.getCode());
    when(accessPassCodeAssembler.assemble(accessPass.getCode())).thenReturn(accessPassCodeDto);
  }
}
