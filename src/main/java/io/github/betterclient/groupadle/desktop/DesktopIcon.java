package io.github.betterclient.groupadle.desktop;

import io.github.betterclient.groupadle.util.OutputReset;
import org.teavm.jso.canvas.CanvasImageSource;

public record DesktopIcon(OutputReset<Application> app, String name, CanvasImageSource icon) { }