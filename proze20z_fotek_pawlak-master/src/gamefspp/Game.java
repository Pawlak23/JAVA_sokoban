package gamefspp;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static gamefspp.AppUtils.STOPWATCH;
import static gamefspp.AppUtils.TIME_LIST;


public class Game extends Canvas implements Runnable {
    public int WIDTH;
    public int HEIGHT;
    public Thread thread;
    public GAMESTATE gameState = GAMESTATE.Menu;
    private boolean running = false;
    private Handler handler;
    private Menu menu;
    private int level_number = 1;// wybieranie poziomu
    private Player player_object;
    private double x_scale = 0;
    private double y_scale = 0;
    private BufferedImage wall;
    private BufferedImage field;
    private BufferedImage chest;
    private BufferedImage chestField;
    private BufferedImage goldenChest;
    private BufferedImage goldenChestField;
    private BufferedImage player;
    private int[][] look_of_map;
    private LevelConfig levelconfig;
    private String path;
    private int LevelNumber;
    private int counter=0;//ilosc skrzynek miejsc wczytanych z pliku
    private int counter2=0;//ilosc skrzynek podczas sprawdzania ułozenia
    public int score=500;    //punkty za przejscie poziomy i startowe 500punktow
    public int score2=0; //punkty za ukladanie skrzynek

    public enum GAMESTATE {
        Menu,
        BestResults,
        HowToPlay,
        Game,
        GameEndWin,
        GameEndLose,
    }

