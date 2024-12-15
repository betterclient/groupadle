package io.github.betterclient.groupadle.apps.settings_unused;

import io.github.betterclient.groupadle.desktop.Application;
import io.github.betterclient.groupadle.util.render.Color;
import io.github.betterclient.groupadle.util.render.UIRenderer;

@SuppressWarnings("unused")
public class SettingsApp extends Application {
    public SettingsApp() {
        super("Settings", 600, 300);
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        this.renderer.fillArea(10, 10, 300, 30, Color.BLUE);

        //i literally cannot get this working
        //this.renderer.renderText("Change background", this.renderer.getIdealRenderingPosForText("Change background", 10, 25, 300, 30), Color.BLACK); //25 is intentional


    }

    @Override
    @SuppressWarnings("all")
    public void mouseClick(double mouseX, double mouseY, boolean isClicked) {
        if (UIRenderer.isPointInRectangle(mouseX, mouseY, 10, 10, 300, 30) && !isClicked) {

        }
    }
}