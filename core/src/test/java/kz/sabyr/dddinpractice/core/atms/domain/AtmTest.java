package kz.sabyr.dddinpractice.core.atms.domain;

import kz.sabyr.dddinpractice.common.DomainEvent;
import kz.sabyr.dddinpractice.core.atms.domain.events.BalanceChangedEvent;
import kz.sabyr.dddinpractice.core.common.domain.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AtmTest {
    @Test
    public void takeMoneyExchangesMoneyWithCommission() {
        Atm atm = new Atm();
        atm.loadMoney(Money.ONE_DOLLAR);

        atm.takeMoney(new BigDecimal("1"));

        assertThat(atm.getMoneyInside().getAmount()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(atm.getMoneyCharged()).isEqualByComparingTo(new BigDecimal("1.01"));
    }

    @Test
    public void commissionIsAtLeastOneCent() {
        Atm atm = new Atm();
        atm.loadMoney(Money.ONE_CENT);

        atm.takeMoney(new BigDecimal("0.01"));

        assertThat(atm.getMoneyCharged()).isEqualByComparingTo("0.02");
    }

    @Test
    public void commissionIsRoundedUpToTheNextCent() {
        Atm atm = new Atm();
        atm.loadMoney(Money.add(Money.ONE_DOLLAR, Money.TEN_CENT));

        atm.takeMoney(new BigDecimal("1.1"));

        assertThat(atm.getMoneyCharged()).isEqualByComparingTo(new BigDecimal("1.12"));
    }

    @Test
    public void takeMoneyRaisesEvent() {
        Atm atm = new Atm();
        atm.loadMoney(Money.ONE_DOLLAR);
        atm.takeMoney(new BigDecimal("1"));

        List<DomainEvent> afterTakeMoneyEvents = atm.getEventList();

        assertThat(afterTakeMoneyEvents).isNotEmpty();
        assertThat(((BalanceChangedEvent)afterTakeMoneyEvents.get(0)).getDelta()).isEqualByComparingTo(new BigDecimal("1.01"));
    }
}
