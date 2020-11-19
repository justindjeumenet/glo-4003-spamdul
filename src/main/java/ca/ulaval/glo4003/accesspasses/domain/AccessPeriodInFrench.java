package ca.ulaval.glo4003.accesspasses.domain;

import ca.ulaval.glo4003.accesspasses.exceptions.InvalidAccessPeriodException;
import java.util.HashMap;
import java.util.Map;

public enum AccessPeriodInFrench {
  UNE_HEURE("1h"), // TODO put back in for 3.1
  UNE_JOURNEE("1j"), // TODO idem
  UNE_JOURNEE_PAR_SEMAINE_POUR_SESSION("1j/semaine/session"),
  UNE_SESSION("1 session"),
  DEUX_SESSIONS("2 session"),
  TROIS_SESSIONS("3 session");

  private String period;
  private static final Map<String, AccessPeriodInFrench> lookup = new HashMap<>();

  static {
    for (AccessPeriodInFrench period : AccessPeriodInFrench.values()) {
      lookup.put(period.toString(), period);
    }
  }

  AccessPeriodInFrench(String period) {
    this.period = period;
  }

  @Override
  public String toString() {
    return period;
  }

  public static AccessPeriodInFrench get(String period) {
    if (period == null) throw new InvalidAccessPeriodException();

    AccessPeriodInFrench foundPeriod = lookup.get(period.toLowerCase());

    if (foundPeriod == null) throw new InvalidAccessPeriodException();

    return foundPeriod;
  }
}
