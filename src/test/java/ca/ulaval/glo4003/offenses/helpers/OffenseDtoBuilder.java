package ca.ulaval.glo4003.offenses.helpers;

import static ca.ulaval.glo4003.offenses.helpers.OffenseMother.*;

import ca.ulaval.glo4003.offenses.api.dto.OffenseDto;
import ca.ulaval.glo4003.offenses.domain.OffenseCodes;

public class OffenseDtoBuilder {
  private String description = createReasonText();
  private OffenseCodes code = createReasonCode();
  private double amount = createAmount();

  private OffenseDtoBuilder() {}

  public static OffenseDtoBuilder anOffenseDto() {
    return new OffenseDtoBuilder();
  }

  public OffenseDtoBuilder withReasonText(String description) {
    this.description = description;
    return this;
  }

  public OffenseDtoBuilder withReasonCode(OffenseCodes code) {
    this.code = code;
    return this;
  }

  public OffenseDtoBuilder withAmount(double amount) {
    this.amount = amount;
    return this;
  }

  public OffenseDto build() {
    OffenseDto offenseDto = new OffenseDto();

    offenseDto.description = description;
    offenseDto.code = code.toString();
    offenseDto.amount = amount;

    return offenseDto;
  }
}
