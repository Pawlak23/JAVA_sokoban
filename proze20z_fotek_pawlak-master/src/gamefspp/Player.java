package gamefspp;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends GameObject{

   
    private Game game;


    public Player(int x, int y, ID id, BufferedImage image, Game game){
        super(x,y,id,image);
        this.game=game;


    }
    public void tick() throws IOException {
        x+=Xspeed;
        y+=Yspeed;
    }

    public void render(Graphics2D g) {
        g.drawImage(image, x, y, null);
    }

    public void move(int x, int y) {
        if (KeyInput.PAUSED){
            return;
        }
        int dx = getX() + x;
        int dy = getY() + y;

        setX(dx);
        setY(dy);
    }
}
