package ca.ulaval.glo4003.funds.services;

import static ca.ulaval.glo4003.funds.helpers.MoneyMother.createMoney;
import static ca.ulaval.glo4003.funds.helpers.MoneyMother.createRatio;

import ca.ulaval.glo4003.funds.domain.Money;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;

public class SustainableMobilityProgramAllocationCalculatorTest {

  private SustainableMobilityProgramAllocationCalculator
      sustainableMobilityProgramAllocationCalculator;
  private Money amountCalculated = createMoney();
  private double ratio = createRatio();

  @Before
  public void setUp() {
    sustainableMobilityProgramAllocationCalculator =
        new SustainableMobilityProgramAllocationCalculator();
  }

  @Test
  public void givenRatio_whenMoneyIsCalculated_thenReturnCalculatedMoney() {
    Money calculatedMoney =
        sustainableMobilityProgramAllocationCalculator.calculate(amountCalculated, ratio);

    Money expectedMoney = Money.fromDouble(ratio * amountCalculated.toDouble());

    Truth.assertThat(calculatedMoney).isEqualTo(expectedMoney);
  }
}
