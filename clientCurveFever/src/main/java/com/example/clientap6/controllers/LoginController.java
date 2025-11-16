package com.example.clientap6.controllers;

import com.example.clientap6.Curve;
import com.example.clientap6.client.UdpClientRequest;
import com.example.clientap6.user.CurrentUser;
import com.example.clientap6.user.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController {
    @FXML
    TextField userField;
    private Stage stage=Curve.stage;
    CurrentUser currentUser=CurrentUser.getInstance();

    @FXML
    protected void onConfirmButtonClick() throws Exception {
        User user = new User(userField.getText());
        UdpClientRequest udpClientRequest=new UdpClientRequest();
        udpClientRequest.requestMethod("login/"+user.getName());
        currentUser.setUser(user);
        FXMLLoader fxmlLoader = new FXMLLoader(Curve.class.getResource("lobby.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setX(500);
        stage.setY(100);
        stage.setScene(scene);
    }
}