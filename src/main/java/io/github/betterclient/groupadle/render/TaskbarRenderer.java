package io.github.betterclient.groupadle.render;

import io.github.betterclient.groupadle.util.render.Color;
import io.github.betterclient.groupadle.util.render.UIRenderer;

public class TaskbarRenderer {
    public void render(UIRenderer renderer) {
        renderer.fillRoundRect(10, 60, 60, renderer.getHeight() - 30, Color.TASKBAR_COLOR, 10f);
    }
}
