package io.github.betterclient.groupadle.apps.helloworld;

import io.github.betterclient.groupadle.desktop.Application;
import io.github.betterclient.groupadle.util.render.Color;

public class HelloWorldApp extends Application {
    public HelloWorldApp() {
        super("HelloWorld!", 300, 300);
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        this.renderer.fillArea(0, 0, getWidth(), getHeight(), Color.BLUE);

        this.renderer.renderText("Hello world!", mouseX, mouseY, Color.WHITE);
    }

    @Override
    public void mouseClick(double mouseX, double mouseY, boolean isClicked) {

    }
}
