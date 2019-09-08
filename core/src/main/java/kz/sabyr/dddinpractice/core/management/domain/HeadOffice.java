package kz.sabyr.dddinpractice.core.management.domain;

import kz.sabyr.dddinpractice.common.AggregateRoot;
import kz.sabyr.dddinpractice.core.common.domain.Money;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class HeadOffice extends AggregateRoot {
    private BigDecimal balance = BigDecimal.ZERO;
    private Money cash = Money.NONE;

    public void changeBalance(BigDecimal balance) {
        this.balance = this.balance.add(balance);
    }

    public static class HeadOfficeBuilder {
        private Long id;
        private BigDecimal balance;
        private Money cash;

        public HeadOfficeBuilder(long id, BigDecimal balance, Money cash) {
            this.id = id;
            this.balance = balance;
            this.cash = cash;
        }

        public HeadOffice build() {
            HeadOffice headOffice = new HeadOffice();
            headOffice.setId(id);
            headOffice.balance = balance;
            headOffice.cash = cash;

            return headOffice;
        }
    }
}