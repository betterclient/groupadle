package io.github.betterclient.groupadle;

import io.github.betterclient.groupadle.apps.calculator.CalculatorApp;
import io.github.betterclient.groupadle.apps.dvd.DVDApp;
import io.github.betterclient.groupadle.apps.info.InfoApp;
import io.github.betterclient.groupadle.apps.snake.SnakeGameApp;
import io.github.betterclient.groupadle.util.HTMLBasedAppHelper;
import io.github.betterclient.groupadle.apps.imageviewer.ImageViewerApp;
import io.github.betterclient.groupadle.desktop.Application;
import io.github.betterclient.groupadle.desktop.DesktopIcon;
import io.github.betterclient.groupadle.event.EventManager;
import io.github.betterclient.groupadle.apps.browser.BrowserApp;
import io.github.betterclient.groupadle.apps.helloworld.HelloWorldApp;
import io.github.betterclient.groupadle.render.*;
import io.github.betterclient.groupadle.util.Image;
import io.github.betterclient.groupadle.util.OutputReset;
import org.teavm.jso.browser.Window;
import org.teavm.jso.canvas.CanvasImageSource;

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

        //SOON
        //this.desktopIcons.add(new DesktopIcon(new OutputReset<>(TerminalApp::new), "Get Started", new Image("terminal.png")));

        HTMLBasedAppHelper.init();
        this.desktopIcons.add(new DesktopIcon(new OutputReset<>(InfoApp::new), "Info", new Image("info.png").get()));
        this.desktopIcons.add(new DesktopIcon(new OutputReset<>(HelloWorldApp::new), "Hello World!", new Image("helloworld.png").get()));
        //this.desktopIcons.add(new DesktopIcon(new OutputReset<>(SettingsApp::new), "Settings!", new Image("settings.png").get()));
        this.desktopIcons.add(new DesktopIcon(new OutputReset<>(BrowserApp::new), "Browser", new Image("browser.png").get()));
        this.desktopIcons.add(new DesktopIcon(new OutputReset<>(ImageViewerApp::new), "Image Viewer", new Image("imageviewer.png").get()));
        this.desktopIcons.add(new DesktopIcon(new OutputReset<>(CalculatorApp::new), "Calculator", new Image("calculator.png").get()));
        this.desktopIcons.add(new DesktopIcon(new OutputReset<>(SnakeGameApp::new), "Snake", new Image("snake.png").get()));
        this.desktopIcons.add(new DesktopIcon(new OutputReset<>(DVDApp::new), "DVD", new Image("dvd.png").get()));
    }

    private CanvasImageSource getOrCreateBackgroundImage() {
        String image = Window.current().getLocalStorage().getItem("background");
        if (image == null || image.isEmpty() || image.equals("undefined")) {
            //Create
            try {
                @SuppressWarnings("deprecation") //no
                URL url = new URL("https://i.imgur.com/4KKqsWp.jpeg");
                InputStream stream = url.openConnection().getInputStream();
                image = Base64.getEncoder().encodeToString(stream.readAllBytes());
                stream.close();
                image = "data:image/png;base64," + image;

                Window.current().getLocalStorage().setItem("background", image);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return new Image(image).get();
    }

    public static Groupadle create() {
        return instance = new Groupadle();
    }

    public static Groupadle getInstance() {
        return instance;
    }

    public void handleClick(int x, int y, boolean isClicked) {
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
    public void launch(DesktopIcon icon) {
        Application instance1 = icon.app().getInstance();

        instance1.icon = icon;

        this.applications.add(instance1);
        this.screenApplications.addFirst(instance1);
        this.focusedApplication = instance1;
    }

    public void focus(Application application) {
        this.focusedApplication = application;
        this.screenApplications.remove(application);
        this.screenApplications.addFirst(application);
    }

    public void close(Application app) {
        app.close();
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
        application.minimize();
        this.screenApplications.remove(application);
        this.minimizedApplications.add(application);

        if (this.focusedApplication == application) {
            this.focusedApplication = null;
        }
    }

    /**
     * Check if anything obstructs the rectangle of the app given
     * @param app app given
     * @return if anything is obstructed
     */
    public boolean checkRectangle(Application app) {
        if (!this.screenApplications.contains(app)) return true;
        if (this.screenApplications.getFirst().equals(app)) return false;

        for (Application application : this.screenApplications) {
            if (application.equals(app)) return false;

            if (app.overlaps(application)) {
                return true;
            }
        }

        //Impossible to reach (btw)
        return false;
    }
}