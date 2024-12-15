package io.github.betterclient.groupadle.event;

import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.MouseEvent;

public record MouseMove(EventManager manager) implements EventListener<MouseEvent> {
    @Override
    public void handleEvent(MouseEvent event) {
        this.manager.mouseX = event.getClientX();
        this.manager.mouseY = event.getClientY();
    }
}
