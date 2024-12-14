package io.github.betterclient.groupadle.render;

import io.github.betterclient.groupadle.Groupadle;
import io.github.betterclient.groupadle.desktop.Application;
import io.github.betterclient.groupadle.desktop.DesktopIcon;
import io.github.betterclient.groupadle.util.render.Color;
import io.github.betterclient.groupadle.util.render.UIRenderer;
import org.teavm.jso.browser.Window;

import java.util.HashMap;
import java.util.Map;

public class DesktopRenderer {
    public void render(UIRenderer renderer) {
        Groupadle groupadle = Groupadle.getInstance();

        renderer.renderImage(
                0, 0,
                renderer.getWidth(),
                renderer.getHeight(),
                groupadle.backgroundImage
        );

        int startX = 70;
        int startY = 70;
        renderer.setFont("15px Arial");
        for (int i = 0; i < groupadle.desktopIcons.size(); i++) {
            DesktopIcon icon = groupadle.desktopIcons.get(i);

            renderer.fillRoundRect(startX, startY, startX + 100, startY + 100, Color.APP_BACKGROUND_COLOR, 10f);
            renderer.renderImage(startX, startY, startX + 100, startY + 100, icon.icon());

            renderer.renderText(icon.name(), startX + 50 - (renderer.getMetrics(icon.name()).getWidth() / 2), startY + 110, Color.WHITE);

            if ((startX + 200) > renderer.getWidth()) {
                startX = 70;
                startY += 120;
            } else {
                startX += 120;
            }
        }
    }

    public Map<Application, Long> lastAppClickTimes = new HashMap<>();

    public boolean click(int x, int y, boolean isClicked) {
        Groupadle groupadle = Groupadle.getInstance();
        int startX = 70;
        int startY = 70;

        for (int i = 0; i < groupadle.desktopIcons.size(); i++) {
            DesktopIcon icon = groupadle.desktopIcons.get(i);

            if (UIRenderer.isPointInRectangle(x, y, startX, startY, startX + 100, startY + 100)) {
                if (!isClicked) return true;

                Application app = icon.app().getInstance();
                long millis = System.currentTimeMillis();
                if (lastAppClickTimes.containsKey(app)) {
                    long time = lastAppClickTimes.get(app);

                    if (millis - time < 200) {
                        groupadle.launch(icon);
                        lastAppClickTimes.remove(app);
                        icon.app().reset(); //reset the returned app
                    }
                } else {
                    lastAppClickTimes.put(app, millis);
                }
                return true;
            }

            if ((startX + 200) > Window.current().getInnerWidth()) {
                startX = 70;
                startY += 120;
            } else {
                startX += 120;
            }
        }

        return false;
    }
}
