package kz.sabyr.dddinpractice.core.atms.domain.repository;

import kz.sabyr.dddinpractice.core.atms.domain.Atm;
import kz.sabyr.dddinpractice.core.atms.jpa.AtmJpa;
import kz.sabyr.dddinpractice.core.atms.jpa.repository.AtmJpaRepository;
import kz.sabyr.dddinpractice.core.common.domain.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AtmRepository {
    @Autowired
    private AtmJpaRepository atmJpaRepository;

    public Optional<Atm> findById(long id) {
        Optional<AtmJpa> atmJpaOptional = atmJpaRepository.findById(id);
        if(!atmJpaOptional.isPresent()) {
            return Optional.ofNullable(null);
        }

        AtmJpa atmJpa = atmJpaOptional.get();
        Money moneyInside = new Money(
                atmJpa.getOneCentCount(),
                atmJpa.getTenCentCount(),
                atmJpa.getQuarterCount(),
                atmJpa.getOneDollarCount(),
                atmJpa.getFiveDollarCount(),
                atmJpa.getTwentyDollarCount()
        );


        Atm atm = new Atm.AtmBuilder(atmJpa.getId(), moneyInside).withMoneyCharged(atmJpa.getMoneyCharged()).build();

        return Optional.of(atm);
    }

    public void save(Atm atm) {
        Optional<AtmJpa> atmJpaOptional = atmJpaRepository.findById(atm.getId());
        if(!atmJpaOptional.isPresent()) {
            return;
        }

        Money moneyInside = atm.getMoneyInside();

        AtmJpa atmJpa = atmJpaOptional.get();
        atmJpa.setMoneyCharged(atm.getMoneyCharged());
        atmJpa.setOneCentCount(moneyInside.getOneCentCount());
        atmJpa.setTenCentCount(moneyInside.getTenCentCount());
        atmJpa.setQuarterCount(moneyInside.getQuarterCount());
        atmJpa.setOneDollarCount(moneyInside.getOneDollarCount());
        atmJpa.setFiveDollarCount(moneyInside.getFiveDollarCount());
        atmJpa.setTwentyDollarCount(moneyInside.getTwentyDollarCount());
        atmJpaRepository.save(atmJpa);
    }
}