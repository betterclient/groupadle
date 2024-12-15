package io.github.betterclient.groupadle.render;

import io.github.betterclient.groupadle.Groupadle;
import io.github.betterclient.groupadle.util.render.UIRenderer;

public class Renderer {
    public void render(UIRenderer renderer, double delta) {
        Groupadle groupadle = Groupadle.getInstance();
        RenderHolder holder = groupadle.renderer;

        //Initialize renderer
        renderer.ready();

        //Render desktop
        holder.desktopRenderer().render(renderer);

        //Render taskbar
        holder.taskbarRenderer().render(renderer);

        //Render top bar
        holder.topBarRenderer().render(renderer);

        //Render apps
        holder.appRenderer().render(renderer, delta);
    }

    public void click(int x, int y, boolean isClicked) {
        RenderHolder holder = Groupadle.getInstance().renderer;

        if (holder.appRenderer().click(x, y, isClicked)) return;
        if (holder.appRenderer().click0(x, y, isClicked)) return;
        if (holder.desktopRenderer().click(x, y, isClicked)) return;

        holder.taskbarRenderer().click(x, y);
    }
}
