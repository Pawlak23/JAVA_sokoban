package gamefspp;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GameConfig {

    FileReader reader;
    Properties p;
    int width;
    int height;
    int level_number;


    public GameConfig(String path) throws IOException {
        reader = new FileReader(path);
        p = new Properties();
        p.load(reader);
        level_number = Integer.parseInt(p.getProperty("Level_number"));
        width = Integer.parseInt(p.getProperty("Width"));
        height = Integer.parseInt(p.getProperty("Height"));
    }

    public int getLevel_number() {

        return level_number;
    }


    public int getWidth() {

        return width;
    }


    public int getHeight() {

        return height;
    }

}
