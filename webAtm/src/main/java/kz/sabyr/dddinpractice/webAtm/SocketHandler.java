package kz.sabyr.dddinpractice.webAtm;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.sabyr.dddinpractice.core.atms.domain.Atm;
import kz.sabyr.dddinpractice.core.atms.domain.repository.AtmRepository;
import kz.sabyr.dddinpractice.core.atms.integration.PaymentGateway;
import kz.sabyr.dddinpractice.core.common.domain.events.DomainEvents;
import kz.sabyr.dddinpractice.webAtm.command.Command;
import kz.sabyr.dddinpractice.webAtm.command.Handler;
import kz.sabyr.dddinpractice.webAtm.command.HandlerResult;
import kz.sabyr.dddinpractice.webAtm.command.TakeMoneyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SocketHandler extends TextWebSocketHandler {
    private Map<String, Atm> sessions = new ConcurrentHashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AtmRepository atmRepository;

    @Autowired
    private PaymentGateway paymentGateway;

    @Autowired
    private DomainEvents domainEvents;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUri(session.getUri()).build().getQueryParams();
        String sessionId = queryParams.getFirst("sessionId");
        if(!sessions.containsKey(sessionId)) {
            Optional<Atm> atm = atmRepository.findById(1L);
            if(atm.isPresent()) {
                sessions.put(sessionId, atm.get());
            }
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUri(session.getUri()).build().getQueryParams();
        String sessionId = queryParams.getFirst("sessionId");
        if(sessionId == null) {
            System.out.println("Session ID is null");
            return;
        }

        Atm atm = sessions.get(sessionId);
        if(atm == null) {
            return;
        }

        Command command = objectMapper.readValue(message.getPayload(), Command.class);
        if(command == null) {
            System.out.println("Command is null");
            return;
        }

        String action = command.getAction();
        Handler handler = null;
        switch (action) {
            case "take":
                handler = objectMapper.readValue(command.getCommand(), TakeMoneyHandler.class);
                ((TakeMoneyHandler) handler).setAtmRepository(atmRepository);
                ((TakeMoneyHandler) handler).setPaymentGateway(paymentGateway);
                ((TakeMoneyHandler) handler).setDomainEvents(domainEvents);
                break;
        }

        if(handler == null) {
            System.out.println("Handler is null");
            return;
        }

        HandlerResult result = handler.handle(atm);

        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(result)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUri(session.getUri()).build().getQueryParams();
        String sessionId = queryParams.getFirst("sessionId");
        sessions.remove(sessionId);
    }
}
