package io.github.betterclient.groupadle.util;

import org.teavm.jso.JSBody;
import org.teavm.jso.canvas.CanvasImageSource;

public class Image {
    private final String img;
    public Image(String image) {
        this.img = image;
    }

    public CanvasImageSource get() {
        return (CanvasImageSource) get(this.img);
    }

    @JSBody(script = """
            const val = window.document.createElement("img");
            val.src = set;
            return val;
            """, params = {"set"})
    public static native Object get(String set);
}
