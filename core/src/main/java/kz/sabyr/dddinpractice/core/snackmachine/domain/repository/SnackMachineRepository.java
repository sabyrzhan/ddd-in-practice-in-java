package kz.sabyr.dddinpractice.core.snackmachine.domain.repository;

import kz.sabyr.dddinpractice.core.common.domain.Money;
import kz.sabyr.dddinpractice.core.snackmachine.domain.Slot;
import kz.sabyr.dddinpractice.core.snackmachine.domain.Snack;
import kz.sabyr.dddinpractice.core.snackmachine.domain.SnackMachine;
import kz.sabyr.dddinpractice.core.snackmachine.domain.SnackPile;
import kz.sabyr.dddinpractice.core.snackmachine.jpa.SlotJpa;
import kz.sabyr.dddinpractice.core.snackmachine.jpa.SnackJpa;
import kz.sabyr.dddinpractice.core.snackmachine.jpa.SnackMachineJpa;
import kz.sabyr.dddinpractice.core.snackmachine.jpa.repository.SlotJpaRepository;
import kz.sabyr.dddinpractice.core.snackmachine.jpa.repository.SnackJpaRepository;
import kz.sabyr.dddinpractice.core.snackmachine.jpa.repository.SnackMachineJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SnackMachineRepository {
    @Autowired
    private SnackMachineJpaRepository snackMachineJpaRepository;

    @Autowired
    private SlotJpaRepository slotJpaRepository;

    @Autowired
    private SnackJpaRepository snackJpaRepository;

    public Optional<SnackMachine> findById(Long id) {
        Optional<SnackMachineJpa> snackMachineJpa = snackMachineJpaRepository.findById(id);
        if(!snackMachineJpa.isPresent()) {
            return Optional.ofNullable(null);
        }

        List<SlotJpa> slotsJpa = slotJpaRepository.findBySnackMachineId(id);
        List<Slot> slots = new ArrayList<>();
        for(SlotJpa slotJpa: slotsJpa) {
            Slot slot = new Slot();
            slot.setId(slotJpa.getId());
            slot.setPosition(slotJpa.getPosition());

            Optional<SnackJpa> snackJpa = snackJpaRepository.findById(slotJpa.getSnackId());
            if(!snackJpa.isPresent()) {
                continue;
            }

            Snack snack = new Snack.SnackBuilder(snackJpa.get().getId(), snackJpa.get().getName()).build();
            SnackPile snackPile = new SnackPile(snack, slotJpa.getQuantity(), slotJpa.getPrice());
            slot.setSnackPile(snackPile);
            slots.add(slot);
        }

        SnackMachine snackMachine = new SnackMachine.SnackMachineBuilder(snackMachineJpa.get().getId())
                .withSlots(slots)
                .build();
        slots.forEach(s -> s.setSnackMachine(snackMachine));

        return Optional.of(snackMachine);
    }

    public void save(SnackMachine snackMachine) {
        Optional<SnackMachineJpa> snackMachineJpaOptional = snackMachineJpaRepository.findById(snackMachine.getId());
        if(!snackMachineJpaOptional.isPresent()) {
            return;
        }

        Money moneyInside = snackMachine.getMoneyInside();
        SnackMachineJpa snackMachineJpa = snackMachineJpaOptional.get();
        snackMachineJpa.setOneCentCount(moneyInside.getOneCentCount());
        snackMachineJpa.setTenCentCount(moneyInside.getTenCentCount());
        snackMachineJpa.setQuarterCount(moneyInside.getQuarterCount());
        snackMachineJpa.setOneDollarCount(moneyInside.getOneDollarCount());
        snackMachineJpa.setFiveDollarCount(moneyInside.getFiveDollarCount());
        snackMachineJpa.setTwentyDollarCount(moneyInside.getTwentyDollarCount());

        List<SlotJpa> slotJpaList = slotJpaRepository.findBySnackMachineId(snackMachine.getId());
        Map<Long, SlotJpa> slotJpaMapBySnackId = slotJpaList.stream().collect(Collectors.toMap(SlotJpa::getSnackId, s -> s));
        for(SnackPile snackPile : snackMachine.getAllSnackPiles()) {
            Snack snack = snackPile.getSnack();
            if(slotJpaMapBySnackId.containsKey(snack.getId())) {
                SlotJpa slotJpa = slotJpaMapBySnackId.get(snack.getId());
                slotJpa.setQuantity(snackPile.getQuantity());
            }
        }

        snackMachineJpaRepository.save(snackMachineJpa);
        slotJpaRepository.saveAll(slotJpaList);
    }
}