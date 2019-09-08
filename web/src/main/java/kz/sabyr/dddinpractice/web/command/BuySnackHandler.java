package kz.sabyr.dddinpractice.web.command;

import kz.sabyr.dddinpractice.core.snackmachine.domain.SnackMachine;
import kz.sabyr.dddinpractice.core.snackmachine.domain.SnackPile;
import kz.sabyr.dddinpractice.core.snackmachine.domain.repository.SnackMachineRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.Collectors;

@Getter
@Setter
public class BuySnackHandler implements Handler {
    private int position;
    private SnackMachineRepository snackMachineRepository;

    @Override
    public HandlerResult handle(SnackMachine snackMachine) {
        String canBuyMessage = snackMachine.canBuySnack(position);
        if(canBuyMessage.equals("")) {
            snackMachine.buySnack(position);
            snackMachineRepository.save(snackMachine);
            canBuyMessage = "You bought a snack";
        }

        HandlerResult result = new HandlerResult();
        result.setMoneyInside(snackMachine.getMoneyInside().toString());
        result.setMoneyInTransaction(snackMachine.getMoneyInTransaction().toString());
        result.setSnackPiles(snackMachine.getAllSnackPiles().stream().collect(Collectors.toMap((SnackPile s) -> s.getSnack().getId(), s -> s)));
        result.setUserMessage(canBuyMessage);
        return result;
    }
}
