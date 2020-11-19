package ca.ulaval.glo4003.reports.domain;

import ca.ulaval.glo4003.cars.domain.ConsumptionType;
import ca.ulaval.glo4003.funds.domain.Money;
import ca.ulaval.glo4003.times.domain.CustomDateTime;

public class ReportEventFactory {

  // TODO #262 : Create with ReportEvent.parkingAreaCode

  public ReportEvent create(ReportEventType type, Money profits) {
    return new ReportEvent(type, CustomDateTime.now(), profits);
  }

  public ReportEvent create(ReportEventType type, Money profits, ConsumptionType consumptionType) {
    return new ReportEvent(type, CustomDateTime.now(), profits, consumptionType);
  }
}