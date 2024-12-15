package io.github.betterclient.groupadle.apps.calculator;

import io.github.betterclient.groupadle.desktop.Application;
import io.github.betterclient.groupadle.util.render.Color;
import io.github.betterclient.groupadle.util.render.UIRenderer;
import org.teavm.jso.JSBody;

public class CalculatorApp extends Application {
    public String currentEval = "O: The calculator is bad:/    ";

    public CalculatorApp() {
        super("Calculator", 300, 400);
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        int width = getWidth();
        int height = getHeight();

        renderer.fillArea(0, 98, width, 100, Color.BLACK);
        renderer.fillArea(width / 4D - 2, 98, width / 4D + 2, height, Color.BLACK);
        renderer.fillArea(width / 4D * 2 - 2, 98, width / 4D * 2 + 2, height, Color.BLACK);
        renderer.fillArea(width / 4D * 3 - 2, 98, width / 4D * 3 + 2, height, Color.BLACK);

        renderer.fillArea(0, height / 5D * 2 - 2, width, height / 5D * 2 + 2, Color.BLACK);
        renderer.fillArea(0, height / 5D * 3 - 2, width, height / 5D * 3 + 2, Color.BLACK);
        renderer.fillArea(0, height / 5D * 4 - 2, width, height / 5D * 4 + 2, Color.BLACK);

        renderer.setSize("30px Arial");

        //life is too short to write good code
        renderer.renderText("1", renderer.getIdealRenderingPosForText("1", 0, 100, width / 4D - 2, height / 5D * 2 - 2), Color.GREEN);
        renderer.renderText("2", renderer.getIdealRenderingPosForText("2",width / 4D + 2, 100, width / 4D * 2 - 2, height / 5D * 2 - 2), Color.GREEN);
        renderer.renderText("3", renderer.getIdealRenderingPosForText("3", width / 4D * 2 + 2, 100, width / 4D * 3 - 2, height / 5D * 2 - 2), Color.GREEN);
        renderer.renderText("+", renderer.getIdealRenderingPosForText("+", width / 4D * 3 + 2, 100, width / 4D * 4 - 2, height / 5D * 2 - 2), Color.GREEN);

        renderer.renderText("4", renderer.getIdealRenderingPosForText("4", 0, 200, width / 4D - 2, height / 5D * 3 - 2), Color.GREEN);
        renderer.renderText("5", renderer.getIdealRenderingPosForText("5",width / 4D + 2, 200, width / 4D * 2 - 2, height / 5D * 3 - 2), Color.GREEN);
        renderer.renderText("6", renderer.getIdealRenderingPosForText("6", width / 4D * 2 + 2, 200, width / 4D * 3 - 2, height / 5D * 3 - 2), Color.GREEN);
        renderer.renderText("-", renderer.getIdealRenderingPosForText("-", width / 4D * 3 + 2, 200, width / 4D * 4 - 2, height / 5D * 3 - 2), Color.GREEN);

        renderer.renderText("7", renderer.getIdealRenderingPosForText("7", 0, 300, width / 4D - 2, height / 5D * 4 - 2), Color.GREEN);
        renderer.renderText("8", renderer.getIdealRenderingPosForText("8",width / 4D + 2, 300, width / 4D * 2 - 2, height / 5D * 4 - 2), Color.GREEN);
        renderer.renderText("9", renderer.getIdealRenderingPosForText("9", width / 4D * 2 + 2, 300, width / 4D * 3 - 2, height / 5D * 4 - 2), Color.GREEN);
        renderer.renderText("*", renderer.getIdealRenderingPosForText("*", width / 4D * 3 + 2, 300, width / 4D * 4 - 2, height / 5D * 4 - 2), Color.GREEN);

        renderer.renderText("/", renderer.getIdealRenderingPosForText("/", 0, 400, width / 4D - 2, height - 60), Color.GREEN);
        renderer.renderText("0", renderer.getIdealRenderingPosForText("0",width / 4D + 2, 400, width / 4D * 2 - 2, height - 60), Color.GREEN);
        renderer.renderText(".", renderer.getIdealRenderingPosForText(".", width / 4D * 2 + 2, 400, width / 4D * 3 - 2, height - 60), Color.GREEN);
        renderer.renderText("=", renderer.getIdealRenderingPosForText("=", width / 4D * 3 + 2, 400, width / 4D * 4 - 2, height - 60), Color.GREEN);

        renderer.renderText(currentEval, renderer.getIdealRenderingPosForText(currentEval, 0, 0, getWidth(), 100), Color.RED);
    }

