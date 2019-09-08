package kz.sabyr.dddinpractice.webAtm.command;

import kz.sabyr.dddinpractice.core.atms.domain.Atm;
import kz.sabyr.dddinpractice.core.atms.domain.repository.AtmRepository;
import kz.sabyr.dddinpractice.core.atms.integration.PaymentGateway;
import kz.sabyr.dddinpractice.core.common.domain.events.DomainEvents;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TakeMoneyHandler implements Handler {
    private BigDecimal amount;
    private AtmRepository atmRepository;
    private PaymentGateway paymentGateway;
    private DomainEvents domainEvents;

    @Override
    public HandlerResult handle(Atm atm) {
        HandlerResult handlerResult = new HandlerResult();
        handlerResult.setAtm(atm);
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            handlerResult.setUserMessage("Invalid amount of money");
            return handlerResult;
        }

        String canTakeMoney = atm.canTakeMoney(amount);
        if(!canTakeMoney.equals("")) {
            handlerResult.setUserMessage(canTakeMoney);
            return handlerResult;
        }

        BigDecimal amountWithCommission = atm.calculateAmountWithCommission(amount);
        paymentGateway.chargePayment(amountWithCommission);
        atm.takeMoney(amount);
        atmRepository.save(atm);
        domainEvents.handle(atm.getEventList());

        handlerResult.setUserMessage("You have taken " + amount.toPlainString() + " amount");
        handlerResult.setAtm(atm);

        return handlerResult;
    }
}