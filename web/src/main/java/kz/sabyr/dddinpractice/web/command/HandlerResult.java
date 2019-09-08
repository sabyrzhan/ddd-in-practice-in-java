package kz.sabyr.dddinpractice.web.command;

import kz.sabyr.dddinpractice.core.snackmachine.domain.SnackPile;
import lombok.Data;

import java.util.Map;

@Data
public class HandlerResult {
    private String moneyInside;
    private String moneyInTransaction;
    private String userMessage;
    private Map<Long, SnackPile> snackPiles;
}
