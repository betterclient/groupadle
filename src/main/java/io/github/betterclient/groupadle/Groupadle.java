package io.github.betterclient.groupadle;

import io.github.betterclient.groupadle.desktop.Application;
import io.github.betterclient.groupadle.desktop.DesktopIcon;
import io.github.betterclient.groupadle.event.EventManager;
import io.github.betterclient.groupadle.impl.TerminalApp;
import io.github.betterclient.groupadle.render.*;
import io.github.betterclient.groupadle.util.OutputReset;
import org.teavm.jso.browser.Window;
import org.teavm.jso.canvas.CanvasImageSource;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLImageElement;

import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * Does all things related to the OS
 */
public class Groupadle {
    private static Groupadle instance;
    public RenderHolder renderer;
    public CanvasImageSource backgroundImage;
    public List<DesktopIcon> desktopIcons;
    public EventManager event;

    public Groupadle() {
        this.renderer = new RenderHolder(new Renderer(), new DesktopRenderer(), new TaskbarRenderer(), new TopBarRenderer(), new ApplicationRenderer());
        this.backgroundImage = getOrCreateBackgroundImage();
        this.desktopIcons = new ArrayList<>();
        this.event = new EventManager();

        //web
        HTMLImageElement img = (HTMLImageElement) HTMLDocument.current().createElement("img");
        img.setSrc("terminal.png");
        DesktopIcon icon = new DesktopIcon(new OutputReset<>(TerminalApp::new), "Get Started", img);

        this.desktopIcons.add(icon);
    }

    private CanvasImageSource getOrCreateBackgroundImage() {
        HTMLImageElement imageEl = (HTMLImageElement) HTMLDocument.current().createElement("img");

        String image = Window.current().getLocalStorage().getItem("background");
        if (image == null || image.isEmpty()) {
            //Create
            try {
                @SuppressWarnings("deprecation") //no
                URL url = new URL("https://i.imgur.com/4KKqsWp.jpeg");
                InputStream stream = url.openConnection().getInputStream();
                image = Base64.getEncoder().encodeToString(stream.readAllBytes());
                stream.close();

                Window.current().getLocalStorage().setItem("background", image);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        imageEl.setSrc("data:image/png;base64," + image);
        return imageEl;
    }

    public static Groupadle create() {
        return instance = new Groupadle();
    }

    public static Groupadle getInstance() {
        return instance;
    }

    public void handleClick(int x, int y, boolean isClicked) {
        for (Application screenApplication : screenApplications) {
            if (screenApplication.check(x, y)) {
                if (focusedApplication == null) {
                    if (isClicked)
                        focusedApplication = screenApplication;
                } else {
                    if (focusedApplication.equals(screenApplication)) {
                        if (focusedApplication.renderer == null) return;

                        focusedApplication.mouseClick(x - focusedApplication.renderer.x, y - focusedApplication.renderer.y, isClicked);
                    } else {
                        if (isClicked)
                            focusedApplication = screenApplication;
                    }
                }
                return;
            }
        }

        this.renderer.mainRenderer().click(x, y, isClicked);
    }

    /**
     * List of opened applications
     */
    public List<Application> applications = new ArrayList<>();

    /**
     * Order of opened applications
     * first element -> most front application
     * last element -> most back application
     */
    public List<Application> screenApplications = new ArrayList<>();

    /**
     * Minimized applications
     */
    public List<Application> minimizedApplications = new ArrayList<>();

    /**
     * Focused application
     */
    public Application focusedApplication = null;

    public Map<Application, double[]> isFocusedMap = new HashMap<>();

    //-------------------------------------------APP UTILS-------------------------------------------
    public void launch(Application app) {
        this.applications.add(app);
        this.screenApplications.addFirst(app);
        this.focusedApplication = app;
    }

    public void close(Application app) {
        this.applications.remove(app);
        this.screenApplications.remove(app);

        if (focusedApplication == app) {
            focusedApplication = null;
        }
    }

    public void maximize(Application application) {
        if (isFocusedMap.containsKey(application)) {
            double[] map = isFocusedMap.remove(application);

            application.renderer.x = map[0];
            application.renderer.y = map[1];

            application.setWidth((int) map[2]);
            application.setHeight((int) map[3]);
            return;
        } else {
            isFocusedMap.put(application, new double[] {
               application.renderer.x,
               application.renderer.y,
               application.getWidth(),
               application.getHeight()
            });
        }

        this.screenApplications.remove(application);
        this.screenApplications.addFirst(application);
        this.focusedApplication = application;

        application.renderer.x = 70;
        application.renderer.y = 70;

        application.setWidth(Window.current().getInnerWidth() - 70);
        application.setHeight(Window.current().getInnerHeight() - 70);
    }

    public void minimize(Application application) {
        this.screenApplications.remove(application);
        this.minimizedApplications.add(application);

        if (this.focusedApplication == application) {
            this.focusedApplication = null;
        }
    }
}