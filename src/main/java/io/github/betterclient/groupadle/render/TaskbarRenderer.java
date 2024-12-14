package io.github.betterclient.groupadle.render;

import io.github.betterclient.groupadle.Groupadle;
import io.github.betterclient.groupadle.desktop.Application;
import io.github.betterclient.groupadle.desktop.DesktopIcon;
import io.github.betterclient.groupadle.util.render.Color;
import io.github.betterclient.groupadle.util.render.UIRenderer;
import org.teavm.jso.browser.Window;

public class TaskbarRenderer {
    public void render(UIRenderer renderer) {
        renderer.fillRoundRect(10, 60, 60, renderer.getHeight() - 30, Color.TASKBAR_COLOR, 10f);

        int curY = 80;
        for (Application application : Groupadle.getInstance().applications) {
            if (curY > renderer.getHeight()) break;
            DesktopIcon icon = application.icon;

            renderer.fillRoundRect(20, curY, 50, curY + 30, Color.APP_BACKGROUND_COLOR, 10f);
            renderer.renderImage(20, curY, 50, curY + 30, icon.icon());

            curY += 40;
        }
    }

    public boolean click(int x, int y) {
        int curY = 80;
        Groupadle groupadle = Groupadle.getInstance();
        for (Application application : groupadle.applications) {
            if (curY > Window.current().getInnerHeight()) break;
            if (UIRenderer.isPointInRectangle(x, y, 20, curY, 50, curY + 30)) {
                groupadle.focus(application);
                return true;
            }

            curY += 40;
        }
        return false;
    }
}
