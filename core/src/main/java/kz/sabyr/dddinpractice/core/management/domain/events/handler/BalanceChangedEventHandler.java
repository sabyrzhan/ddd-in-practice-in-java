package kz.sabyr.dddinpractice.core.management.domain.events.handler;

import kz.sabyr.dddinpractice.common.DomainEventHandler;
import kz.sabyr.dddinpractice.core.atms.domain.events.BalanceChangedEvent;
import kz.sabyr.dddinpractice.core.management.domain.HeadOffice;
import kz.sabyr.dddinpractice.core.management.domain.repository.HeadOfficeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class BalanceChangedEventHandler implements DomainEventHandler<BalanceChangedEvent> {
    @Autowired
    private HeadOfficeRepository headOfficeRepository;

    @Override
    public void handle(BalanceChangedEvent domainEvent) {
        Optional<HeadOffice> headOfficeOptional = headOfficeRepository.getHeadOffice();
        if (!headOfficeOptional.isPresent()) {
            log.warn("HeadOffice does not exist");
            return;
        }

        HeadOffice headOffice = headOfficeOptional.get();
        headOffice.changeBalance(domainEvent.getDelta());
        headOfficeRepository.save(headOffice);
    }
}
