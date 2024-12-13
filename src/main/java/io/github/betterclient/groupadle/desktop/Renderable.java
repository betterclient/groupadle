package io.github.betterclient.groupadle.desktop;

import io.github.betterclient.groupadle.util.render.Color;
import io.github.betterclient.groupadle.util.render.UIRenderer;
import org.teavm.jso.canvas.TextMetrics;

public class Renderable {
    public static final Runnable EMPTY_RUNNABLE = () -> {};

    public double x, y;
    private double width = 0, height = 0;

    public Runnable render = EMPTY_RUNNABLE;
    private UIRenderer vr;

    public Renderable(UIRenderer renderer, int x, int y, double width, double height) {
        this.vr = renderer;
        this.x = x;
        this.y = y;

        this.width = Math.max(width, 0);
        this.height = Math.max(height, 0);
    }

    public void setHeight(double height) {
        this.height = Math.max(height, 0);
    }

    public void setWidth(double width) {
        this.width = Math.max(width, 0);
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

    public Renderable renderText(String text, double x, double y, Color color) {
        Runnable oldRender = render;
        render = () -> {
            oldRender.run();
            vr.renderText(text, this.getX() + x, this.getY() + y, color);
        };

        return this;
    }

    public Renderable renderText(String text, int[] pos, Color color) {
        return this.renderText(text, pos[0], pos[1], color);
    }

    public Renderable fillArea(double startX, double startY, double endX, double endY, Color color) {
        Runnable oldRender = render;
        render = () -> {
            oldRender.run();
            vr.fillRoundRect(this.getX() + startX, this.getY() + startY, this.getX() + endX, this.getY() + endY, color, 3);
        };


        return this;
    }

    public void renderWithXY(int x, int y) {
        this.x = x;
        this.y = y;

        this.render.run();
    }

    public void render() {
        this.render.run();
    }

    public double[] getIdealRenderingPosForText(String w, double x1, double y1, double endX, double endY) {
        TextMetrics metrics = this.vr.getMetrics(w);
        double afterWidth = metrics.getWidth();
        double afterHeight = metrics.getActualBoundingBoxAscent() + metrics.getActualBoundingBoxDescent();

        double tx = ((((endX - x1) / 2) - (afterWidth / 2)));
        double ty = ((((endY - y1) / 2) - (afterHeight / 2)));

        double[] pos = new double[]{x1 + tx, y1 + ty};

        pos[0] = pos[0] - this.getX();
        pos[1] = pos[1] - this.getY();

        return pos;
    }

    public void setVr(UIRenderer vr) {
        this.vr = vr;
    }
}
