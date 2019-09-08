package kz.sabyr.dddinpractice.web.command;

import kz.sabyr.dddinpractice.core.snackmachine.domain.SnackMachine;

public interface Handler {
    HandlerResult handle(SnackMachine snackMachine);
}
