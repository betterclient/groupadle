package io.github.betterclient.groupadle.desktop;

import org.teavm.jso.browser.Window;

public abstract class Application {
    public Renderable renderer;
    public final String name;
    private int width, height;

    protected Application(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public abstract void render(double mouseX, double mouseY, double delta);
    public abstract void mouseClick(double mouseX, double mouseY, boolean isClicked);
    public abstract void setWidth(int width);
    public abstract void setHeight(int height);

    /**
     * Call this from either {@link Application#setWidth(int)} or {@link Application#setHeight(int)}
     * This is used to confirm the size change
     * @param width width
     * @param height height
     */
    protected void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }

    public final boolean check(double x, double y) {
        if (this.renderer == null) return false;

        return (x >= renderer.x && x <= renderer.x + renderer.getWidth()) && (y >= renderer.y && y <= renderer.y + renderer.getHeight());
    }

    public final int[] setPosition(AppPosition position) {
        int[] pos;
        int guiWidth = Window.current().getInnerWidth();
        int guiHeight = Window.current().getInnerHeight();
        switch (position) {
            case CENTER -> pos = new int[]{(guiWidth / 2) - (width / 2), (guiHeight / 2) - (height / 2)};
            case CENTER_LEFT -> pos = new int[]{0, (guiHeight / 2) - (height / 2)};
            case CENTER_RIGHT -> pos = new int[]{guiWidth - width, (guiHeight / 2) - (height / 2)};
            case CENTER_TOP -> pos = new int[]{(guiWidth / 2) - (width / 2), 0};
            case CENTER_BOTTOM -> pos = new int[]{(guiWidth / 2) - (width / 2), guiHeight - height};
            case TOP_LEFT -> pos = new int[]{0, 0};
            case TOP_RIGHT -> pos = new int[]{guiWidth - width, 0};
            case BOTTOM_LEFT -> pos = new int[]{0, guiHeight - height};
            case BOTTOM_RIGHT -> pos = new int[]{guiWidth - width, guiHeight - height};
            default -> throw new IllegalStateException("null or new position.");
        }

        return pos;
    }
}