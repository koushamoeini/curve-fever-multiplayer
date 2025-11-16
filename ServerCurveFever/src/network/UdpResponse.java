package network;

import gameData.GameData;
import gameData.GameDataList;
import userData.User;
import userData.UserPort;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class UdpResponse extends Thread {
    private byte[] bytes;
    private DatagramPacket datagramPacket;
    private DatagramSocket datagramSocket = new DatagramSocket(9999);
    private List<UserPort> userPorts = new ArrayList<>();
    private String inviteText;
    private int invitePort;
    private String acceptedUser = " / ";
    private GameDataList gameDataList=GameDataList.getInstance();

    public UdpResponse() throws SocketException {
    }

    @Override
    public void run() {
        while (true) {
            try {
                bytes = new byte[50000];
                datagramPacket = new DatagramPacket(bytes, bytes.length);
                datagramSocket.receive(datagramPacket);
                InetAddress address = datagramPacket.getAddress();
                datagramPacket = new DatagramPacket(bytes, bytes.length, address, datagramPacket.getPort());
                String received = new String(datagramPacket.getData(), 0, datagramPacket.getLength()).trim();
                if (received.startsWith("login")) {
                    UserPort userPort = new UserPort(new User(received.split("/")[1]), datagramPacket.getPort());
                    userPorts.add(userPort);
                    datagramSocket.send(datagramPacket);
                } else if (received.startsWith("getUserList")) {
                    String response = sendClientList();
                    sendPacket(response, address, datagramPacket.getPort());
                } else if (received.startsWith("getInvite")) {
                    if (invitePort == datagramPacket.getPort())
                        sendPacket(inviteText, address, invitePort);
                    else
                        datagramSocket.send(datagramPacket);
                } else if (received.startsWith("invite")) {
                    inviteReq(received.split("/")[1], received.split("/")[2]);
                    datagramSocket.send(datagramPacket);
                } else if (received.startsWith("acceptReq/")) {
                    acceptReq(received);
                } else if (received.startsWith("startGame/")) {
                    startGame(received);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void acceptReq(String received) throws IOException {
        String userName = received.split("/")[1];
        if (userName.equals(acceptedUser.split("/")[0])) {
            sendPacket("true", datagramPacket.getAddress(), datagramPacket.getPort());
            acceptedUser = " / ";
        } else  datagramSocket.send(datagramPacket);
    }

    public void startGame(String received) throws IOException {
        String[] strings = received.split("/");
        acceptedUser = strings[2] + "/" + strings[1];
        datagramSocket.send(datagramPacket);
        gameDataList.getGameDataList().add(new GameData(new User(strings[1]),new User(strings[2])));
    }

    public String sendClientList() {
        String response = "";
        for (UserPort userPort : userPorts) {
            String answer = userPort.getUser().getUserName() + "/";
            response += answer;
        }
        return response;
    }

    public void inviteReq(String username, String host) {
        for (UserPort userPort : userPorts) {
            if (userPort.getUser().getUserName().equals(username)) {
                invitePort = userPort.getPort();
                inviteText = host + "/invited you";
            }
        }
    }

    public void sendPacket(String response, InetAddress clientAddress, int clientPort) throws IOException {
        byte[] sendData = response.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
        datagramSocket.send(sendPacket);
    }
}
