package com.codegym.games.snake;

import com.codegym.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    private int x;
    private int y;
    private List<GameObject> snakeParts = new ArrayList<>();
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public Snake(int x, int y) {
        this.x = x;
        this.y = y;

        GameObject first = new GameObject(x,y);
        GameObject second = new GameObject(x+1,y);
        GameObject third = new GameObject(x+2,y);

        snakeParts.add(first);
        snakeParts.add(second);
        snakeParts.add(third);
    }

    public void setDirection(Direction direction) {
        if (this.direction == Direction.LEFT && snakeParts.get(0).x == snakeParts.get(1).x) {
            direction = this.direction;
        } else if (this.direction == Direction.RIGHT && snakeParts.get(0).x == snakeParts.get(1).x) {
            direction = this.direction;
        } else if (this.direction == Direction.UP && snakeParts.get(0).y == snakeParts.get(1).y) {
            direction = this.direction;
        } else if (this.direction == Direction.DOWN && snakeParts.get(0).y == snakeParts.get(1).y) {
            direction = this.direction;
        } else {
            if (direction == Direction.LEFT && this.direction != Direction.RIGHT) {
                this.direction = direction;
            } else if (direction == Direction.RIGHT && this.direction != Direction.LEFT) {
                this.direction = direction;
            } else if (direction == Direction.UP && this.direction != Direction.DOWN) {
                this.direction = direction;
            } else if (direction == Direction.DOWN && this.direction != Direction.UP) {
                this.direction = direction;
            } else {
                direction = this.direction;
            }
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void draw(Game game) {
        if (isAlive) {
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.DARKGREEN, 75);
            game.setCellValueEx(snakeParts.get(1).x, snakeParts.get(1).y, Color.NONE, BODY_SIGN, Color.DARKGREEN, 75);
            game.setCellValueEx(snakeParts.get(2).x, snakeParts.get(2).y, Color.NONE, BODY_SIGN, Color.DARKGREEN, 75);
        } else {
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.RED, 75);
            game.setCellValueEx(snakeParts.get(1).x, snakeParts.get(1).y, Color.NONE, BODY_SIGN, Color.RED, 75);
            game.setCellValueEx(snakeParts.get(2).x, snakeParts.get(2).y, Color.NONE, BODY_SIGN, Color.RED, 75);
        }
    }

    public GameObject createNewHead(){
        int headX = snakeParts.get(0).x;
        int headY = snakeParts.get(0).y;
        if (direction == Direction.LEFT) {
            return new GameObject(headX-1,headY);
        } else if (direction == Direction.RIGHT) {
            return new GameObject(headX+1,headY);
        } else if (direction == Direction.UP) {
            return new GameObject(headX,headY-1);
        } else {
            return new GameObject(headX,headY+1);
        }
    }

    public void removeTail(){
        snakeParts.remove(snakeParts.size()-1);
    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();

        boolean collision = checkCollision(newHead);

        if (newHead.x < 0 || newHead.x >= SnakeGame.WIDTH || newHead.y < 0 || newHead.y >= SnakeGame.HEIGHT) {
            isAlive = false;
        } else if (collision) {
            this.isAlive = false;
        } else {
            snakeParts.add(0,newHead);
            if (apple.x == newHead.x && apple.y == newHead.y) {
                apple.isAlive = false;
            } else {
                apple.isAlive = true;
                removeTail();
            }
        }
        }

    public boolean checkCollision(GameObject obj) {
        for (int i = 0; i < snakeParts.size(); i++) {
            int tempX = snakeParts.get(i).x;
            for (int j =0; j <snakeParts.size(); j++) {
                int tempY = snakeParts.get(j).y;

                if (obj.x == tempX && obj.y == tempY) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getLength(){
        return snakeParts.size();
    }


}
