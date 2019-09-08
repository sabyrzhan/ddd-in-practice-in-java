package kz.sabyr.dddinpractice.core.management.domain.repository;

import kz.sabyr.dddinpractice.core.common.domain.Money;
import kz.sabyr.dddinpractice.core.management.domain.HeadOffice;
import kz.sabyr.dddinpractice.core.management.jpa.HeadOfficeJpa;
import kz.sabyr.dddinpractice.core.management.jpa.repository.HeadOfficeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HeadOfficeRepository {
    @Autowired
    private HeadOfficeJpaRepository headOfficeJpaRepository;

    public Optional<HeadOffice> getHeadOffice() {
        Optional<HeadOfficeJpa> headOfficeJpaOptional = headOfficeJpaRepository.findById(1L);
        if(!headOfficeJpaOptional.isPresent()) {
            return Optional.ofNullable(null);
        }

        HeadOfficeJpa headOfficeJpa = headOfficeJpaOptional.get();

        Money cash = new Money(headOfficeJpa.getOneCentCount(),
                headOfficeJpa.getTenCentCount(), headOfficeJpa.getQuarterCount(), headOfficeJpa.getOneDollarCount(),
                headOfficeJpa.getFiveDollarCount(), headOfficeJpa.getTwentyDollarCount());

        HeadOffice headOffice = new HeadOffice.HeadOfficeBuilder(headOfficeJpa.getId(),
                headOfficeJpa.getBalance(), cash).build();

        return Optional.of(headOffice);
    }

    public void save(HeadOffice headOffice) {
        Optional<HeadOfficeJpa> headOfficeOptional = headOfficeJpaRepository.findById(headOffice.getId());
        if(!headOfficeOptional.isPresent()) {
            return;
        }

        HeadOfficeJpa headOfficeJpa = headOfficeOptional.get();
        headOfficeJpa.setBalance(headOffice.getBalance());

        Money cash = headOffice.getCash();
        headOfficeJpa.setOneCentCount(cash.getOneCentCount());
        headOfficeJpa.setTenCentCount(cash.getTenCentCount());
        headOfficeJpa.setQuarterCount(cash.getQuarterCount());
        headOfficeJpa.setOneDollarCount(cash.getOneDollarCount());
        headOfficeJpa.setFiveDollarCount(cash.getFiveDollarCount());
        headOfficeJpa.setTwentyDollarCount(cash.getTwentyDollarCount());

        headOfficeJpaRepository.save(headOfficeJpa);
    }
}