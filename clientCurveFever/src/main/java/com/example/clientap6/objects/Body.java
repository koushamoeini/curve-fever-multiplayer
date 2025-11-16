package com.example.clientap6.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Body {
    private Circle circle=new Circle();
    private boolean isActive=false;


    public Body(double layoutX,double layoutY,double radius) {
        circle.setFill(Color.AQUA);
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

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }

}
