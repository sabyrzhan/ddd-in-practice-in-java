package kz.sabyr.dddinpractice.webAtm.command;

import kz.sabyr.dddinpractice.core.atms.domain.Atm;
import kz.sabyr.dddinpractice.core.snackmachine.domain.SnackMachine;

public interface Handler {
    HandlerResult handle(Atm atm);
}
