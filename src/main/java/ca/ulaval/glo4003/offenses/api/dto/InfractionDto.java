package ca.ulaval.glo4003.offenses.api.dto;

public class InfractionDto {
  public String infraction;
  public String code;
  public int montant;

  @Override
  public String toString() {
    return String.format(
        "OffenseFileDto{infraction='%s', code='%s', montant='%s'}", infraction, code, montant);
  }
}
