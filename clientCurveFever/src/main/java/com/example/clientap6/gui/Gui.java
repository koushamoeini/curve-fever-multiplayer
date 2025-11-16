package com.example.clientap6.gui;

import com.example.clientap6.Curve;
import com.example.clientap6.client.TcpClientRequest;
import com.example.clientap6.objects.Body;
import com.example.clientap6.objects.Snake;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Gui {
    private final Pane pane = new Pane();
    private Timeline bodyUpdate;
    private Snake snake;
    private Stage stage = Curve.stage;
    private List<Body> bodies = new ArrayList<>();
    private List<Snake> snakes = new ArrayList<>();
    private TcpClientRequest tcpClientRequest = TcpClientRequest.getInstance();

    public Gui() throws Exception {
        String snakeCoordinate = tcpClientRequest.requestMethod("sendSnake/");
        snake = new Snake(Double.parseDouble(snakeCoordinate.split(",")[0]), Double.parseDouble(snakeCoordinate.split(",")[1]), 30);
        Timeline snakeUpdate = new Timeline(snakeUpdateKeyFrame);
        snakeUpdate.setCycleCount(Animation.INDEFINITE);
        snakeUpdate.play();
        bodyUpdate = new Timeline(bodyUpdateKeyFrame);
        bodyUpdate.setCycleCount(Animation.INDEFINITE);
        bodyUpdate.play();
        Timeline bodyActivator = new Timeline(bodyActivatorKeyFrame);
        bodyActivator.setCycleCount(Animation.INDEFINITE);
        bodyActivator.play();
        stage.setScene(new Scene(pane));
        stage.getScene().getRoot().setStyle("-fx-background-color: Black;");
        stage.getScene().setFill(Color.RED);
        stage.setX(0);
        stage.setY(0);
        stage.setWidth(1920);
        stage.setHeight(1000);
        stage.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case RIGHT -> snake.setRight(true);

                case LEFT -> snake.setLeft(true);

                case UP -> snake.setUp(true);

                case DOWN -> snake.setDown(true);
            }
        });
        stage.getScene().setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case RIGHT -> snake.setRight(false);

                case LEFT -> snake.setLeft(false);

                case UP -> snake.setUp(false);

                case DOWN -> snake.setDown(false);
            }
        });
    }

    public void setBodies(String str) {
        try {
            for (Body body : bodies) {
                body.getCircle().setVisible(false);
                body.setCircle(new Circle());
            }
            bodies.clear();
            String s = str.split("~")[0];
            for (int i = 0; i < s.split("/").length; i++) {
                Body body = new Body(Double.parseDouble(s.split("/")[i].split(",")[0]), Double.parseDouble(s.split("/")[i].split(",")[1]), 20);
                bodies.add(body);
                pane.getChildren().add(body.getCircle());

            }
        } catch (Exception ignored) {
        }
    }

    public void setSnakes(String str) {
        for (Snake snake1 : snakes) {
            snake1.getCircle().setVisible(false);
            snake1.setCircle(new Circle());
        }
        snakes.clear();
        String s = str.split("~")[1];
        for (int i = 0; i < s.split("/").length; i++) {
            Snake snake1 = new Snake(Double.parseDouble(s.split("/")[i].split(",")[0]), Double.parseDouble(s.split("/")[i].split(",")[1]), 30);
            snakes.add(snake1);
            pane.getChildren().add(snake1.getCircle());
        }
    }

    KeyFrame snakeUpdateKeyFrame = new KeyFrame(Duration.millis(10), event -> {
        try {
            if (snake.isRight()) tcpClientRequest.requestMethod("clickedRight");
            if (snake.isLeft()) tcpClientRequest.requestMethod("clickedLeft");
            if (snake.isUp()) tcpClientRequest.requestMethod("clickedUp");
            if (snake.isDown()) tcpClientRequest.requestMethod("clickedDown");
            tcpClientRequest.requestMethod("snakeUpdate");
        } catch (Exception ignored) {
        }
    });
    KeyFrame bodyUpdateKeyFrame = new KeyFrame(Duration.millis(100), event -> {
        try {
            tcpClientRequest.requestMethod("bodyUpdate");
            setBodies(tcpClientRequest.requestMethod("getData/"));
        } catch (Exception ignored) {
        }
    });
    KeyFrame bodyActivatorKeyFrame = new KeyFrame(Duration.millis(1), event -> {
        try {
            setSnakes(tcpClientRequest.requestMethod("getData/"));
            String snakeCoordinate = tcpClientRequest.requestMethod("sendSnake/");
            snake.getCircle().setCenterX(Double.parseDouble(snakeCoordinate.split(",")[0]));
            snake.getCircle().setCenterY(Double.parseDouble(snakeCoordinate.split(",")[1]));
            for (Body body : bodies) {
                if (!body.getCircle().getBoundsInParent().intersects(snake.getCircle().getBoundsInParent())) {
                    body.setActive(true);
                }
            }
            for (Body body1 : bodies) {
                if (body1.isActive() && body1.getCircle().getBoundsInParent().intersects(snake.getCircle().getBoundsInParent()) ||
                        snake.getCircle().getCenterX() > stage.getWidth() || snake.getCircle().getCenterX() < 0 ||
                        snake.getCircle().getCenterY() > stage.getHeight() || snake.getCircle().getCenterY() < 0) {
                    snake.getCircle().setVisible(false);
                    snake = null;
                    bodyUpdate.stop();
                }
            }
        } catch (Exception ignored) {
        }
    });
}