    @Override
    public void mouseClick(double mouseX, double mouseY, boolean isClicked) {
        if (!isClicked) return;

        int width = getWidth();
        int height = getHeight();

        if (UIRenderer.isPointInRectangle(mouseX, mouseY, 0, 100, width / 4D - 2, height / 5D * 2 - 2)) {
            resetEval();
            currentEval += "1";
        }

        if (UIRenderer.isPointInRectangle(mouseX, mouseY, width / 4D + 2, 100, width / 4D * 2 - 2, height / 5D * 2 - 2)) {
            resetEval();
            currentEval += "2";
        }

        if (UIRenderer.isPointInRectangle(mouseX, mouseY, width / 4D * 2 + 2, 100, width / 4D * 3 - 2, height / 5D * 2 - 2)) {
            resetEval();
            currentEval += "3";
        }

        if (UIRenderer.isPointInRectangle(mouseX, mouseY, width / 4D * 3 + 2, 100, width / 4D * 4 - 2, height / 5D * 2 - 2)) {
            resetEval();
            currentEval += "+";
        }

        if (UIRenderer.isPointInRectangle(mouseX, mouseY, 0, 200, width / 4D - 2, height / 5D * 3 - 2)) {
            resetEval();
            currentEval += "4";
        }

        if (UIRenderer.isPointInRectangle(mouseX, mouseY, width / 4D + 2, 200, width / 4D * 2 - 2, height / 5D * 3 - 2)) {
            resetEval();
            currentEval += "5";
        }

        if (UIRenderer.isPointInRectangle(mouseX, mouseY, width / 4D * 2 + 2, 200, width / 4D * 3 - 2, height / 5D * 3 - 2)) {
            resetEval();
            currentEval += "6";
        }

        if (UIRenderer.isPointInRectangle(mouseX, mouseY, width / 4D * 3 + 2, 200, width / 4D * 4 - 2, height / 5D * 3 - 2)) {
            resetEval();
            currentEval += "-";
        }

        if (UIRenderer.isPointInRectangle(mouseX, mouseY, 0, 300, width / 4D - 2, height / 5D * 4 - 2)) {
            resetEval();
            currentEval += "7";
        }

        if (UIRenderer.isPointInRectangle(mouseX, mouseY, width / 4D + 2, 300, width / 4D * 2 - 2, height / 5D * 4 - 2)) {
            resetEval();
            currentEval += "8";
        }

        if (UIRenderer.isPointInRectangle(mouseX, mouseY, width / 4D * 2 + 2, 300, width / 4D * 3 - 2, height / 5D * 4 - 2)) {
            resetEval();
            currentEval += "9";
        }

        if (UIRenderer.isPointInRectangle(mouseX, mouseY, width / 4D * 3 + 2, 300, width / 4D * 4 - 2, height / 5D * 4 - 2)) {
            resetEval();
            currentEval += "*";
        }

        if (UIRenderer.isPointInRectangle(mouseX, mouseY, 0, 300, width / 4D - 2, height - 60)) {
            resetEval();
            currentEval += "/";
        }

        if (UIRenderer.isPointInRectangle(mouseX, mouseY, width / 4D + 2, 300, width / 4D * 2 - 2, height - 60)) {
            resetEval();
            currentEval += "0";
        }

        if (UIRenderer.isPointInRectangle(mouseX, mouseY, width / 4D * 2 + 2, 300, width / 4D * 3 - 2, height - 60)) {
            resetEval();
            currentEval += ".";
        }

        if (UIRenderer.isPointInRectangle(mouseX, mouseY, width / 4D * 3 + 2, 300, width / 4D * 4 - 2, height - 60)) {
            //eval
            try {
                currentEval = "O: " + eval(currentEval);
            } catch (Exception e) {
                currentEval = "O: Error!";
            }
        }
    }

    private void resetEval() {
        if (currentEval.startsWith("O: "))
            currentEval = "";
    }

    @JSBody(params = "str", script = "return eval(str);")
    public static native String eval(String str);
}