package kz.sabyr.dddinpractice.webAtm.command;

import kz.sabyr.dddinpractice.core.atms.domain.Atm;
import kz.sabyr.dddinpractice.core.common.domain.Money;
import kz.sabyr.dddinpractice.core.snackmachine.domain.SnackMachine;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutMoneyHandler implements Handler {
    private String amountToInsert;

    @Override
    public HandlerResult handle(Atm atm) {
        return null;
    }
}
