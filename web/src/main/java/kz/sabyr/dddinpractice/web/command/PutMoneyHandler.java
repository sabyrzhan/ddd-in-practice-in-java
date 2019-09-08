package kz.sabyr.dddinpractice.web.command;

import kz.sabyr.dddinpractice.core.common.domain.Money;
import kz.sabyr.dddinpractice.core.snackmachine.domain.SnackMachine;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutMoneyHandler implements Handler {
    private String amountToInsert;

    @Override
    public HandlerResult handle(SnackMachine snackMachine) {
        if(amountToInsert != null) {
            switch (amountToInsert) {
                case "ONE_CENT":
                    snackMachine.insertMoney(Money.ONE_CENT);
                    break;
                case "TEN_CENT":
                    snackMachine.insertMoney(Money.TEN_CENT);
                    break;
                case "QUARTER_DOLLAR":
                    snackMachine.insertMoney(Money.QUARTER_DOLLAR);
                    break;
                case "ONE_DOLLAR":
                    snackMachine.insertMoney(Money.ONE_DOLLAR);
                    break;
                case "FIVE_DOLLAR":
                    snackMachine.insertMoney(Money.FIVE_DOLLAR);
                    break;
                case "TWENTY_DOLLAR":
                    snackMachine.insertMoney(Money.TWENTY_DOLLAR);
                    break;
            }
        }

        HandlerResult result = new HandlerResult();
        result.setMoneyInside(snackMachine.getMoneyInside().toString());
        result.setMoneyInTransaction(snackMachine.getMoneyInTransaction().toString());
        result.setUserMessage("You put " + snackMachine.getMoneyInTransaction().toString());

        return result;
    }
}
