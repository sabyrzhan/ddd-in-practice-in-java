package kz.sabyr.dddinpractice.core.atms.domain.events;

import kz.sabyr.dddinpractice.common.DomainEvent;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BalanceChangedEvent implements DomainEvent {
    private BigDecimal delta;

    public BalanceChangedEvent(BigDecimal delta) {
        this.delta = delta;
    }
}
