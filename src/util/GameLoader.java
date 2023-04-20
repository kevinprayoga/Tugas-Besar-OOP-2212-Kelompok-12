package util;

import main.GamePanel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.*;

public class GameLoader {
    GamePanel gamePanel;

    public GameLoader(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
}
