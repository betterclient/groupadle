package io.github.betterclient.groupadle.apps.info;

import io.github.betterclient.groupadle.desktop.Application;
import io.github.betterclient.groupadle.util.render.Color;

import java.util.Timer;
import java.util.TimerTask;

public class InfoApp extends Application {
    public int cycleColor = 0;
    public Color[] colors = new Color[] {
            Color.WHITE,
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.APP_BACKGROUND_COLOR,
            Color.TASKBAR_COLOR,
            Color.MAXIMIZE_APP_COLOR
    };
    public Timer t = new Timer();

    public InfoApp() {
        super("Info", 400, 200);
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                cycleColor++;

                if (cycleColor == colors.length) {
                    cycleColor = 0;
                }
            }
        }, 100, 200);
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        this.renderer.fillArea(10, 10, getWidth() - 10, getHeight() - 10, Color.BLACK);

        this.renderer.setSize("15px Arial");
        this.renderer.renderText("Hello, welcome to Groupadle (web)OS", 20, 30, Color.WHITE);
        this.renderer.renderText("This is a fun project made by me", 20, 50, Color.WHITE);

        this.renderer.renderText("I have future plans for this project.", 20, 90, Color.TASKBAR_COLOR);
        this.renderer.renderText("I'm just gonna say samsung dex.", 20, 110, Color.TASKBAR_COLOR);


        this.renderer.setSize("30px Arial");
        this.renderer.renderText("- BetterClient", 200, 170, colors[cycleColor]);
    }

    @Override
    public void close() {
        this.t.cancel();
    }
}
