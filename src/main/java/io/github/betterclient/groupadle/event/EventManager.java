package io.github.betterclient.groupadle.event;

import org.teavm.jso.dom.html.HTMLDocument;

public class EventManager {
    public int mouseX, mouseY;

    public void register() {
        HTMLDocument.current().addEventListener("mousedown", new MouseDown());
        HTMLDocument.current().addEventListener("mouseup", new MouseUp());
        HTMLDocument.current().addEventListener("mousemove", new MouseMove(this));

        HTMLDocument.current().addEventListener("keypress", new KeyPress());
    }
}
