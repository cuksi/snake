package com.codegym.games.snake;
import com.codegym.engine.cell.*;


public class SnakeGame extends Game {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private static final int GOAL = 28;
    private int score;

    @Override
    public void initialize() {
        super.initialize();
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame() {
        snake = new Snake(WIDTH/2,HEIGHT/2);
        snake.draw(this);

        turnDelay = 300;
        setTurnTimer(turnDelay);

        score = 0;
        setScore(score);


        createNewApple();

        isGameStopped = false;


        drawScene();


    }

    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.CORAL,"");
            }
        }

        snake.draw(this);

        apple.draw(this);

    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);

        if (!apple.isAlive) {
            score += 5;
            setScore(score);
            turnDelay -=10;
            setTurnTimer(turnDelay);

            createNewApple();
        }

        if (!snake.isAlive) {
            gameOver();
        }

        if (snake.getLength() > GOAL) {
            win();
        }

        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {

            if (key == Key.LEFT ) {
                snake.setDirection(Direction.LEFT);
             } else if (key == Key.RIGHT) {
                snake.setDirection(Direction.RIGHT);
            } else if (key == Key.UP) {
                snake.setDirection(Direction.UP);
            }else if (key == Key.DOWN) {
                snake.setDirection(Direction.DOWN);
            }

            if (key == Key.SPACE && isGameStopped) {
                createGame();
            }
    }

    private void createNewApple() {

        boolean appleCollision = true;


        while (appleCollision) {
            int width = getRandomNumber(WIDTH);
            int height = getRandomNumber(HEIGHT);

            Apple newApple = new Apple(width,height);

            boolean tempCollisionCheck = snake.checkCollision(newApple);

            if (!tempCollisionCheck) {
                this.apple = newApple;
                appleCollision = false;
            }

        }
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.BLACK,"GAME OVER",Color.LIGHTCYAN,100);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.PURPLE,"YOU WON",Color.DARKMAGENTA,100);
    }

    public static void main(String[] args) {


    }

}