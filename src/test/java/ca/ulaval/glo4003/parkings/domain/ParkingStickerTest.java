package ca.ulaval.glo4003.parkings.domain;

import static ca.ulaval.glo4003.parkings.helpers.ParkingStickerBuilder.aParkingSticker;

import ca.ulaval.glo4003.times.domain.Days;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParkingStickerTest {
  private ParkingSticker parkingSticker;

  @Before
  public void setUp() {
    parkingSticker = aParkingSticker().withValidDay("monday").build();
  }

  @Test
  public void givenValidDay_whenValidateParkingStickerDay_thenReturnTrue() {
    Truth.assertThat(parkingSticker.validateParkingStickerDay(Days.MONDAY)).isEqualTo(true);
  }

  @Test
  public void givenInvalidDay_whenValidateParkingStickerDay_thenReturnFalse() {
    Truth.assertThat(parkingSticker.validateParkingStickerDay(Days.FRIDAY)).isEqualTo(false);
  }
}