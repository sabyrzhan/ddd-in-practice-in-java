package kz.sabyr.dddinpractice.common;

public interface DomainEventHandler<T extends DomainEvent> {
    void handle(T domainEvent);
}
