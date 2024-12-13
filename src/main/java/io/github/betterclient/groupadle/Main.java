package io.github.betterclient.groupadle;

import io.github.betterclient.groupadle.util.render.UIRenderer;
import org.teavm.jso.browser.AnimationFrameCallback;
import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.events.MouseEvent;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.dom.html.HTMLDocument;

import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {
        Groupadle groupadle = Groupadle.create();

        HTMLCanvasElement canvasElement = (HTMLCanvasElement) HTMLDocument.current().getElementById("SIGMA_CANVAS");

        AnimationFrameCallback[] frame = new AnimationFrameCallback[1];
        AtomicReference<Double> previousTime = new AtomicReference<>(0D);
        groupadle.event.register();
        frame[0] = timestamp -> {
            double delta = timestamp - previousTime.get();
            previousTime.set(timestamp);

            groupadle.renderer.mainRenderer().render(new UIRenderer(canvasElement), delta);
            Window.requestAnimationFrame(frame[0]);
        };
        Window.requestAnimationFrame(frame[0]);
    }
}
