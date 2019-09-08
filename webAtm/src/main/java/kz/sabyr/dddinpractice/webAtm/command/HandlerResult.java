package kz.sabyr.dddinpractice.webAtm.command;

import kz.sabyr.dddinpractice.core.atms.domain.Atm;
import lombok.Data;

@Data
public class HandlerResult {
    private String userMessage;
    private Atm atm;
}
