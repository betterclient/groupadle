package io.github.betterclient.groupadle.render;

import io.github.betterclient.groupadle.Groupadle;
import io.github.betterclient.groupadle.desktop.AppPosition;
import io.github.betterclient.groupadle.desktop.Application;
import io.github.betterclient.groupadle.desktop.Renderable;
import io.github.betterclient.groupadle.util.render.Color;
import io.github.betterclient.groupadle.util.render.UIRenderer;

public class ApplicationRenderer {
    public void render(UIRenderer renderer, double delta) {
        if (isHoldingApplication) {
            holdedApplication.renderer.x = Groupadle.getInstance().event.mouseX - holdPosX;
            holdedApplication.renderer.y = Groupadle.getInstance().event.mouseY - holdPosY;
        }

        for (Application screenApplication : Groupadle.getInstance().screenApplications.reversed()) {
            render(screenApplication, renderer, delta);
        }
    }

    private void render(Application application, UIRenderer renderer, double delta) {
        Groupadle groupadle = Groupadle.getInstance();
        int width = application.getWidth();
        int height = application.getHeight();
        if (application.renderer == null) {
            application.renderer = new Renderable(renderer, 0, 0, width, height);
            int[] pos = application.setPosition(AppPosition.CENTER);
            application.renderer.x = pos[0];
            application.renderer.y = pos[1];
        } else {
            application.renderer.setVr(renderer);
        }

        double mx = groupadle.event.mouseX - application.renderer.x;
        double my = groupadle.event.mouseY - application.renderer.y;

        mx = Math.max(mx, 0); mx = Math.min(mx, width - 2);
        my = Math.max(my, 2); my = Math.min(my, height);

        renderTopBar(application, renderer, width);

        renderer.fillRect(
                application.renderer.x,
                application.renderer.y,
                application.renderer.x + width,
                application.renderer.y + height,
                Color.WHITE
        );

        //Clip the renderer
        renderer.addFixedSize(application);

        application.renderer.reset();
        application.render(
                mx,
                my,
                delta
        );
        application.renderer.render();

        renderer.removeFixedSize();
    }

    private void renderTopBar(Application application, UIRenderer renderer, double width) {
        //Bar
        renderer.fillRect(
                application.renderer.x,
                application.renderer.y - 20,
                application.renderer.x + width, application.renderer.y,
                Color.APPBAR_COLOR
        );

        //Name
        double[] str = application.renderer.getIdealRenderingPosForText(application.name, application.renderer.x, application.renderer.y - 20, application.renderer.x + width, application.renderer.y);
        renderer.renderText(application.name, str[0] + application.renderer.getX() - 10, str[1] + application.renderer.getY() + 10, Color.WHITE);

        //Buttons
        double[] pos = new double[] {application.renderer.x, application.renderer.y - 20};
        renderer.drawCircle(pos[0] + 20, pos[1] + 10, 4f, Color.CLOSE_APP_COLOR);
        renderer.drawCircle(pos[0] + 40, pos[1] + 10, 4f, Color.MAXIMIZE_APP_COLOR);
        renderer.drawCircle(pos[0] + 60, pos[1] + 10, 4f, Color.MINIMIZE_APP_COLOR);
    }

    public void click(int x, int y, boolean isClicked) {
        for (Application screenApplication : Groupadle.getInstance().screenApplications.reversed()) {
            if(tryHandleTopBarClick(screenApplication, x, y, isClicked)) return;
        }
    }

    public boolean isHoldingApplication = false;
    public Application holdedApplication = null;
    public double holdPosX, holdPosY;

    private boolean tryHandleTopBarClick(Application application, int x, int y, boolean isClicked) {
        if (isHoldingApplication && !isClicked) isHoldingApplication = false;

        if (!UIRenderer.isPointInRectangle(x, y, application.renderer.x,
                application.renderer.y - 20,
                application.renderer.x + application.getWidth(), application.renderer.y)) {
            return false;
        }

        double[] pos = new double[] {application.renderer.x, application.renderer.y - 20};
        if (UIRenderer.isPointInRectangle(x, y,
                pos[0] + 12, pos[1] + 2, pos[0] + 28, pos[1] + 18)) {

            if (!isClicked) Groupadle.getInstance().close(application);

        } else if (UIRenderer.isPointInRectangle(x, y,
                pos[0] + 32, pos[1] + 2, pos[0] + 48, pos[1] + 18)) {

            if (!isClicked) Groupadle.getInstance().maximize(application);

        } else if (UIRenderer.isPointInRectangle(x, y,
                pos[0] + 52, pos[1] + 2, pos[0] + 68, pos[1] + 18)) {

            if (!isClicked) Groupadle.getInstance().minimize(application);

        } else {
            if (Groupadle.getInstance().isFocusedMap.containsKey(application)) {
                //Hold with old position
                isHoldingApplication = isClicked;
                if (isClicked) {
                    holdPosX = x - application.renderer.x;
                    holdPosY = y - application.renderer.y;
                    holdedApplication = application;
                }

                //move to new position

                if (isClicked) Groupadle.getInstance().maximize(application);
                return true;
            }

            //Move!
            isHoldingApplication = isClicked;
            if (isClicked) {
                holdPosX = x - application.renderer.x;
                holdPosY = y - application.renderer.y;
                holdedApplication = application;
            }
        }

        return true;
    }
}
