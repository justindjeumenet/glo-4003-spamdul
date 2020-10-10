package ca.ulaval.glo4003.access.services;

import ca.ulaval.glo4003.access.api.dto.AccessPassCodeDto;
import ca.ulaval.glo4003.access.api.dto.AccessPassDto;
import ca.ulaval.glo4003.access.assembler.AccessPassAssembler;
import ca.ulaval.glo4003.access.assembler.AccessPassCodeAssembler;
import ca.ulaval.glo4003.access.domain.*;
import ca.ulaval.glo4003.accounts.domain.AccountId;
import ca.ulaval.glo4003.accounts.services.AccountService;
import ca.ulaval.glo4003.cars.domain.Car;
import ca.ulaval.glo4003.cars.domain.ConsumptionTypes;
import ca.ulaval.glo4003.cars.domain.LicensePlate;
import ca.ulaval.glo4003.cars.services.CarService;
import ca.ulaval.glo4003.funds.domain.BillId;
import ca.ulaval.glo4003.funds.domain.Money;
import ca.ulaval.glo4003.funds.services.BillService;
import java.util.UUID;

public class AccessService {
  private AccessPassAssembler accessPassAssembler;
  private AccessPassFactory accessPassFactory;
  private CarService carService;
  private AccessPassPriceByCarConsumptionRepository accessPassPriceByCarConsumptionRepository;
  private BillService billService;
  private AccountService accountService;
  private AccessPassRepository accessPassRepository;
  private AccessPassCodeAssembler accessPassCodeAssembler;

  public AccessService(
      AccessPassAssembler accessPassAssembler,
      AccessPassFactory accessPassFactory,
      CarService carService,
      AccessPassPriceByCarConsumptionRepository accessPassPriceByCarConsumptionRepository,
      AccountService accountService,
      BillService billService,
      AccessPassRepository accessPassRepository,
      AccessPassCodeAssembler accessPassCodeAssembler) {

    this.accessPassAssembler = accessPassAssembler;
    this.accessPassFactory = accessPassFactory;
    this.carService = carService;
    this.accessPassPriceByCarConsumptionRepository = accessPassPriceByCarConsumptionRepository;
    this.accountService = accountService;
    this.billService = billService;
    this.accessPassRepository = accessPassRepository;
    this.accessPassCodeAssembler = accessPassCodeAssembler;
  }

  public AccessPassCodeDto addAccessPass(AccessPassDto accessPassDto, String accountId) {

    AccessPass accessPass = accessPassAssembler.assemble(accessPassDto, accountId);
    accessPass = accessPassFactory.create(accessPass);

    LicensePlate licensePlate = accessPass.getLicensePlate();

    ConsumptionTypes consumptionTypes;
    if (licensePlate == null) {
      consumptionTypes = ConsumptionTypes.ZERO_POLLUTION;
    } else {
      Car car = carService.getCarByLicensePlate(accessPass.getLicensePlate());
      consumptionTypes = car.getConsumptionType();
    }

    Money moneyDue =
        accessPassPriceByCarConsumptionRepository
            .findByConsumptionType(consumptionTypes)
            .getFeeForPeriod(AccessPeriods.ONE_DAY);
    BillId billId = billService.addBillForAccessCode(moneyDue, accessPass.getAccessPassCode());
    accountService.addAccessCodeToAccount(
        new AccountId(UUID.fromString(accountId)), accessPass.getAccessPassCode(), billId);

    AccessPassCode accessPassCode = accessPassRepository.save(accessPass);

    // TODO test service

    return accessPassCodeAssembler.assemble(accessPassCode);
  }
}
