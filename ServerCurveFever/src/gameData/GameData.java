package gameData;

import objects.Coordinate;
import objects.Prize;
import objects.Snake;
import userData.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

public class GameData{
    private User user1;
    private User user2;
    private List<Coordinate> bodies=new ArrayList<>();
    private HashMap<String, Snake> snakes=new HashMap<>();
    private List<Prize> prizes=new ArrayList<>();

    public GameData(User user1,User user2) {
        this.user1=user1;
        this.user2=user2;
    }
    public String toString(){
        String response=listToString(bodies);
        response+="~"+snakes.get(user1.getUserName()).getCoordinate().getX()+","+snakes.get(user1.getUserName()).getCoordinate().getY()+"/"+snakes.get(user2.getUserName()).getCoordinate().getX()+","+snakes.get(user2.getUserName()).getCoordinate().getY();
        return response;
    }
    public String listToString(List<Coordinate> list) {
        String response = "";
        for (Coordinate coordinate : list) {
            String answer = coordinate.getX()+","+coordinate.getY()+"/";
            response += answer;
        }
        return response;
    }
    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public List<Coordinate> getBodies() {
        return bodies;
    }

    public void setBodies(List<Coordinate> bodies) {
        this.bodies = bodies;
    }

    public HashMap<String, Snake> getSnakes() {
        return snakes;
    }
}
