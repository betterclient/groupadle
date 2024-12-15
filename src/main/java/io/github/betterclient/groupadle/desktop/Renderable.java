package io.github.betterclient.groupadle.desktop;

import io.github.betterclient.groupadle.util.render.Color;
import io.github.betterclient.groupadle.util.render.UIRenderer;
import org.teavm.jso.canvas.CanvasImageSource;
import org.teavm.jso.canvas.TextMetrics;

public class Renderable {
    public static final Runnable EMPTY_RUNNABLE = () -> {};

    public double x, y;
    double width, height;

    public Runnable render = EMPTY_RUNNABLE;
    private UIRenderer vr;

    public Renderable(UIRenderer renderer, int x, int y, double width, double height) {
        this.vr = renderer;
        this.x = x;
        this.y = y;

        this.width = Math.max(width, 0);
        this.height = Math.max(height, 0);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void reset() {
        this.render = EMPTY_RUNNABLE;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void renderText(String text, double x, double y, Color color) {
        Runnable oldRender = render;
        render = () -> {
            oldRender.run();
            vr.renderText(text, this.getX() + x, this.getY() + y, color);
        };

    }

    public void renderText(String text, double[] pos, Color color) {
        this.renderText(text, pos[0], pos[1], color);
    }

    public void fillArea(double startX, double startY, double endX, double endY, Color color) {
        Runnable oldRender = render;
        render = () -> {
            oldRender.run();
            vr.fillRoundRect(this.getX() + startX, this.getY() + startY, this.getX() + endX, this.getY() + endY, color, 3);
        };
    }

    public void fillAreaWH(double startX, double startY, double endX, double endY, Color color) {
        Runnable oldRender = render;
        render = () -> {
            oldRender.run();
            vr.fillRoundRect(this.getX() + startX, this.getY() + startY, this.getX() + endX + startX, this.getY() + endY + startY, color, 3);
        };

    }

    public void render() {
        this.render.run();
    }

    public double[] getIdealRenderingPosForText(String w, double x1, double y1, double endX, double endY) {
        TextMetrics metrics = this.vr.getMetrics(w);
        double afterWidth = metrics.getWidth();
        double afterHeight = /*metrics.getActualBoundingBoxAscent() + */metrics.getActualBoundingBoxDescent();

        double tx = ((((endX - x1) / 2) - (afterWidth / 2)));
        double ty = ((((endY - y1) / 2) - (afterHeight / 2)));

        return new double[]{x1 + tx, y1 + ty};
    }

    public void setVr(UIRenderer vr) {
        this.vr = vr;
    }

    public void renderImage(int x, int y, int endX, int endY, CanvasImageSource image) {
        Runnable oldRender = render;
        render = () -> {
            oldRender.run();
            vr.renderImage(this.getX() + x, this.getY() + y, this.getX() + endX, this.getY() + endY, image);
        };

    }

    public TextMetrics getMetrics(String str) {
        return this.vr.getMetrics(str);
    }

    public void setSize(String s) {
        Runnable old = render;
        render = () -> {
            old.run();
            this.vr.setFont(s);
        };
    }
}
