package kz.sabyr.dddinpractice.core.atms.domain;

import kz.sabyr.dddinpractice.common.AggregateRoot;
import kz.sabyr.dddinpractice.core.atms.domain.events.BalanceChangedEvent;
import kz.sabyr.dddinpractice.core.common.domain.Money;
import kz.sabyr.dddinpractice.core.common.domain.events.DomainEvents;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Atm extends AggregateRoot {
    private Money moneyInside = Money.NONE;
    private BigDecimal moneyCharged = BigDecimal.ZERO;
    private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.01");

    public String canTakeMoney(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            return "Invalid amount";
        }

        if(moneyInside.getAmount().compareTo(amount) < 0) {
            return "Not enough money";
        }

        if(!moneyInside.canAllocateMoney(amount)) {
            return "Not enough change";
        }

        return "";
    }

    public void takeMoney(BigDecimal amount) {
        String canTakeMoneyMessage = canTakeMoney(amount);
        if(!canTakeMoneyMessage.equals("")) {
            throw new IllegalStateException(canTakeMoneyMessage);
        }

        Money allocated = moneyInside.allocate(amount);
        moneyInside = Money.subtract(moneyInside, allocated);
        BigDecimal amountWithCommission = calculateAmountWithCommission(amount);
        moneyCharged = moneyCharged.add(amountWithCommission);

        addDomainEvent(new BalanceChangedEvent(amountWithCommission));
    }

    public void loadMoney(Money money) {
        moneyInside = Money.add(moneyInside, money);
    }

    public BigDecimal calculateAmountWithCommission(BigDecimal amount) {
        BigDecimal commission = amount.multiply(COMMISSION_RATE);
        BigDecimal lessThanCent = commission.remainder(new BigDecimal("0.01"));
        if(lessThanCent.compareTo(BigDecimal.ZERO) > 0) {
            commission = commission.subtract(lessThanCent).add(new BigDecimal("0.01"));
        }

        return amount.add(commission);
    }

    public static final class AtmBuilder {
        private long id;
        private Money moneyInside;
        private BigDecimal moneyCharged;

        public AtmBuilder(long id, Money moneyInside) {
            this.id = id;
            this.moneyInside = moneyInside;
        }

        public AtmBuilder withMoneyCharged(BigDecimal moneyCharged) {
            this.moneyCharged = moneyCharged;
            return this;
        }

        public Atm build() {
            Atm atm = new Atm();
            atm.setId(id);
            atm.moneyInside = moneyInside;
            atm.moneyCharged = moneyCharged;

            return atm;
        }
    }
}
