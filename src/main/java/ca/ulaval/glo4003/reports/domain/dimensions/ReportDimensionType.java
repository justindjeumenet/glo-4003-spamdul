package ca.ulaval.glo4003.reports.domain.dimensions;

public enum ReportDimensionType {
  CONSUMPTION_TYPE("consumptionType"),
  PARKING_AREA("parkingArea");

  String reportDimensionType;

  ReportDimensionType(String reportDimensionType) {
    this.reportDimensionType = reportDimensionType;
  }

  @Override
  public String toString() {
    return reportDimensionType;
  }
}
