package ca.ulaval.glo4003.reports.domain.metrics;

import ca.ulaval.glo4003.reports.domain.ReportPeriodData;

public abstract class ReportMetric {

  public abstract ReportMetricType getType();

  public abstract void calculate(ReportPeriodData data);

  protected ReportMetricData toMetricData(double value) {
    return new ReportMetricData() {
      @Override
      public ReportMetricType getType() {
        return ReportMetric.this.getType();
      }

      @Override
      public double getValue() {
        return value;
      }
    };
  }
}
