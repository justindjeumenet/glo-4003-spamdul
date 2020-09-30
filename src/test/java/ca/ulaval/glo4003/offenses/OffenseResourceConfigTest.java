package ca.ulaval.glo4003.offenses;

import ca.ulaval.glo4003.domain.parking.ParkingStickerRepository;
import ca.ulaval.glo4003.offenses.api.OffenseResource;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OffenseResourceConfigTest {

  @Mock private ParkingStickerRepository parkingStickerRepository;
  private OffenseResourceConfig offenseResourceConfig;

  @Before
  public void setUp() {
    offenseResourceConfig = new OffenseResourceConfig();
  }

  @Test
  public void whenCreatingOffenseResource_thenReturnIt() {
    OffenseResource offenseResource =
        offenseResourceConfig.createOffenseResource(parkingStickerRepository);

    Truth.assertThat(offenseResource).isNotNull();
  }
}
