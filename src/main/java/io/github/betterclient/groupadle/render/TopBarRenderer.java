package io.github.betterclient.groupadle.render;

import io.github.betterclient.groupadle.util.render.Color;
import io.github.betterclient.groupadle.util.render.UIRenderer;
import org.teavm.jso.canvas.TextMetrics;

import java.time.LocalDateTime;

public class TopBarRenderer {
    public void render(UIRenderer renderer) {
        renderer.fillRect(0, 0, renderer.getWidth(), 50, Color.TOPBAR_COLOR);

        this.renderTime(renderer);

        renderer.setFont("30px Arial");
        renderer.renderText("Groupadle WebOS V1", 10, 35, Color.WHITE);
        renderer.setFont("15px Arial");
    }

    private void renderTime(UIRenderer renderer) {
        String timeText = getTime();
        TextMetrics metrics = renderer.getMetrics(timeText);
        renderer.renderText(timeText, renderer.getWidth() - metrics.getWidth() - 30, metrics.getFontBoundingBoxDescent() + 30, Color.WHITE);
    }

    private String getTime() {
        LocalDateTime time = LocalDateTime.now();
        int hours = time.getHour();
        int minutes = time.getMinute();

        String period = "AM";
        if (hours >= 12) {
            period = "PM";
            if (hours > 12) {
                hours -= 12;
            }
        } else if (hours == 0) {
            hours = 12;
        }

        return String.format("%02d:%02d %s", hours, minutes, period);
    }
}
