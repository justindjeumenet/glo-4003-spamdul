package ca.ulaval.glo4003.funds;

import ca.ulaval.glo4003.accounts.services.AccountService;
import ca.ulaval.glo4003.funds.domain.*;
import ca.ulaval.glo4003.funds.services.BillService;
import ca.ulaval.glo4003.funds.services.converters.MoneyConverter;
import ca.ulaval.glo4003.initiatives.domain.InitiativeFundCollector;
import ca.ulaval.glo4003.reports.services.ReportEventService;

public class FundInjector {

  private final BillIdGenerator billIdGenerator = new BillIdGenerator();

  public BillService createBillService(
      ReportEventService reportEventService,
      AccountService accountService,
      InitiativeFundCollector initiativeFundCollector) {
    BillFactory billFactory = new BillFactory(billIdGenerator);

    return new BillService(
        billFactory, reportEventService, accountService, initiativeFundCollector);
  }

  public MoneyConverter createMoneyConverter() {
    return new MoneyConverter();
  }
}
