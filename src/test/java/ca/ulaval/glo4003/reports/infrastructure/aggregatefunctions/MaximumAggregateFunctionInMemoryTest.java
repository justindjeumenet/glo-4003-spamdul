package ca.ulaval.glo4003.reports.infrastructure.aggregatefunctions;

import static ca.ulaval.glo4003.reports.helpers.ReportMetricDataBuilder.aReportMetricData;
import static ca.ulaval.glo4003.reports.helpers.ReportPeriodBuilder.aReportPeriod;
import static ca.ulaval.glo4003.reports.helpers.ReportPeriodDataBuilder.aReportPeriodData;
import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.reports.domain.ReportPeriod;
import ca.ulaval.glo4003.reports.domain.ReportPeriodData;
import ca.ulaval.glo4003.reports.domain.metrics.ReportMetricData;
import ca.ulaval.glo4003.reports.domain.metrics.ReportMetricType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class MaximumAggregateFunctionInMemoryTest {

  private ReportAggregateFunctionInMemory aggregateFunction;

  private final ReportMetricType metricType = ReportMetricType.PROFITS;
  private final ReportMetricType otherMetricType = ReportMetricType.GATE_ENTRIES;
  private final ReportMetricData highestMetricData =
      aReportMetricData().withType(metricType).withValue(10).build();
  private final ReportMetricData lowestMetricData =
      aReportMetricData().withType(metricType).withValue(5).build();
  private final ReportMetricData otherMetricData =
      aReportMetricData().withType(otherMetricType).withValue(20).build();

  @Before
  public void setUp() {
    aggregateFunction = new MaximumAggregateFunctionInMemory();
  }

  @Test
  public void whenAggregating_thenSetPeriodNameWithMaximum() {
    List<ReportPeriod> periods = Collections.emptyList();

    List<ReportPeriod> aggregatedPeriods =
        aggregateFunction.aggregate(periods, Collections.singletonList(metricType));

    assertThat(aggregatedPeriods.get(0).getName()).contains(" (maximum)");
  }

  @Test
  public void whenAggregating_thenSetSingleData() {
    List<ReportPeriod> periods = Collections.emptyList();

    List<ReportPeriod> aggregatedPeriods =
        aggregateFunction.aggregate(periods, Collections.singletonList(metricType));

    assertThat(aggregatedPeriods.get(0).getData()).hasSize(1);
  }

  @Test
  public void whenAggregating_thenSetNoDimensions() {
    List<ReportPeriod> periods = Collections.emptyList();

    List<ReportPeriod> aggregatedPeriods =
        aggregateFunction.aggregate(periods, Collections.singletonList(metricType));

    assertThat(aggregatedPeriods.get(0).getData().get(0).getDimensions()).hasSize(0);
  }

  @Test
  public void whenAggregating_thenSetMetricWithType() {
    List<ReportPeriod> periods = Collections.emptyList();

    List<ReportPeriod> aggregatedPeriods =
        aggregateFunction.aggregate(periods, Collections.singletonList(metricType));

    assertThat(aggregatedPeriods.get(0).getData().get(0).getMetrics()).hasSize(1);
    assertThat(aggregatedPeriods.get(0).getData().get(0).getMetrics().get(0).getType())
        .isEqualTo(metricType);
  }

  @Test
  public void givenNoPeriod_whenAggregating_thenReturnZero() {
    double expectedAverage = 0d;
    List<ReportPeriod> periods = Collections.emptyList();

    List<ReportPeriod> aggregatedPeriods =
        aggregateFunction.aggregate(periods, Collections.singletonList(metricType));

    assertThat(aggregatedPeriods.get(0).getData().get(0).getMetrics()).hasSize(1);
    assertThat(aggregatedPeriods.get(0).getData().get(0).getMetrics().get(0).getValue())
        .isEqualTo(expectedAverage);
  }

  @Test
  public void givenNoPeriod_whenAggregating_thenReturnNoPeriodName() {
    List<ReportPeriod> periods = Collections.emptyList();

    List<ReportPeriod> aggregatedPeriods =
        aggregateFunction.aggregate(periods, Collections.singletonList(metricType));

    assertThat(aggregatedPeriods.get(0).getName()).contains("no period");
  }

  @Test
  public void givenSinglePeriodWithMetricType_whenAggregating_thenReturnThatPeriodMetricValue() {
    double expectedMaximum = highestMetricData.getValue();
    ReportPeriodData periodDataWithMetricType =
        aReportPeriodData().withMetrics(Collections.singletonList(highestMetricData)).build();
    ReportPeriod periodWithMetricType =
        aReportPeriod().withData(Collections.singletonList(periodDataWithMetricType)).build();
    List<ReportPeriod> periods = Collections.singletonList(periodWithMetricType);

    List<ReportPeriod> aggregatedPeriods =
        aggregateFunction.aggregate(periods, Collections.singletonList(metricType));

    assertThat(aggregatedPeriods.get(0).getData().get(0).getMetrics()).hasSize(1);
    assertThat(aggregatedPeriods.get(0).getData().get(0).getMetrics().get(0).getValue())
        .isEqualTo(expectedMaximum);
  }

  @Test
  public void givenSinglePeriodWithOtherMetricType_whenAggregating_thenReturnZero() {
    double expectedMaximum = 0d;
    ReportPeriodData periodDataWithOtherMetricType =
        aReportPeriodData().withMetrics(Collections.singletonList(otherMetricData)).build();
    ReportPeriod periodWithOtherMetricType =
        aReportPeriod().withData(Collections.singletonList(periodDataWithOtherMetricType)).build();
    List<ReportPeriod> periods = Collections.singletonList(periodWithOtherMetricType);

    List<ReportPeriod> aggregatedPeriods =
        aggregateFunction.aggregate(periods, Collections.singletonList(metricType));

    assertThat(aggregatedPeriods.get(0).getData().get(0).getMetrics()).hasSize(1);
    assertThat(aggregatedPeriods.get(0).getData().get(0).getMetrics().get(0).getValue())
        .isEqualTo(expectedMaximum);
  }

  @Test
  public void
      givenMultiplePeriodsWithMetricType_whenAggregating_thenReturnPeriodWithMaximumOfMetricValue() {
    double expectedMaximum = highestMetricData.getValue();
    ReportPeriodData firstPeriodDataWithMetricType =
        aReportPeriodData().withMetrics(Collections.singletonList(highestMetricData)).build();
    ReportPeriodData secondPeriodDataWithMetricType =
        aReportPeriodData().withMetrics(Collections.singletonList(lowestMetricData)).build();
    ReportPeriod firstPeriodWithMetricType =
        aReportPeriod().withData(Collections.singletonList(firstPeriodDataWithMetricType)).build();
    ReportPeriod secondPeriodWithMetricType =
        aReportPeriod().withData(Collections.singletonList(secondPeriodDataWithMetricType)).build();
    List<ReportPeriod> periods =
        Arrays.asList(firstPeriodWithMetricType, secondPeriodWithMetricType);

    List<ReportPeriod> aggregatedPeriods =
        aggregateFunction.aggregate(periods, Collections.singletonList(metricType));

    assertThat(aggregatedPeriods.get(0).getData().get(0).getMetrics()).hasSize(1);
    assertThat(aggregatedPeriods.get(0).getData().get(0).getMetrics().get(0).getValue())
        .isEqualTo(expectedMaximum);
  }

  @Test
  public void
      givenMultiplePeriodsWithDifferentMetricTypes_whenAggregating_thenReturnMaximumOfMetricValueForMetricType() {
    double expectedMaximum = highestMetricData.getValue();
    ReportPeriodData periodDataWithMetricType =
        aReportPeriodData().withMetrics(Collections.singletonList(highestMetricData)).build();
    ReportPeriodData periodDataWithOtherMetricType =
        aReportPeriodData().withMetrics(Collections.singletonList(otherMetricData)).build();
    ReportPeriod periodWithMetricType =
        aReportPeriod().withData(Collections.singletonList(periodDataWithMetricType)).build();
    ReportPeriod periodWithOtherMetricType =
        aReportPeriod().withData(Collections.singletonList(periodDataWithOtherMetricType)).build();
    List<ReportPeriod> periods = Arrays.asList(periodWithMetricType, periodWithOtherMetricType);

    List<ReportPeriod> aggregatedPeriods =
        aggregateFunction.aggregate(periods, Collections.singletonList(metricType));

    assertThat(aggregatedPeriods.get(0).getData().get(0).getMetrics()).hasSize(1);
    assertThat(aggregatedPeriods.get(0).getData().get(0).getMetrics().get(0).getValue())
        .isEqualTo(expectedMaximum);
  }

  @Test
  public void
      givenSinglePeriodWithMultipleDataOfMetricType_whenAggregating_thenReturnMaximumOfMetricValueForMetricType() {
    double expectedMaximum = highestMetricData.getValue() + lowestMetricData.getValue();
    ReportPeriodData periodDataWithMetricType =
        aReportPeriodData().withMetrics(Collections.singletonList(highestMetricData)).build();
    ReportPeriodData otherPeriodDataWithMetricType =
        aReportPeriodData().withMetrics(Collections.singletonList(lowestMetricData)).build();
    ReportPeriod periodWithMetricType =
        aReportPeriod()
            .withData(Arrays.asList(periodDataWithMetricType, otherPeriodDataWithMetricType))
            .build();
    List<ReportPeriod> periods = Collections.singletonList(periodWithMetricType);

    List<ReportPeriod> aggregatedPeriods =
        aggregateFunction.aggregate(periods, Collections.singletonList(metricType));

    assertThat(aggregatedPeriods.get(0).getData().get(0).getMetrics()).hasSize(1);
    assertThat(aggregatedPeriods.get(0).getData().get(0).getMetrics().get(0).getValue())
        .isEqualTo(expectedMaximum);
  }
}
