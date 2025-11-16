package com.example.clientap6.objects;

import com.example.clientap6.client.TcpClientRequest;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.util.List;

public class Snake {
    private boolean isRight = false;
    private boolean isLeft = false;
    private boolean isUp = false;
    private boolean isDown = false;
    private Circle circle = new Circle();

    public Snake(double layoutX, double layoutY, double radius) {
        circle.setFill(Color.RED);
        circle.setRadius(radius);
        circle.setCenterY(layoutY);
        circle.setCenterX(layoutX);
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        isDown = down;
    }

}
