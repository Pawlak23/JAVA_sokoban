package gamefspp;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LevelConfig {
    FileReader reader;
    String level_name;
    int map_width;
    int map_height;
    int[][] full_map_int;
    Properties p;



    public LevelConfig(String path) throws IOException {
        reader = new FileReader(path);
        p = new Properties();
        p.load(reader);
        String map_state;
        level_name = p.getProperty("Level_name");
        map_width = Integer.parseInt(p.getProperty("Map_width"));
        map_height = Integer.parseInt(p.getProperty("Map_height"));
        int[][] full_map_int_temp = new int[map_width][map_height];
        map_state = p.getProperty("Map_state");
        String  [][] full_map_string = new String[map_width][map_height];
        String[] map_split = map_state.split(";");
        for(int i=0;i<map_split.length;i++) {

            full_map_string[i]=map_split[i].split(",");
            for(int j=0;j<full_map_string[i].length;j++) {
                full_map_int_temp[i][j]=Integer.parseInt(full_map_string[i][j]);

            }

        }
        full_map_int=full_map_int_temp;
    }

    //wypisywanie tablicy
    public void printFull_map_int() {
        for (int i = 0; i < map_height; i++) {
            for (int j = 0; j < map_width; j++) {
                System.out.print(full_map_int[i][j]);
            }
            System.out.println();
        }
    }
        public int[][]getFull_map_int(){
            return full_map_int;
        }



    public String getLevel_name() {

        return level_name;
    }


    public int getMap_width() {

        return map_width;
    }


    public int getMap_height() {

        return map_height;
    }

}
