package gameData;

import java.util.ArrayList;
import java.util.List;

public class GameDataList {
    List<GameData> gameDataList=new ArrayList<>();
    private static GameDataList instance;
    public static GameDataList getInstance() {
        if (instance == null)
            instance = new GameDataList();
        return instance;
    }
    public GameDataList() {
        instance = this;
    }

    public List<GameData> getGameDataList() {
        return gameDataList;
    }
}
