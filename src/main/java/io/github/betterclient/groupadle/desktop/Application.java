package io.github.betterclient.groupadle.desktop;

import org.teavm.jso.browser.Window;

public abstract class Application {
    public Renderable renderer;
    public final String name;
    public DesktopIcon icon;
    private int width, height;

    protected Application(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public abstract void render(double mouseX, double mouseY, double delta);
    public void mouseClick(double mouseX, double mouseY, boolean isClicked) {}
    public void keyboard(int charCode) {}

    public void close() {}

    public void minimize() {}

    public void setWidth(int width) {
        this.setSize(
                width,
                getHeight()
        );
    }

    public void setHeight(int height) {
        this.setSize(
                getWidth(),
                height
        );
    }

    /**
     * Call this from either {@link Application#setWidth(int)} or {@link Application#setHeight(int)}
     * This is used to confirm the size change
     * @param width width
     * @param height height
     */
    protected final void setSize(int width, int height) {
        this.width = width;
        this.height = height;

        this.renderer.width = width;
        this.renderer.height = height;
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

    public final boolean overlaps(Application other) {
        if (this.renderer == null || other.renderer == null) return false;

        return this.renderer.x + this.width > other.renderer.x &&
                this.renderer.x < other.renderer.x + other.width &&
                this.renderer.y + this.height > other.renderer.y &&
                this.renderer.y < other.renderer.y + other.height;
    }

    public final int[] setPosition() {
        return new int[]{(Window.current().getInnerWidth() / 2) - (width / 2), (Window.current().getInnerHeight() / 2) - (height / 2)};
    }
}