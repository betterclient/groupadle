package io.github.betterclient.groupadle.apps.browser;

import io.github.betterclient.groupadle.util.HTMLBasedAppHelper;
import io.github.betterclient.groupadle.desktop.Application;
import io.github.betterclient.groupadle.util.render.Color;
import io.github.betterclient.groupadle.util.render.UIRenderer;
import org.teavm.jso.dom.html.HTMLBodyElement;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLIFrameElement;

public class BrowserApp extends Application {
    public HTMLIFrameElement myFrame;
    public HTMLBodyElement body;

    public BrowserApp() {
        super("Browser", 900, 900);
        myFrame = (HTMLIFrameElement) HTMLDocument.current().createElement("iframe");
        myFrame.getStyle().setProperty("position", "absolute");
        myFrame.getStyle().setProperty("border", "none");
        this.body = HTMLDocument.current().getBody();
        myFrame.setSourceAddress("https://betterclient.github.io/z--");
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        HTMLBasedAppHelper.check(myFrame, this);

        this.renderer.fillArea(10, 20, getWidth() - 10, getHeight() - 10, Color.BLACK);
        this.renderer.renderText("Because of Cross-origin resource sharing, only some websites are allowed.", 10, 12, Color.BLACK);

        this.renderer.fillArea(getWidth() - 250, 2, getWidth() - 140, 18, Color.BLACKISH_BLACK);
        this.renderer.renderText("Z-- Compiler", this.renderer.getIdealRenderingPosForText(
                "Z-- Compiler", getWidth() - 250, 12, getWidth() - 140, 18
        ), Color.RED);

        this.renderer.fillArea(getWidth() - 130, 2, getWidth() - 10, 18, Color.BLACKISH_BLACK);
        this.renderer.renderText("Surprise link", this.renderer.getIdealRenderingPosForText(
                "Surprise link", getWidth() - 130, 12, getWidth() - 10, 18
        ), Color.RED);

        HTMLBasedAppHelper.move(myFrame, new int[] {
                (int) this.renderer.x,
                (int) (this.renderer.y + 20),
                this.getWidth(),
                this.getHeight() - 20
        });
    }

    @Override
    public void mouseClick(double mouseX, double mouseY, boolean isClicked) {
        if (UIRenderer.isPointInRectangle(mouseX, mouseY, getWidth() - 130, 2, getWidth() - 10, 18)) {
            //Surprise
            myFrame.setSourceAddress("https://www.youtube.com/embed/dQw4w9WgXcQ");
        } else if (UIRenderer.isPointInRectangle(mouseX, mouseY, getWidth() - 250, 2, getWidth() - 140, 18)) {
            //Z--
            myFrame.setSourceAddress("https://betterclient.github.io/z--");
        }
    }

    @Override
    public void close() {
        if (HTMLBasedAppHelper.checkNode(myFrame)) body.removeChild(myFrame);
    }

    @Override
    public void minimize() {
        if (HTMLBasedAppHelper.checkNode(myFrame)) body.removeChild(myFrame);
    }
}
