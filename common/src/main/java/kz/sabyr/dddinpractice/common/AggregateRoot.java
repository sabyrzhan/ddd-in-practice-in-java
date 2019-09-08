package kz.sabyr.dddinpractice.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot extends Entity {
    private List<DomainEvent> eventList = new ArrayList<>();

    protected void addDomainEvent(DomainEvent ev) {
        eventList.add(ev);
    }

    public void clearEvents() {
        eventList.clear();
    }

    public List<DomainEvent> getEventList() {
        return Collections.unmodifiableList(eventList);
    }
}
