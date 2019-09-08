package kz.sabyr.dddinpractice.webAtm.controller.views;

import kz.sabyr.dddinpractice.core.atms.domain.Atm;
import kz.sabyr.dddinpractice.core.snackmachine.domain.Snack;
import kz.sabyr.dddinpractice.core.snackmachine.domain.SnackPile;
import lombok.Getter;

@Getter
public class AtmView {
    private Atm atm;

    public AtmView(Atm atm) {
         this.atm = atm;
    }
}
