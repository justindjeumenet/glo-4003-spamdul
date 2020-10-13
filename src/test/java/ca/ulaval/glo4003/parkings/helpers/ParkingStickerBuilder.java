package ca.ulaval.glo4003.parkings.helpers;

import static ca.ulaval.glo4003.accounts.helpers.AccountMother.createAccountId;
import static ca.ulaval.glo4003.communications.helpers.EmailAddressMother.createEmailAddress;
import static ca.ulaval.glo4003.locations.helpers.PostalCodeMother.createPostalCode;
import static ca.ulaval.glo4003.parkings.helpers.ParkingAreaMother.createParkingAreaCode;
import static ca.ulaval.glo4003.parkings.helpers.ParkingStickerMother.*;
import static ca.ulaval.glo4003.times.helpers.DayMother.createDay;

import ca.ulaval.glo4003.accounts.domain.AccountId;
import ca.ulaval.glo4003.communications.domain.EmailAddress;
import ca.ulaval.glo4003.locations.domain.PostalCode;
import ca.ulaval.glo4003.parkings.domain.ParkingAreaCode;
import ca.ulaval.glo4003.parkings.domain.ParkingSticker;
import ca.ulaval.glo4003.parkings.domain.ParkingStickerCode;
import ca.ulaval.glo4003.parkings.domain.ReceptionMethod;
import ca.ulaval.glo4003.times.domain.DayOfWeek;

public class ParkingStickerBuilder {
  private ParkingStickerCode parkingStickerCode = createParkingStickerCode();
  private AccountId accountId = createAccountId();
  private ParkingAreaCode parkingAreaCode = createParkingAreaCode();
  private ReceptionMethod receptionMethod = createReceptionMethod();
  private PostalCode postalCode = createPostalCode();
  private EmailAddress emailAddress = createEmailAddress();
  private DayOfWeek validDay = createDay();

  private ParkingStickerBuilder() {}

  public static ParkingStickerBuilder aParkingSticker() {
    return new ParkingStickerBuilder();
  }

  public ParkingStickerBuilder withValidDay(String validDay) {
    this.validDay = DayOfWeek.get(validDay);
    return this;
  }

  public ParkingStickerBuilder withReceptionMethod(ReceptionMethod receptionMethod) {
    this.receptionMethod = receptionMethod;
    return this;
  }

  public ParkingSticker build() {
    ParkingSticker parkingSticker;

    switch (receptionMethod) {
      case POSTAL:
        parkingSticker =
            new ParkingSticker(accountId, parkingAreaCode, receptionMethod, postalCode, validDay);
        break;
      default:
      case EMAIL:
        parkingSticker =
            new ParkingSticker(accountId, parkingAreaCode, receptionMethod, emailAddress, validDay);
        break;
    }

    parkingSticker.setCode(parkingStickerCode);
    return parkingSticker;
  }
}
