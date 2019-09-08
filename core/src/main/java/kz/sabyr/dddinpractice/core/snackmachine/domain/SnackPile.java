package kz.sabyr.dddinpractice.core.snackmachine.domain;

import kz.sabyr.dddinpractice.common.ValueObject;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Embeddable
public class SnackPile extends ValueObject {
    public static final SnackPile EMPTY = new SnackPile(Snack.NONE, 0, BigDecimal.ZERO);

    private Snack snack;
    private int quantity;
    private BigDecimal price;

    public SnackPile(Snack snack, int quantity, BigDecimal price) {
        if(quantity < 0) {
            throw new IllegalArgumentException();
        }

        if(price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }

        if(price.remainder(new BigDecimal("0.01")).compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalArgumentException();
        }

        this.snack = snack;
        this.quantity = quantity;
        this.price = price;
    }

    public SnackPile subtractOne() {
        return new SnackPile(snack, quantity - 1, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnackPile snackPile = (SnackPile) o;
        return quantity == snackPile.quantity &&
                Objects.equals(snack, snackPile.snack) &&
                Objects.equals(price, snackPile.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(snack, quantity, price);
    }
}
