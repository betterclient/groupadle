package io.github.betterclient.groupadle.util;

import io.github.betterclient.groupadle.Groupadle;
import io.github.betterclient.groupadle.desktop.Application;
import org.teavm.jso.dom.html.HTMLBodyElement;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.NodeList;

public class HTMLBasedAppHelper {
    static HTMLBodyElement body;
    public static void init() {
        body = HTMLDocument.current().getBody();
    }

    public static boolean checkNode(HTMLElement el) {
        NodeList<? extends HTMLElement> list = body.querySelectorAll("*");

        for (int i = 0; i < list.getLength(); i++) {
            HTMLElement element = list.get(i);

            if (element.equals(el)) {
                return true;
            }
        }

        return false;
    }

    public static void check(HTMLElement element, Application app) {
        if (!HTMLBasedAppHelper.checkNode(element) && !Groupadle.getInstance().checkRectangle(app)) {
            body.appendChild(element);
        }

        if (Groupadle.getInstance().checkRectangle(app) && HTMLBasedAppHelper.checkNode(element)) {
            body.removeChild(element);
        }
    }

    public static void move(HTMLElement element, int[] pos) {
        element.getStyle().setProperty("left", pos[0] + "px");
        element.getStyle().setProperty("top", pos[1] + "px");
        element.getStyle().setProperty("width", pos[2] + "px");
        element.getStyle().setProperty("height", pos[3] + "px");
    }
}
