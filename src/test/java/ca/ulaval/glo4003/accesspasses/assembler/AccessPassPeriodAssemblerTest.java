package ca.ulaval.glo4003.accesspasses.assembler;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.accesspasses.domain.AccessPeriod;
import ca.ulaval.glo4003.accesspasses.domain.AccessPeriodInFrench;
import org.junit.Before;
import org.junit.Test;

public class AccessPassPeriodAssemblerTest {
  private AccessPassPeriodAssembler accessPassPeriodAssembler;

  @Before
  public void setUp() {
    accessPassPeriodAssembler = new AccessPassPeriodAssembler();
  }

  @Test
  public void givenUneHeure_whenAssembling_thenReturnOneHour() {
    AccessPeriodInFrench accessPeriodInFrench = AccessPeriodInFrench.UNE_HEURE;

    AccessPeriod accessPeriod = accessPassPeriodAssembler.assemble(accessPeriodInFrench);

    assertThat(accessPeriod).isEqualTo(AccessPeriod.ONE_HOUR);
  }

  @Test
  public void givenUneJournee_whenAssembling_thenReturnOneDay() {
    AccessPeriodInFrench accessPeriodInFrench = AccessPeriodInFrench.UNE_JOURNEE;

    AccessPeriod accessPeriod = accessPassPeriodAssembler.assemble(accessPeriodInFrench);

    assertThat(accessPeriod).isEqualTo(AccessPeriod.ONE_DAY);
  }

  @Test
  public void
      givenUneJourneeParSemainePourSession_whenAssembling_thenReturnOneDayByWeekForSession() {
    AccessPeriodInFrench accessPeriodInFrench =
        AccessPeriodInFrench.UNE_JOURNEE_PAR_SEMAINE_POUR_SESSION;

    AccessPeriod accessPeriod = accessPassPeriodAssembler.assemble(accessPeriodInFrench);

    assertThat(accessPeriod).isEqualTo(AccessPeriod.ONE_DAY_BY_WEEK_FOR_SESSION);
  }

  @Test
  public void givenUneSession_whenAssembling_thenReturnOneSession() {
    AccessPeriodInFrench accessPeriodInFrench = AccessPeriodInFrench.UNE_SESSION;

    AccessPeriod accessPeriod = accessPassPeriodAssembler.assemble(accessPeriodInFrench);

    assertThat(accessPeriod).isEqualTo(AccessPeriod.ONE_SESSION);
  }

  @Test
  public void givenDeuxSessions_whenAssembling_thenReturnTwoSessions() {
    AccessPeriodInFrench accessPeriodInFrench = AccessPeriodInFrench.DEUX_SESSIONS;

    AccessPeriod accessPeriod = accessPassPeriodAssembler.assemble(accessPeriodInFrench);

    assertThat(accessPeriod).isEqualTo(AccessPeriod.TWO_SESSIONS);
  }

  @Test
  public void givenTroisSessions_whenAssembling_thenReturnThreeSessions() {
    AccessPeriodInFrench accessPeriodInFrench = AccessPeriodInFrench.TROIS_SESSIONS;

    AccessPeriod accessPeriod = accessPassPeriodAssembler.assemble(accessPeriodInFrench);

    assertThat(accessPeriod).isEqualTo(AccessPeriod.THREE_SESSIONS);
  }
}
