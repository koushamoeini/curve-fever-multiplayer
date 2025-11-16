package com.example.clientap6.client;

import com.example.clientap6.user.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class UdpClientRequest extends Thread {
    private DatagramSocket datagramSocket= new DatagramSocket();;
    private DatagramPacket datagramPacket;
    private List<User> users=new ArrayList<>();
    private Timeline updateUserTimer;
    private static UdpClientRequest instance;
    private boolean started = false;

    public UdpClientRequest() throws SocketException {
        instance = this;
        updateUserTimer = new Timeline(updateUserKeyFrame);
        updateUserTimer.setCycleCount(Animation.INDEFINITE);
    }

    public static UdpClientRequest getInstance() throws SocketException {
        if (instance == null) {
            instance = new UdpClientRequest();
        }
        return instance;
    }
    
    @Override
    public synchronized void start() {
        if (!started) {
            started = true;
            updateUserTimer.play();
            super.start();
        }
    }
    
    public synchronized String requestMethod(String str) throws Exception {
        byte[] bytes1 = str.getBytes();
        InetAddress address=InetAddress.getLocalHost();
        datagramPacket = new DatagramPacket(bytes1, bytes1.length, address, 9999);
        datagramSocket.send(datagramPacket);
        byte[] bytes2 = new byte[50000];
        datagramPacket = new DatagramPacket(bytes2, bytes2.length);
        datagramSocket.receive(datagramPacket);
        return new String(datagramPacket.getData(), 0, datagramPacket.getLength()).trim();
    }
    KeyFrame updateUserKeyFrame = new KeyFrame(Duration.millis(100), event -> {
        users.clear();
        try {
            String usersNames= requestMethod("getUserList/");
            String[] userNamesList=usersNames.split("/");
            for(String str:userNamesList){
                User user=new User(str);
                users.add(user);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    });

    public List<User> getUsers() {
        return users;
    }
}