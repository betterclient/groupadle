package io.github.betterclient.groupadle.apps.snake;

import io.github.betterclient.groupadle.desktop.Application;
import io.github.betterclient.groupadle.util.render.Color;
import io.github.betterclient.groupadle.util.render.UIRenderer;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class SnakeGameApp extends Application {
    public int tileSize = 20;
    public int score = 0;
    public List<SnakePoint> snake = new ArrayList<>();
    public final AtomicReference<Direction> currentDir = new AtomicReference<>(Direction.RIGHT);
    public SnakePoint food;
    private final Timer tiemr;

    public SnakeGameApp() {
        super("Snake", 500, 500);
        snake.add(new SnakePoint(5, 5));
        snake.add(new SnakePoint(4, 5));
        snake.add(new SnakePoint(3, 5));
        food();
        tiemr = new Timer();
        tiemr.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SnakeGameApp.this.moveSnake();
            }
        }, 1000, 200);
    }

    public void food() {
        Random rand = new Random();
        int x = rand.nextInt((getWidth() - 10) / tileSize);
        int y = rand.nextInt((getHeight() - 110) / tileSize);
        food = new SnakePoint(x, y);
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        this.renderer.fillArea(10, 100, getWidth() - 10, getHeight() - 10, Color.BLACKISH_BLACK);
        String str = "Score: " + score;

        this.renderer.setSize("30px Arial");
        this.renderer.renderText(
                str,
                this.renderer.getIdealRenderingPosForText(str, 0, 0, getWidth(), 100),
                Color.BLACK
        );

        this.renderer.fillAreaWH(60, 10, 40, 40, Color.BLACK);
        this.renderer.fillAreaWH(10, 60, 40, 40, Color.BLACK);
        this.renderer.fillAreaWH(60, 60, 40, 40, Color.BLACK);
        this.renderer.fillAreaWH(110, 60, 40, 40, Color.BLACK);

        //Game render

        for (SnakePoint snakePoint : snake) {
            if (snakePoint.equals(snake.getFirst())) {
                this.renderer.fillAreaWH(10 + snakePoint.x * tileSize, 100 + snakePoint.y * tileSize, tileSize, tileSize, Color.BLUE);
            } else {
                this.renderer.fillAreaWH(10 + snakePoint.x * tileSize, 100 + snakePoint.y * tileSize, tileSize, tileSize, Color.GREEN);
            }
        }

        this.renderer.fillAreaWH(10 + food.x * tileSize, 100 + food.y * tileSize, tileSize, tileSize, Color.RED);
    }

    public void moveSnake() {
        SnakePoint head = snake.getFirst();
        SnakePoint newHead = new SnakePoint(head.x, head.y);
        int height = getHeight() - 100;
        int width = getWidth() - 10;

        switch (currentDir.get()) {
            case UP:
                newHead.y--;
                break;
            case DOWN:
                newHead.y++;
                break;
            case LEFT:
                newHead.x--;
                break;
            case RIGHT:
                newHead.x++;
                break;
        }

        if (newHead.x < 0) newHead.x = width / tileSize - 1;
        if (newHead.x >= width / tileSize) newHead.x = 0;
        if (newHead.y < 0) newHead.y = height / tileSize - 1;
        if (newHead.y >= height / tileSize) newHead.y = 0;

        snake.addFirst(newHead);

        if (newHead.equals(food)) {
            food();
            score++;
        } else {
            snake.removeLast();
        }
    }

    @Override
    public void close() {
        tiemr.cancel();
    }

    @Override
    public void mouseClick(double mouseX, double mouseY, boolean isClicked) {
        if (UIRenderer.isPointInRectangleWH(mouseX, mouseY, 60, 10, 40, 40)) {
            currentDir.set(Direction.UP);
        }

        if (UIRenderer.isPointInRectangleWH(mouseX, mouseY, 10, 60, 40, 40)) {
            currentDir.set(Direction.LEFT);
        }

        if (UIRenderer.isPointInRectangleWH(mouseX, mouseY, 60, 60, 40, 40)) {
            currentDir.set(Direction.DOWN);
        }

        if (UIRenderer.isPointInRectangleWH(mouseX, mouseY, 110, 60, 40, 40)) {
            currentDir.set(Direction.RIGHT);
        }
    }

    public static class SnakePoint {
        public int x, y;
        public SnakePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof SnakePoint pont) {
                return pont.x == this.x && pont.y == this.y;
            }
            return false;
        }
    }
    public enum Direction {
        RIGHT, UP, DOWN, LEFT
    }
}