    public Game() throws IOException {

        handler = new Handler(this);
        menu = new Menu(this, handler);
        this.addMouseListener(menu);
        GameConfig gameconfig = new GameConfig("resorces/GameConfig.properties");
        WIDTH = gameconfig.getWidth();
        HEIGHT = gameconfig.getHeight();

        new Window(WIDTH, HEIGHT, "SOKOBAN", this);


        ImageConfig picture = new ImageConfig();
        wall = picture.loadPicture("pictures/wall.png");//0
        field = picture.loadPicture("pictures/field.png");//1
        chest = picture.loadPicture("pictures/chest.png");//2
        chestField = picture.loadPicture("pictures/chestField.png");//3
        goldenChest = picture.loadPicture("pictures/goldenChest.png");//4
        goldenChestField = picture.loadPicture("pictures/goldenChestField.png");//5
        player = picture.loadPicture("pictures/player.png");


        path = "resorces/LevelConfig" + level_number + ".properties";
        levelconfig = new LevelConfig(path);
        look_of_map = levelconfig.getFull_map_int();
        gameconfig = new GameConfig("resorces/GameConfig.properties");
        LevelNumber = gameconfig.getLevel_number();//liczba poziomów z pliku konfiguracyjnego
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (look_of_map[j][i] == 0) {
                    handler.addObject(new Wall(i * 50, j * 50, ID.Wall, wall));
                } else {
                    handler.addObject(new Field(i * 50, j * 50, ID.Field, field));
                }
                if (look_of_map[j][i] == 3) {
                    handler.addObject(new ChestField(i * 50, j * 50, ID.ChestField, chestField));
                    counter++;
                }
                if (look_of_map[j][i] == 5) {
                    handler.addObject(new GoldenChestField(i * 50, j * 50, ID.GoldenChestField, goldenChestField));
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (look_of_map[j][i] == 2) {
                    handler.addObject(new Chest(i * 50, j * 50, ID.Chest, chest));
                }


                if (look_of_map[j][i] == 4) {
                    handler.addObject(new GoldenChest(i * 50, j * 50, ID.GoldenChest, goldenChest));
                }
            }
        }
        player_object = new Player(150, 250, ID.Player, player, this);
        handler.addObject(player_object);
        this.addKeyListener(new KeyInput(handler, this));
    }

    public void get_New_Scale() {
        set_x_scale((double) getWidth() / WIDTH);
        set_y_scale((double) getHeight() / HEIGHT);
    }

    public double get_x_scale() {
        return x_scale;
    }

    public void set_x_scale(double x_scale) {
        this.x_scale = x_scale;
    }

    public double get_y_scale() {
        return y_scale;
    }

    public void set_y_scale(double y_scale) {
        this.y_scale = y_scale;
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                try {
                    tick();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                delta -= 1;
            }
            try {
                thread.sleep(2);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (running)
                render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }
        }
        stop();
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();

    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadNewLevel() throws IOException {
        String path = "resorces/LevelConfig" + level_number + ".properties";
        LevelConfig levelconfig = new LevelConfig(path);
        int[][] look_of_map = levelconfig.getFull_map_int();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (look_of_map[j][i] == 0) {
                    handler.addObject(new Wall(i * 50, j * 50, ID.Wall, wall));
                } else {
                    handler.addObject(new Field(i * 50, j * 50, ID.Field, field));
                }
                if (look_of_map[j][i] == 3) {
                    handler.addObject(new ChestField(i * 50, j * 50, ID.ChestField, chestField));
                    counter++;
                }
                if (look_of_map[j][i] == 5) {
                    handler.addObject(new GoldenChestField(i * 50, j * 50, ID.GoldenChestField, goldenChestField));
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (look_of_map[j][i] == 2) {
                    handler.addObject(new Chest(i * 50, j * 50, ID.Chest, chest));
                }


                if (look_of_map[j][i] == 4) {
                    handler.addObject(new GoldenChest(i * 50, j * 50, ID.GoldenChest, goldenChest));
                }
            }
        }

        if (level_number == 2) {
            player_object = new Player(150, 250, ID.Player, player, this);
            handler.addObject(player_object);
        }
        if (level_number == 3) {
            player_object = new Player(250, 200, ID.Player, player, this);
            handler.addObject(player_object);
        }
    }

    private void tick() throws IOException {
        get_New_Scale(); //za każdym razem patrzymy jaki jest rozmiar okna żeby potem przeskalować wszyskie rzeczy (pobieramy obecny x_scale,y_scale)

        if (gameState == GAMESTATE.Menu || gameState == GAMESTATE.BestResults || gameState == GAMESTATE.HowToPlay) {
            menu.tick();
        }
        if (gameState == GAMESTATE.Game) {
            handler.tick();
            GameObject tempObject2 = null;
            GameObject tempObject3 = null;
            score2=0;
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getId() == ID.GoldenChestField) {
                    tempObject2 = handler.object.get(i);//jeden goldenchestfield
                }
                if (tempObject.getId() == ID.GoldenChest) {
                    tempObject3 = handler.object.get(i);//jeden goldenchest
                }
            }
            if (tempObject2.getX() == tempObject3.getX() && tempObject2.getY() == tempObject3.getY()) {
                score2+=150; //punkty za ułożenie złotej skrzynki
            }


            //normalne przechodzenio poziomu
            counter2=0;
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject4 = handler.object.get(i);
                if (tempObject4.getId() == ID.ChestField||tempObject4.getId() == ID.GoldenChestField) {
                    for (int j = 0; j < handler.object.size(); j++) {
                        GameObject tempObject5=handler.object.get(j);
                        if (tempObject5.getId() == ID.Chest||tempObject5.getId() == ID.GoldenChest) {
                            if (tempObject5.getX() == tempObject4.getX() && tempObject5.getY() == tempObject4.getY()){
                                counter2++;
                            }
                        }

                    }
                }
                if(counter2==counter+1){
                    deleteLevel();
                    counter=0;
                    level_number += 1;
                    score=score+score2+500;
                    if(level_number<=LevelNumber) {
                        loadNewLevel();
                        AppUtils.reloadTimer();
                        System.out.println(TIME_LIST);
                    }
                    else {
                        gameState=GAMESTATE.GameEndWin;
                        AppUtils.reloadTimer();
                        System.out.println(TIME_LIST);
                    }
                }
            }

            //szybkie przechodzenie do następnego lvl
            /*for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getId() == ID.Player && tempObject.getX() == 400 && tempObject.getY() == 400) {
                    deleteLevel();
                    level_number += 1;
                    score=score+score2+500; //za przejscie poziomu 300pkt

                    if (level_number <= LevelNumber) {
                        AppUtils.reloadTimer();
                        loadNewLevel();
                        System.out.println(TIME_LIST);
                    } else {
                        gameState = GAMESTATE.GameEndWin;
                        AppUtils.reloadTimer();
                        System.out.println(TIME_LIST);
                        System.out.println(handler.parameters.getPlayerName());
                    }
                }
            }*/
        }

        if (STOPWATCH.getTime(TimeUnit.SECONDS)>=300){
            gameState= GAMESTATE.GameEndLose;
        }
        if (!STOPWATCH.isStarted()&& gameState==GAMESTATE.Game) {
            STOPWATCH.start();
        }
    }

    private void render() {
        get_New_Scale();
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.gray);
        g2.fillRect(0, 0, (int) (WIDTH * get_x_scale()), (int) (HEIGHT * get_y_scale()));
        //System.out.println(counter2);
        //System.out.println(score);
        //System.out.println(gameState);
        //System.out.println(handler.move_counter);

        if (gameState == GAMESTATE.Menu || gameState == GAMESTATE.BestResults || gameState == GAMESTATE.HowToPlay  || gameState == GAMESTATE.GameEndWin || gameState == GAMESTATE.GameEndLose) {
            g2.scale(get_x_scale(), get_y_scale());
            menu.render(g2);
        }
        if (gameState == GAMESTATE.Game) {
            g2.scale(get_x_scale(), get_y_scale());
            handler.render(g2); //rysowanie planszy
        }
        g.dispose();
        bs.show();
    }

    public void deleteLevel() {

        for (int i = handler.object.size() - 1; i >= 0; i--) {
            GameObject tempObject = handler.object.get(i);
            handler.removeObject(tempObject);
        }
    }
}



