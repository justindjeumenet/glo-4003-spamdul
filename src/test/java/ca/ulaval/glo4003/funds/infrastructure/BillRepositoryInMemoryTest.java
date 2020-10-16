package ca.ulaval.glo4003.funds.infrastructure;

import static ca.ulaval.glo4003.funds.helpers.BillBuilder.aBill;
import static ca.ulaval.glo4003.funds.helpers.MoneyMother.createMoney;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.funds.domain.Bill;
import ca.ulaval.glo4003.funds.domain.BillId;
import ca.ulaval.glo4003.funds.domain.BillRepository;
import ca.ulaval.glo4003.funds.domain.Money;
import ca.ulaval.glo4003.funds.exception.BillNotFoundException;
import com.google.common.truth.Truth;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BillRepositoryInMemoryTest {
  private BillRepository billRepository;

  @Mock BillQueryInMemory billQueryInMemory;

  private final Bill bill = aBill().build();
  private final Bill other_bill = aBill().build();

  @Before
  public void setUp() {
    billRepository = new BillRepositoryInMemory();
  }

  @Test
  public void whenSavingBill_thenReturnId() {
    BillId billId = billRepository.save(bill);

    Truth.assertThat(billId).isSameInstanceAs(bill.getId());
  }

  @Test
  public void givenBillIds_whenGettingBills_thenReturnBills() {
    billRepository.save(bill);
    billRepository.save(other_bill);
    List<BillId> billIds = new ArrayList<>();
    billIds.add(bill.getId());
    billIds.add(other_bill.getId());

    List<Bill> bills = billRepository.getBills(billIds);

    Truth.assertThat(bills).contains(bill);
    Truth.assertThat(bills).contains(other_bill);
  }

  @Test(expected = BillNotFoundException.class)
  public void givenBillIds_whenGettingBillsButNotFound_thenThrowBillNotFoundException() {
    List<BillId> billIds = new ArrayList<>();
    billIds.add(bill.getId());
    billIds.add(other_bill.getId());

    billRepository.getBills(billIds);
  }

  @Test
  public void givenBillId_whenGettingBill_thenReturnBill() {
    billRepository.save(bill);
    Bill billFromRepository = billRepository.getBill(bill.getId());

    Truth.assertThat(billFromRepository).isEqualTo(bill);
  }

  @Test(expected = BillNotFoundException.class)
  public void givenBillId_whenGettingBillNotFound_thenThrowBillNotFoundException() {
    billRepository.getBill(bill.getId());
  }

  @Test(expected = BillNotFoundException.class)
  public void givenNoBill_whenUpdating_thenThrowBillNotFoundException() {
    billRepository.updateBill(bill);
  }

  @Test
  public void givenBill_whenUpdating_thenBillIsUpdated() {
    billRepository.save(bill);
    Bill billBeforeUpdating = billRepository.getBill(bill.getId());
    bill.pay(bill.getAmountDue());

    billRepository.updateBill(bill);
    Bill billAfterUpdating = billRepository.getBill(bill.getId());

    Truth.assertThat(billAfterUpdating.getAmountPaid()).isNotEqualTo(billBeforeUpdating);
  }

  @Test
  public void givenBillQuery_whenGettingAllBills_thenShouldExecuteQueries() {
    billRepository.getAll(billQueryInMemory);

    verify(billQueryInMemory).execute();
  }
}
