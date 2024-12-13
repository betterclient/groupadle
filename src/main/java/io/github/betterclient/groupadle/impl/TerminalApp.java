package io.github.betterclient.groupadle.impl;

import io.github.betterclient.groupadle.desktop.Application;
import io.github.betterclient.groupadle.util.render.Color;

public class TerminalApp extends Application {
    public TerminalApp() {
        super("Terminal", 300, 300);
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        this.renderer.fillArea(0, 0, getWidth(), getHeight(), Color.BLUE);

        this.renderer.renderText("Hello world!", mouseX, mouseY, Color.WHITE);
    }

    @Override
    public void mouseClick(double mouseX, double mouseY, boolean isClicked) {

    }

    @Override
    public void setWidth(int width) {
        this.setSize(
                width,
                getHeight()
        );
    }

    @Override
    public void setHeight(int height) {
        this.setSize(
                getWidth(),
                height
        );
    }
}
