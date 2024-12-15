package io.github.betterclient.groupadle.event;

import io.github.betterclient.groupadle.Groupadle;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.MouseEvent;

public class MouseDown implements EventListener<MouseEvent> {
    @Override
    public void handleEvent(MouseEvent event) {
        int x = event.getClientX();
        int y = event.getClientY();

        Groupadle.getInstance().handleClick(x, y, true);
    }
}
