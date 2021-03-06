package ca.ulaval.glo4003.parkings.services;

import ca.ulaval.glo4003.accounts.services.AccountService;
import ca.ulaval.glo4003.funds.domain.Bill;
import ca.ulaval.glo4003.funds.services.BillService;
import ca.ulaval.glo4003.parkings.domain.*;
import ca.ulaval.glo4003.parkings.services.assemblers.ParkingStickerCodeAssembler;
import ca.ulaval.glo4003.parkings.services.converters.ParkingStickerConverter;
import ca.ulaval.glo4003.parkings.services.dto.ParkingStickerCodeDto;
import ca.ulaval.glo4003.parkings.services.dto.ParkingStickerDto;
import java.util.logging.Logger;

public class ParkingStickerService extends ParkingStickerCreationObservable {
  private final Logger logger = Logger.getLogger(ParkingStickerService.class.getName());
  private final ParkingStickerConverter parkingStickerConverter;
  private final ParkingStickerCodeAssembler parkingStickerCodeAssembler;
  private final ParkingStickerFactory parkingStickerFactory;
  private final AccountService accountService;
  private final ParkingAreaRepository parkingAreaRepository;
  private final BillService billService;

  public ParkingStickerService(
      ParkingStickerFactory parkingStickerFactory,
      AccountService accountService,
      ParkingAreaRepository parkingAreaRepository,
      BillService billService) {
    this(
        new ParkingStickerConverter(),
        new ParkingStickerCodeAssembler(),
        parkingStickerFactory,
        accountService,
        parkingAreaRepository,
        billService);
  }

  public ParkingStickerService(
      ParkingStickerConverter parkingStickerConverter,
      ParkingStickerCodeAssembler parkingStickerCodeAssembler,
      ParkingStickerFactory parkingStickerFactory,
      AccountService accountService,
      ParkingAreaRepository parkingAreaRepository,
      BillService billService) {
    this.parkingStickerConverter = parkingStickerConverter;
    this.parkingStickerCodeAssembler = parkingStickerCodeAssembler;
    this.accountService = accountService;
    this.parkingStickerFactory = parkingStickerFactory;
    this.parkingAreaRepository = parkingAreaRepository;
    this.billService = billService;
  }

  public ParkingStickerCodeDto addParkingSticker(
      ParkingStickerDto parkingStickerDto, String accountId) {
    logger.info(String.format("Add new parking sticker %s", parkingStickerDto));

    ParkingSticker parkingSticker = parkingStickerConverter.convert(parkingStickerDto, accountId);
    accountService.getAccount(accountId);
    parkingSticker = parkingStickerFactory.create(parkingSticker);

    ParkingArea parkingArea = parkingAreaRepository.get(parkingSticker.getParkingAreaCode());

    Bill bill = billService.addBillForParkingSticker(parkingSticker, parkingArea);
    accountService.addParkingStickerToAccount(parkingSticker.getAccountId(), parkingSticker, bill);

    notifyParkingStickerCreated(parkingSticker);

    return parkingStickerCodeAssembler.assemble(parkingSticker.getCode());
  }
}
