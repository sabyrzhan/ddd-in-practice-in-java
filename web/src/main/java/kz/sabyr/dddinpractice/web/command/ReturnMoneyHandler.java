package kz.sabyr.dddinpractice.web.command;

import kz.sabyr.dddinpractice.core.snackmachine.domain.SnackMachine;

public class ReturnMoneyHandler implements Handler {
    @Override
    public HandlerResult handle(SnackMachine snackMachine) {
        snackMachine.returnMoney();

        HandlerResult result = new HandlerResult();
        result.setMoneyInside(snackMachine.getMoneyInside().toString());
        result.setMoneyInTransaction(snackMachine.getMoneyInTransaction().toString());
        result.setUserMessage("Money was returned!");
        return result;
    }
}
