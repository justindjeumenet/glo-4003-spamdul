package ca.ulaval.glo4003.times.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class TimeDay extends TimeCalendar {
  public TimeDay(CustomDateTime customDateTime) {
    super(customDateTime);
  }

  @Override
  protected CustomDateTime firstDateTime() {
    Calendar day = thatDay();
    setAtMinimumTime(day);
    return toDateTime(day);
  }

  @Override
  protected CustomDateTime lastDateTime() {
    Calendar day = thatDay();
    setAtMaximumTime(day);
    return toDateTime(day);
  }

  private Calendar thatDay() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, getYear());
    calendar.set(Calendar.DAY_OF_YEAR, getDayOfYear());
    return calendar;
  }

  @Override
  public String toString() {
    return toPeriod().getStart().toDate().toString();
  }

  @Override
  public int compareTo(TimeCalendar other) {
    LocalDateTime day = toPeriod().getStart().toLocalDateTime();
    LocalDateTime otherDay = other.toPeriod().getStart().toLocalDateTime();
    int daysBetween = (int) ChronoUnit.DAYS.between(day, otherDay);
    return day.isBefore(otherDay) ? daysBetween : -daysBetween;
  }
}
