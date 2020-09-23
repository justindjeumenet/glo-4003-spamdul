package ca.ulaval.glo4003.domain.parking.helpers;

import static ca.ulaval.glo4003.domain.account.helpers.AccountMother.createAccountId;
import static ca.ulaval.glo4003.domain.parking.helpers.ParkingAreaMother.createParkingAreaCode;
import static ca.ulaval.glo4003.domain.parking.helpers.ParkingStickerMother.createParkingStickerCode;
import static ca.ulaval.glo4003.domain.parking.helpers.ParkingStickerMother.createReceptionMethod;

import ca.ulaval.glo4003.domain.account.AccountId;
import ca.ulaval.glo4003.domain.parking.ParkingAreaCode;
import ca.ulaval.glo4003.domain.parking.ParkingSticker;
import ca.ulaval.glo4003.domain.parking.ParkingStickerCode;
import ca.ulaval.glo4003.domain.parking.ReceptionMethods;

public class ParkingStickerBuilder {
  private ParkingStickerCode parkingStickerCode = createParkingStickerCode();
  private AccountId accountId = createAccountId();
  private ParkingAreaCode parkingAreaCode = createParkingAreaCode();
  private ReceptionMethods receptionMethod = createReceptionMethod();

  private ParkingStickerBuilder() {}

  public static ParkingStickerBuilder aParkingSticker() {
    return new ParkingStickerBuilder();
  }

  public ParkingSticker build() {
    ParkingSticker parkingSticker = new ParkingSticker(accountId, parkingAreaCode, receptionMethod);
    parkingSticker.setCode(parkingStickerCode);
    return parkingSticker;
  }
}
