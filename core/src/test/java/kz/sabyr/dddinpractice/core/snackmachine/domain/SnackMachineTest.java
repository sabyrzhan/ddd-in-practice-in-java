package kz.sabyr.dddinpractice.core.snackmachine.domain;

import kz.sabyr.dddinpractice.core.common.domain.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class SnackMachineTest {
    @Test
    public void returnMoneyEmptiesMoneyInTransaction() {
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.insertMoney(new Money(0,0,0,1,0,0));

        snackMachine.returnMoney();

        assertThat(snackMachine.getMoneyInTransaction()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    public void insertMoneyGoesToMoneyInTransaction() {
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.insertMoney(Money.ONE_DOLLAR);

        assertThat(snackMachine.getMoneyInTransaction()).isEqualByComparingTo(BigDecimal.ONE);
        assertThat(snackMachine.getMoneyInside().getAmount()).isEqualByComparingTo(BigDecimal.ONE);
    }

    @Test
    public void cannotInsertMoreThanOneCoinOrNoteAtATime() {
        SnackMachine snackMachine = new SnackMachine();

        assertThatThrownBy(() -> snackMachine.insertMoney(Money.add(Money.ONE_CENT, Money.ONE_CENT))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void buySnackTradesInsertedMoneyForASnack() {
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.loadSnacks(1, new SnackPile(Snack.CHOCOLATE, 10, new BigDecimal("1")));
        snackMachine.insertMoney(Money.ONE_DOLLAR);

        snackMachine.buySnack(1);

        assertThat(snackMachine.getMoneyInside()).isEqualTo(Money.ONE_DOLLAR);
        assertThat(snackMachine.getMoneyInTransaction()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(snackMachine.getSnackPile(1).getQuantity()).isEqualTo(9);
    }

    @Test
    public void cannotBuySnackWhenThereIsNoSnack() {
        SnackMachine snackMachine = new SnackMachine();
        assertThatThrownBy(() -> snackMachine.buySnack(1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void cannotMakePurchaseWhenThereIsNoEnoughMoney() {
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.loadSnacks(1, new SnackPile(Snack.CHOCOLATE, 1, new BigDecimal("2")));
        snackMachine.insertMoney(Money.ONE_DOLLAR);
        assertThatThrownBy(() -> snackMachine.buySnack(1)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void machineShouldReturnMoneyWithHighestDenominationFirst() {
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.loadMoney(Money.ONE_DOLLAR);

        snackMachine.insertMoney(Money.QUARTER_DOLLAR);
        snackMachine.insertMoney(Money.QUARTER_DOLLAR);
        snackMachine.insertMoney(Money.QUARTER_DOLLAR);
        snackMachine.insertMoney(Money.QUARTER_DOLLAR);

        snackMachine.returnMoney();

        assertThat(snackMachine.getMoneyInside().getQuarterCount()).isEqualTo(4);
        assertThat(snackMachine.getMoneyInside().getOneDollarCount()).isEqualTo(0);
    }

    @Test
    public void afterPurchaseChangeIsReturned() {
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.loadSnacks(1, new SnackPile(Snack.CHOCOLATE, 1, new BigDecimal("0.5")));
        snackMachine.loadMoney(Money.multiplyByMultiplier(Money.TEN_CENT, 10));

        snackMachine.insertMoney(Money.ONE_DOLLAR);

        snackMachine.buySnack(1);

        assertThat(snackMachine.getMoneyInside().getAmount()).isEqualByComparingTo(new BigDecimal("1.5"));
        assertThat(snackMachine.getMoneyInTransaction()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    public void cannotBuySnackIfNotEnoughChange() {
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.loadSnacks(1, new SnackPile(Snack.CHOCOLATE, 1, new BigDecimal("0.5")));
        snackMachine.loadMoney(Money.multiplyByMultiplier(Money.ONE_DOLLAR, 1));

        snackMachine.insertMoney(Money.ONE_DOLLAR);

        assertThatThrownBy(() -> snackMachine.buySnack(1)).isInstanceOf(IllegalStateException.class);
    }
}
