package io.github.betterclient.groupadle.apps.imageviewer;

import io.github.betterclient.groupadle.util.HTMLBasedAppHelper;
import io.github.betterclient.groupadle.desktop.Application;
import io.github.betterclient.groupadle.util.Image;
import io.github.betterclient.groupadle.util.render.Color;
import org.teavm.jso.dom.html.HTMLBodyElement;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLInputElement;

public class ImageViewer extends Application {
    public HTMLInputElement inputElement;
    public HTMLBodyElement body;

    public ImageViewer() {
        super("Image Viewer", 600, 600);
        inputElement = (HTMLInputElement) HTMLDocument.current().createElement("input");
        inputElement.setType("text");
        inputElement.setValue("settings.png");
        inputElement.getStyle().setProperty("position", "absolute");
        inputElement.getStyle().setProperty("border", "none");

        this.body = HTMLDocument.current().getBody();
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        HTMLBasedAppHelper.check(inputElement, this);

        this.renderer.fillArea(10, 10, getWidth() - 10, getHeight() - 10, Color.BLACK);
        String a = inputElement.getValue();
        if (a.trim().isEmpty()) a = "settings.png";

        this.renderer.renderImage(0, 40, getWidth(), getHeight() - 50, new Image(a).get());

        HTMLBasedAppHelper.move(inputElement, new int[] {
                (int) this.renderer.x,
                (int) this.renderer.y,
                this.getWidth(), 40
        });
        this.renderer.renderText("examples: \"settings.png\" \"imageviewer.png\" \"browser.png\" \"helloworld.png\"", 10, getHeight() - 25, Color.WHITE);
    }

    @Override
    public void mouseClick(double mouseX, double mouseY, boolean isClicked) {}

    @Override
    public void close() {
        if (HTMLBasedAppHelper.checkNode(inputElement)) body.removeChild(inputElement);
    }

    @Override
    public void minimize() {
        if (HTMLBasedAppHelper.checkNode(inputElement)) body.removeChild(inputElement);
    }
}
