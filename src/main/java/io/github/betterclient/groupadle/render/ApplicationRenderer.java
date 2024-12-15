package io.github.betterclient.groupadle.render;

import io.github.betterclient.groupadle.Groupadle;
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
        if (isSizingFromRight) {
            sizeApp.setWidth(Math.max((int) (Groupadle.getInstance().event.mouseX - sizeApp.renderer.x), 100));
            sizeApp.setHeight(Math.max((int) (Groupadle.getInstance().event.mouseY - sizeApp.renderer.y), 100));
        }

        for (Application screenApplication : Groupadle.getInstance().screenApplications.reversed()) {
            renderer.setFont("15px Arial");
            render(screenApplication, renderer, delta);
        }
    }

    private void render(Application application, UIRenderer renderer, double delta) {
        Groupadle groupadle = Groupadle.getInstance();
        int width = application.getWidth();
        int height = application.getHeight();
        if (application.renderer == null) {
            application.renderer = new Renderable(renderer, 0, 0, width, height);
            int[] pos = application.setPosition();
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
                application == Groupadle.getInstance().focusedApplication ? Color.FOCUSED_APPBAR_COLOR : Color.APPBAR_COLOR
        );

        //Name
        double[] str = application.renderer.getIdealRenderingPosForText(application.name, application.renderer.x, application.renderer.y - 20, application.renderer.x + width, application.renderer.y);
        renderer.renderText(application.name, str[0] - 10, str[1] + 5, Color.WHITE);

        //Buttons
        double[] pos = new double[] {application.renderer.x, application.renderer.y - 20};
        renderer.drawCircle(pos[0] + 20, pos[1] + 10, 4f, Color.CLOSE_APP_COLOR);
        renderer.drawCircle(pos[0] + 40, pos[1] + 10, 4f, Color.MAXIMIZE_APP_COLOR);
        renderer.drawCircle(pos[0] + 60, pos[1] + 10, 4f, Color.MINIMIZE_APP_COLOR);
    }

    public boolean click(int x, int y, boolean isClicked) {
        for (Application screenApplication : Groupadle.getInstance().screenApplications.reversed()) {
            if(tryHandleTopBarClick(screenApplication, x, y, isClicked)) return true;
        }

        return false;
    }

    public boolean isHoldingApplication = false;
    public Application holdedApplication = null;
    public double holdPosX, holdPosY;

    private boolean tryHandleTopBarClick(Application application, int x, int y, boolean isClicked) {
        if (isHoldingApplication && !isClicked) isHoldingApplication = false;
        if (isSizingFromRight && !isClicked) isSizingFromRight = false;
        if (application.renderer == null) return false;

        if (!UIRenderer.isPointInRectangle(x, y, application.renderer.x,
                application.renderer.y - 20,
                application.renderer.x + application.getWidth(), application.renderer.y)) {
                return tryHandleSizing(application, x, y, isClicked);
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
            Groupadle.getInstance().focus(application);
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
            return true;
        }

        return false;
    }

    boolean isSizingFromRight = false;
    Application sizeApp = null;

    private boolean tryHandleSizing(Application application, int x, int y, boolean isClicked) {
        //only right allowed.
        if (UIRenderer.isPointInRectangle(x, y,
                application.renderer.x + application.getWidth() - 2,
                application.renderer.y + application.getHeight() - 2,
                application.renderer.x + application.getWidth() + 2,
                application.renderer.y + application.getHeight() + 2)) {
            //Bottom right
            sizeApp = application;
            isSizingFromRight = isClicked;
            return true;
        }

        return false;
    }

    public boolean click0(int x, int y, boolean isClicked) {
        Groupadle groupadle = Groupadle.getInstance();

        for (Application screenApplication : groupadle.screenApplications) {
            if (screenApplication.check(x, y)) {
                if (groupadle.focusedApplication == null) {
                    if (isClicked)
                        groupadle.focus(screenApplication);
                } else {
                    if (groupadle.focusedApplication.equals(screenApplication)) {
                        if (groupadle.focusedApplication.renderer == null) return true;

                        groupadle.focusedApplication.mouseClick(x - groupadle.focusedApplication.renderer.x, y - groupadle.focusedApplication.renderer.y, isClicked);
                    } else {
                        if (isClicked)
                            groupadle.focus(screenApplication);
                    }
                }
                return true;
            }
        }

        return false;
    }
}
