package network;

import objects.Coordinate;
import gameData.GameData;
import gameData.GameDataList;
import objects.Snake;

import java.io.*;
import java.net.Socket;

public class TcpLogic extends Thread {
    private Socket socket;
    private InputStreamReader inputStream;
    private OutputStreamWriter outputStream;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private GameData gameData;
    private String currentUser;

    public TcpLogic(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                inputStream = new InputStreamReader(socket.getInputStream());
                outputStream = new OutputStreamWriter(socket.getOutputStream());
                bufferedReader = new BufferedReader(inputStream);
                bufferedWriter = new BufferedWriter(outputStream);
            } catch (IOException e) {
            }
            try {
                String received = bufferedReader.readLine();
                if (received.startsWith("startGame/")) {
                    startGame(received);
                } else if (received.startsWith("getData/")) {
                    try {
                        sendData();
                    } catch (Exception e) {
                        sentMsg(" ");
                    }
                } else if (received.startsWith("sendSnake/")) {
                    sentMsg(gameData.getSnakes().get(currentUser).getCoordinate().getX() + "," + gameData.getSnakes().get(currentUser).getCoordinate().getY());
                } else if (received.startsWith("clickedRight")) {
                    clickedOnRight();
                    sentMsg(" ");
                } else if (received.startsWith("clickedLeft")) {
                    clickedOnLeft();
                    sentMsg(" ");
                } else if (received.startsWith("clickedUp")) {
                    clickedOnUp();
                    sentMsg(" ");
                } else if (received.startsWith("clickedDown")) {
                    clickedOnDown();
                    sentMsg(" ");
                } else if (received.startsWith("bodyUpdate")) {
                    bodyUpdate();
                    sentMsg(" ");
                } else if (received.startsWith("snakeUpdate")) {
                    snakeUpdate();
                    sentMsg(" ");
                }  else sentMsg(" ");
            } catch (IOException ignored) {
                try {
                    sentMsg(" ");
                } catch (IOException e) {
                }
            }
        }
    }
    public void bodyUpdate() {
        gameData.getBodies().add(gameData.getSnakes().get(currentUser).getCoordinate());
    }

    public void snakeUpdate() throws IOException {
        gameData.getSnakes().get(currentUser).setCoordinate(new Coordinate(gameData.getSnakes().get(currentUser).getCoordinate().getX() + gameData.getSnakes().get(currentUser).getxVelocity(), gameData.getSnakes().get(currentUser).getCoordinate().getY() - gameData.getSnakes().get(currentUser).getyVelocity()));

    }

    public void clickedOnRight() {
        double num = gameData.getSnakes().get(currentUser).getVelocity() / 100;
        if (Math.abs(gameData.getSnakes().get(currentUser).getxVelocity()) <= gameData.getSnakes().get(currentUser).getVelocity() - num) {
            gameData.getSnakes().get(currentUser).setxVelocity(gameData.getSnakes().get(currentUser).getxVelocity() + num);
            if (gameData.getSnakes().get(currentUser).getyVelocity() > 0)
                gameData.getSnakes().get(currentUser).setyVelocity(Math.sqrt(Math.pow(gameData.getSnakes().get(currentUser).getVelocity(), 2) - Math.pow(gameData.getSnakes().get(currentUser).getxVelocity(), 2)));
            else
                gameData.getSnakes().get(currentUser).setyVelocity(-Math.sqrt(Math.pow(gameData.getSnakes().get(currentUser).getVelocity(), 2) - Math.pow(gameData.getSnakes().get(currentUser).getxVelocity(), 2)));

        }
    }

    public void clickedOnLeft() {
        double num = gameData.getSnakes().get(currentUser).getVelocity() / 100;
        if (Math.abs(gameData.getSnakes().get(currentUser).getxVelocity()) <= gameData.getSnakes().get(currentUser).getVelocity() - num)
            gameData.getSnakes().get(currentUser).setxVelocity(gameData.getSnakes().get(currentUser).getxVelocity() - num);
        if (gameData.getSnakes().get(currentUser).getyVelocity() > 0)
            gameData.getSnakes().get(currentUser).setyVelocity(Math.sqrt(Math.pow(gameData.getSnakes().get(currentUser).getVelocity(), 2) - Math.pow(gameData.getSnakes().get(currentUser).getxVelocity(), 2)));
        else
            gameData.getSnakes().get(currentUser).setyVelocity(-Math.sqrt(Math.pow(gameData.getSnakes().get(currentUser).getVelocity(), 2) - Math.pow(gameData.getSnakes().get(currentUser).getxVelocity(), 2)));
    }

    public void clickedOnUp() {
        double num = gameData.getSnakes().get(currentUser).getVelocity() / 100;
        if (Math.abs(gameData.getSnakes().get(currentUser).getyVelocity()) <= gameData.getSnakes().get(currentUser).getVelocity() - num)
            gameData.getSnakes().get(currentUser).setyVelocity(gameData.getSnakes().get(currentUser).getyVelocity() + num);

        if (gameData.getSnakes().get(currentUser).getxVelocity() > 0)
            gameData.getSnakes().get(currentUser).setxVelocity(Math.sqrt(Math.pow(gameData.getSnakes().get(currentUser).getVelocity(), 2) - Math.pow(gameData.getSnakes().get(currentUser).getyVelocity(), 2)));
        else
            gameData.getSnakes().get(currentUser).setxVelocity(-Math.sqrt(Math.pow(gameData.getSnakes().get(currentUser).getVelocity(), 2) - Math.pow(gameData.getSnakes().get(currentUser).getyVelocity(), 2)));
    }

    public void clickedOnDown() {
        double num = gameData.getSnakes().get(currentUser).getVelocity() / 100;
        if (Math.abs(gameData.getSnakes().get(currentUser).getyVelocity()) <= gameData.getSnakes().get(currentUser).getVelocity() - num)
            gameData.getSnakes().get(currentUser).setyVelocity(gameData.getSnakes().get(currentUser).getyVelocity() - num);
        if (gameData.getSnakes().get(currentUser).getxVelocity() > 0)
            gameData.getSnakes().get(currentUser).setxVelocity(Math.sqrt(Math.pow(gameData.getSnakes().get(currentUser).getVelocity(), 2) - Math.pow(gameData.getSnakes().get(currentUser).getyVelocity(), 2)));
        else
            gameData.getSnakes().get(currentUser).setxVelocity(-Math.sqrt(Math.pow(gameData.getSnakes().get(currentUser).getVelocity(), 2) - Math.pow(gameData.getSnakes().get(currentUser).getyVelocity(), 2)));
    }

    public void startGame(String received) throws IOException {
        String name = received.split("/")[1];
        currentUser = name;
        GameDataList gameDataList = GameDataList.getInstance();
        for (GameData gameData1 : gameDataList.getGameDataList()) {
            if (gameData1.getUser1().getUserName().equals(name)) {
                gameData = gameData1;
                gameData.getSnakes().put(currentUser, new Snake(new Coordinate(100, 500)));
                //sentMsg("");
                return;
            } else if (gameData1.getUser2().getUserName().equals(name)) {
                gameData = gameData1;
                gameData.getSnakes().put(currentUser, new Snake(new Coordinate(1600, 500)));
                gameData.getSnakes().get(currentUser).setxVelocity(-1);
                //sentMsg("");
                return;
            }
        }
    }

    public void sendData() throws IOException {
        sentMsg(gameData.toString());
    }

    public void sentMsg(String str) throws IOException {
        bufferedWriter.write(str);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
}
