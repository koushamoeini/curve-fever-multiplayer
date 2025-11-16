package com.example.clientap6.controllers;

import com.example.clientap6.client.TcpClientRequest;
import com.example.clientap6.client.UdpClientRequest;
import com.example.clientap6.gui.Gui;
import com.example.clientap6.user.CurrentUser;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {
    private UdpClientRequest udpClientRequest = UdpClientRequest.getInstance();
    private TcpClientRequest tcpClientRequest = TcpClientRequest.getInstance();
    private CurrentUser currentUser = CurrentUser.getInstance();
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public ScrollPane invitePane;

    private Timeline updateScrollPane;
    private Timeline inviteUpdate;
    private List<String> inviteName = new ArrayList<>();
    private GridPane grid = new GridPane();

    public LobbyController() throws IOException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        KeyFrame updateScrollPaneKeyFrame = new KeyFrame(Duration.seconds(1), event -> {
            GridPane grid = new GridPane();
            grid.setPadding(new Insets(udpClientRequest.getUsers().size()));
            grid.setHgap(udpClientRequest.getUsers().size());
            grid.setVgap(udpClientRequest.getUsers().size());
            for (int i = 0; i < udpClientRequest.getUsers().size(); i++) {
                if (!udpClientRequest.getUsers().get(i).getName().equals(currentUser.getUser().getName())) {
                    Button button = new Button(udpClientRequest.getUsers().get(i).getName());
                    button.setOnAction(e -> {
                        try {
                            udpClientRequest.requestMethod("invite/" + button.getText() + "/" + currentUser.getUser().getName());
                        } catch (Exception ignored) {
                        }
                    });
                    grid.add(button, 1, i);
                }
            }
            scrollPane.setContent(grid);
        });
        KeyFrame invteUpdateKeyFrame = new KeyFrame(Duration.millis(3), event -> {
            grid.setPadding(new Insets(udpClientRequest.getUsers().size()));
            grid.setHgap(udpClientRequest.getUsers().size());
            grid.setVgap(udpClientRequest.getUsers().size());
            try {
                if (udpClientRequest.requestMethod("getInvite/").split("/")[1].equals("invited you")) {
                    Button button = new Button(udpClientRequest.requestMethod("getInvite/").split("/")[0] + " " + udpClientRequest.requestMethod("getInvite/").split("/")[1]);
                    button.setOnAction(e -> {
                        try {
                            udpClientRequest.requestMethod("startGame/" + currentUser.getUser().getName() + "/" + button.getText().split(" ")[0]);
                            tcpClientRequest.requestMethod("startGame/" + currentUser.getUser().getName());
                            new Gui();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }

                    });
                    if (!isInList(button.getText())) {
                        inviteName.add(button.getText());
                        grid.add(button, 0, inviteName.size());
                    }
                    invitePane.setContent(grid);
                }

            } catch (Exception ignored) {
            }
            try {
                if (udpClientRequest.requestMethod("acceptReq/" + currentUser.getUser().getName()).equals("true")) {
                    tcpClientRequest.requestMethod("startGame/" + currentUser.getUser().getName());
                    new Gui();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        updateScrollPane = new Timeline(updateScrollPaneKeyFrame);
        updateScrollPane.setCycleCount(Animation.INDEFINITE);
        updateScrollPane.play();
        inviteUpdate = new Timeline(invteUpdateKeyFrame);
        inviteUpdate.setCycleCount(Animation.INDEFINITE);
        inviteUpdate.play();
    }

    public boolean isInList(String str) {
        for (String name : inviteName) {
            if (name.equals(str)) return true;
        }
        return false;
    }
}

