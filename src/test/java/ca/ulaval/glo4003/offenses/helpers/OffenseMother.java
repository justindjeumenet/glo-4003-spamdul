package ca.ulaval.glo4003.offenses.helpers;

import static ca.ulaval.glo4003.interfaces.helpers.Randomizer.randomEnum;

import ca.ulaval.glo4003.offenses.domain.OffenseCodes;
import com.github.javafaker.Faker;

public class OffenseMother {
  public static String createReasonText() {
    return Faker.instance().superhero().descriptor();
  }

  public static OffenseCodes createReasonCode() {
    return randomEnum(OffenseCodes.class);
  }

  public static double createAmount() {
    return Faker.instance().number().numberBetween(1, 200);
  }
}