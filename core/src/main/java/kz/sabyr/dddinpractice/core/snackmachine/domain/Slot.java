package kz.sabyr.dddinpractice.core.snackmachine.domain;

import kz.sabyr.dddinpractice.common.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Slot extends Entity {
    private SnackPile snackPile;
    private SnackMachine snackMachine;
    private int position;

    public Slot() {
    }

    public Slot(SnackMachine snackMachine, int position) {
        this.snackPile = SnackPile.EMPTY;
        this.snackMachine = snackMachine;
        this.position = position;
    }
}
