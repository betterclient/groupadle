package io.github.betterclient.groupadle.event;

import io.github.betterclient.groupadle.Groupadle;
import org.teavm.jso.dom.events.KeyboardEvent;
import org.teavm.jso.dom.events.MouseEvent;
import org.teavm.jso.dom.html.HTMLDocument;

public class EventManager {
    public int mouseX, mouseY;

    public void register() {
        HTMLDocument.current().addEventListener("mousedown", evt -> {
            MouseEvent event = (MouseEvent) evt;
            int x = event.getClientX();
            int y = event.getClientY();

            Groupadle.getInstance().handleClick(x, y, true);
        });

        HTMLDocument.current().addEventListener("mouseup", evt -> {
            MouseEvent event = (MouseEvent) evt;
            int x = event.getClientX();
            int y = event.getClientY();

            Groupadle.getInstance().handleClick(x, y, false);
        });

        HTMLDocument.current().addEventListener("mousemove", evt -> {
            MouseEvent event = (MouseEvent) evt;

            this.mouseX = event.getClientX();
            this.mouseY = event.getClientY();
        });

        HTMLDocument.current().addEventListener("keypress", evt -> {
            KeyboardEvent event = (KeyboardEvent) evt;

            if (Groupadle.getInstance().focusedApplication != null) {
                Groupadle.getInstance().focusedApplication.keyboard(event.getCharCode());
            }
        });
    }
}
