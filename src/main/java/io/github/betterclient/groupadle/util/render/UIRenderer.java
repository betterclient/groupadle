package io.github.betterclient.groupadle.util.render;

import io.github.betterclient.groupadle.desktop.Application;
import org.teavm.jso.browser.Window;
import org.teavm.jso.canvas.CanvasImageSource;
import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.canvas.TextMetrics;
import org.teavm.jso.dom.html.HTMLCanvasElement;

public class UIRenderer {
    private final CanvasRenderingContext2D context2D;
    private final HTMLCanvasElement canvas;

    public UIRenderer(HTMLCanvasElement canvas) {
        this.context2D = (CanvasRenderingContext2D) canvas.getContext("2d");
        this.canvas = canvas;
    }

    public void ready() {
        //Inner childhood reference??? (inside joke)
        this.canvas.setWidth(Window.current().getInnerWidth());
        this.canvas.setHeight(Window.current().getInnerHeight());
        this.context2D.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        this.setFont("30px Arial");
    }

    /**
     * just leaving this here so I don't forget
     * <p>
     * "30px Arial"
     */
    public void setFont(String font) {
        this.context2D.setFont(font);
    }

    public int getWidth() {
        return this.canvas.getWidth();
    }

    public int getHeight() {
        return this.canvas.getHeight();
    }

    private void setColor(Color color) {
        this.context2D.setFillStyle(
                "rgba(" +
                        color.getRed() +
                        ", " +
                        color.getGreen() +
                        ", " +
                        color.getBlue() +
                        ", " +
                        (color.getAlpha() / 255f) + //Alpha is represented as a float for some f*cking reason.
                ")"
        );
    }

    //--------------ACTUAL IMPLEMENTATIONS--------------

    private boolean isEnabled = false;
    public void fillRect(double x, double y, double endX, double endY, Color color) {
        this.setColor(color);
        this.context2D.fillRect(x, y, endX - x, endY - y);
    }

    public void fillRoundRect(double x, double y, double endX, double endY, Color color, float radius) {
        this.setColor(color);
        double width = endX - x;
        double height = endY - y;

        this.context2D.beginPath();
        this.context2D.moveTo(x + radius, y);
        this.context2D.lineTo(x + width - radius, y);
        this.context2D.arcTo(x + width, y, x + width, y + height, radius);
        this.context2D.lineTo(x + width, y + height - radius);
        this.context2D.arcTo(x + width, y + height, x + width - radius, y + height, radius);
        this.context2D.lineTo(x + radius, y + height);
        this.context2D.arcTo(x, y + height, x, y + height - radius, radius);
        this.context2D.lineTo(x, y + radius);
        this.context2D.arcTo(x, y, x + radius, y, radius);
        this.context2D.closePath();
        this.context2D.stroke();
        this.context2D.fill();
    }

    public void renderImage(double x, double y, double endX, double endY, CanvasImageSource image) {
        this.context2D.drawImage(image, x, y, endX - x, endY - y);
    }

    public TextMetrics getMetrics(String text) {
        return this.context2D.measureText(text);
    }

    public void renderText(String text, double x, double y, Color color) {
        this.setColor(color);
        this.context2D.fillText(text, x, y);
    }

    public static boolean isPointInRectangle(double mouseX, double mouseY, double x, double y, double endX, double endY) {
        return (mouseX >= x && mouseX <= endX) && (mouseY >= y && mouseY <= endY);
    }

    public static boolean isPointInRectangleWH(double mouseX, double mouseY, double x, double y, double width, double height) {
        double endX = x + width;
        double endY = y + height;
        return (mouseX >= x && mouseX <= endX) && (mouseY >= y && mouseY <= endY);
    }

    public void addFixedSize(Application renderer) {
        if (isEnabled) throw new RuntimeException("Only one allowed.");

        this.context2D.save();
        double sx = (int) renderer.renderer.x;
        double sy = (int) renderer.renderer.y;

        double ex = renderer.getWidth();
        double ey = renderer.getHeight();

        isEnabled = true;

        this.context2D.beginPath();
        this.context2D.rect(sx, sy, ex, ey);
        this.context2D.clip();
    }

    public void removeFixedSize() {
        isEnabled = false;
        this.context2D.restore();
    }

    public void drawCircle(double x, double y, float radius, Color color) {
        this.setColor(color);

        this.context2D.beginPath();
        this.context2D.arc(x, y, radius * 2, 0, Math.PI * 2);
        this.context2D.fill();
    }
}
