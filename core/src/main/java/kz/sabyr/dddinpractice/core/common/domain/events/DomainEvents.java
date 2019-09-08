package kz.sabyr.dddinpractice.core.common.domain.events;

import kz.sabyr.dddinpractice.common.DomainEvent;
import kz.sabyr.dddinpractice.core.atms.domain.events.BalanceChangedEvent;
import kz.sabyr.dddinpractice.core.management.domain.events.handler.BalanceChangedEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DomainEvents {
    @Autowired
    private BalanceChangedEventHandler balanceChangedEventHandler;

    public void handle(List<DomainEvent> eventList) {
        if(eventList == null) {
            return;
        }

        for(DomainEvent domainEvent: eventList) {
            if(domainEvent == null) {
                continue;
            }

            if(domainEvent.getClass() == BalanceChangedEvent.class) {
                balanceChangedEventHandler.handle((BalanceChangedEvent)domainEvent);
            }
        }
    }
}