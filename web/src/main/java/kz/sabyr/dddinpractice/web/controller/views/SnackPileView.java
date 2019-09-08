package kz.sabyr.dddinpractice.web.controller.views;

import kz.sabyr.dddinpractice.core.snackmachine.domain.Snack;
import kz.sabyr.dddinpractice.core.snackmachine.domain.SnackPile;
import lombok.Getter;

@Getter
public class SnackPileView {
    private SnackPile snackPile;

    public SnackPileView(SnackPile snackPile) {
        this.snackPile = snackPile;
    }

    public String getImagePath() {
        String result = null;
        Snack snack = snackPile.getSnack();
        switch (snack.getName()) {
            case "Chocolate":
                result = "/images/icon_chocolate.png";
                break;
            case "Soda":
                result = "/images/icon_soda.png";
                break;
            case "Gum":
                result = "/images/icon_gum.png";
                break;
        }

        return result;
    }
}
