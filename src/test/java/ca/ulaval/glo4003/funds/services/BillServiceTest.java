package ca.ulaval.glo4003.funds.services;

import static ca.ulaval.glo4003.funds.helpers.BillBuilder.aBill;
import static ca.ulaval.glo4003.funds.helpers.MoneyMother.createMoney;
import static ca.ulaval.glo4003.parkings.helpers.ParkingAreaBuilder.aParkingArea;
import static ca.ulaval.glo4003.parkings.helpers.ParkingStickerBuilder.aParkingSticker;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.funds.domain.Bill;
import ca.ulaval.glo4003.funds.domain.BillFactory;
import ca.ulaval.glo4003.funds.domain.Money;
import ca.ulaval.glo4003.parkings.domain.ParkingArea;
import ca.ulaval.glo4003.parkings.domain.ParkingPeriods;
import ca.ulaval.glo4003.parkings.domain.ParkingSticker;
import com.google.common.truth.Truth;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BillServiceTest {

  @Mock BillFactory billFactory;

  private BillService billService;

  private final ParkingSticker parkingSticker = aParkingSticker().build();
  private final Money parkingPeriodFee = createMoney();
  private final Bill bill = aBill().build();
  private ParkingArea parkingArea;

  @Before
  public void setUp() {
    billService = new BillService(billFactory);

    Map<ParkingPeriods, Money> feePerPeriod = new HashMap<>();
    feePerPeriod.put(ParkingPeriods.ONE_DAY, parkingPeriodFee);
    parkingArea = aParkingArea().withFeePerPeriod(feePerPeriod).build();

    when(billFactory.createForParkingSticker(parkingPeriodFee, parkingSticker.getReceptionMethod()))
        .thenReturn(bill);
  }

  @Test
  public void whenCreatingBillForParkingSticker_thenReturnBillForParkingSticker() {
    Bill receivedBill = billService.createBillForParkingSticker(parkingSticker, parkingArea);

    Truth.assertThat(receivedBill).isSameInstanceAs(bill);
  }
}
