package ca.ulaval.glo4003.cars;

import ca.ulaval.glo4003.accounts.services.AccountService;
import ca.ulaval.glo4003.cars.services.CarService;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CarInjectorTest {

  @Mock private AccountService accountService;

  private CarInjector carInjector;

  @Before
  public void setUp() {
    carInjector = new CarInjector();
  }

  @Test
  public void whenCreatingCarService_thenReturnIt() {
    CarService carService = carInjector.createCarService(accountService);

    Truth.assertThat(carService).isNotNull();
  }
}
