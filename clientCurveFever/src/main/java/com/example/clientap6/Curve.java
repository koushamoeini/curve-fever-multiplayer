package com.example.clientap6;

import com.example.clientap6.user.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Curve extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        Curve.stage =stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Curve.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("curve");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}