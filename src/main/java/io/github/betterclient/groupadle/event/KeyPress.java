package io.github.betterclient.groupadle.event;

import io.github.betterclient.groupadle.Groupadle;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.KeyboardEvent;

public class KeyPress implements EventListener<KeyboardEvent> {
    @Override
    public void handleEvent(KeyboardEvent event) {
        if (Groupadle.getInstance().focusedApplication != null) {
            Groupadle.getInstance().focusedApplication.keyboard(event.getCharCode());
        }
    }
}
