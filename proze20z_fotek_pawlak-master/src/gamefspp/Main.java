package gamefspp;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        //LevelConfig levelconfig = new LevelConfig("resorces/LevelConfig1.properties");
       // System.out.println(levelconfig.getLevel_name());
        //System.out.println(levelconfig.getMap_height());
        //System.out.println(levelconfig.getMap_width());
        //levelconfig.printFull_map_int();
        GameConfig gameconfig=new GameConfig("resorces/GameConfig.properties");
        System.out.println(gameconfig.getWidth());
        System.out.println(gameconfig.getHeight());
        System.out.println(gameconfig.getLevel_number());
        new Game();
    }
}
