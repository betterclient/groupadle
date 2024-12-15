package io.github.betterclient.groupadle.apps.dvd;

import io.github.betterclient.groupadle.desktop.Application;
import io.github.betterclient.groupadle.util.render.Color;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DVDApp extends Application {
    public int dx = new Random().nextInt(500);
    public int dy = new Random().nextInt(500);
    public int dMotX = 2, dMotY = 2;
    public int dwidth = 100;
    public int dheight = 100;
    private final Timer tiemr = new Timer();

    public DVDApp() {
        super("DVD", 500, 500);
        tiemr.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 1000, 10);
    }

    public void update() {
        dx += dMotX;
        dy += dMotY;

        if (dx <= 0 || dx + dwidth >= getWidth()) {
            dMotX = -dMotX;
        }
        if (dy <= 0 || dy + dheight >= getHeight()) {
            dMotY = -dMotY;
        }
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        this.renderer.fillArea(5, 5, getWidth() - 5, getHeight() - 5, Color.BLACK);

        this.renderer.fillArea(dx, dy, dx + dwidth, dy + dheight, Color.WHITE);
        this.renderer.renderImage(dx, dy, dx + dwidth, dy + dheight, icon.icon());
    }

    @Override
    public void mouseClick(double mouseX, double mouseY, boolean isClicked) {}

    @Override
    public void close() {
        tiemr.cancel();
    }
}
