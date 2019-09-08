package kz.sabyr.dddinpractice.core.snackmachine.domain;

import kz.sabyr.dddinpractice.common.AggregateRoot;
import kz.sabyr.dddinpractice.core.common.domain.Money;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class SnackMachine extends AggregateRoot {
    private Money moneyInside;
    private BigDecimal moneyInTransaction;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<Slot> slots;

    public SnackMachine() {
        moneyInside = Money.NONE;
        moneyInTransaction = BigDecimal.ZERO;

        slots = new ArrayList<>();
        slots.add(new Slot(this, 1));
        slots.add(new Slot(this, 2));
        slots.add(new Slot(this, 3));
    }

    public void loadMoney(Money money) {
        moneyInside = Money.add(moneyInside, money);
    }

    public void insertMoney(Money money) {
        List<Money> allowedCoins = Arrays.asList(Money.ONE_CENT, Money.TEN_CENT, Money.QUARTER_DOLLAR, Money.ONE_DOLLAR, Money.FIVE_DOLLAR, Money.TWENTY_DOLLAR);
        if(!allowedCoins.contains(money)) {
            throw new IllegalArgumentException();
        }
        moneyInTransaction = moneyInTransaction.add(money.getAmount());
        moneyInside = Money.add(moneyInside, money);
    }

    public void returnMoney() {
        Money money = moneyInside.allocate(moneyInTransaction);
        moneyInTransaction = BigDecimal.ZERO;
        moneyInside = Money.subtract(moneyInside, money);
    }

    public void buySnack(int position) {
        String canBuyMessage = canBuySnack(position);
        if(canBuyMessage.equals("")) {
            throw new IllegalStateException(canBuyMessage);
        }

        Slot slot = slots.stream().filter(s -> s.getPosition() == position).findFirst().orElse(null);
        Money allocated = moneyInside.allocate(moneyInTransaction.subtract(slot.getSnackPile().getPrice()));
        slot.setSnackPile(slot.getSnackPile().subtractOne());
        moneyInside = Money.subtract(moneyInside, allocated);
        moneyInTransaction = BigDecimal.ZERO;
    }

    public void loadSnacks(int position, SnackPile snackPile) {
        Slot slot = slots.stream().filter(s -> s.getPosition() == position).findFirst().orElse(null);
        slot.setSnackPile(snackPile);
    }

    public String canBuySnack(int position) {
        SnackPile snackPile = getSnackPile(position);
        if(snackPile.getQuantity() <= 0) {
            return "The snack pile is empty";
        }

        if(moneyInTransaction.compareTo(snackPile.getPrice()) < 0) {
            return "Not enough money";
        }

        if(!moneyInside.canAllocateMoney(moneyInTransaction.subtract(snackPile.getPrice()))) {
            return "Not enough change";
        }

        return "";
    }

    public SnackPile getSnackPile(int position) {
        return slots.stream().filter(s -> s.getPosition() == position).findFirst().orElse(null).getSnackPile();
    }

    public List<SnackPile> getAllSnackPiles() {
        List<SnackPile> piles = slots.stream().map(s -> s.getSnackPile()).collect(Collectors.toList());
        piles.sort(Comparator.comparingInt(SnackPile::getQuantity));

        return Collections.unmodifiableList(piles);
    }

    public static class SnackMachineBuilder {
        private long id;
        private List<Slot> slots;

        public SnackMachineBuilder(long id) {
            this.id = id;
        }

        public SnackMachineBuilder withSlots(List<Slot> slots) {
            this.slots = slots;
            return this;
        }

        public SnackMachine build() {
            SnackMachine snackMachine = new SnackMachine();
            snackMachine.setId(id);
            snackMachine.slots = slots;
            return snackMachine;
        }
    }
}
