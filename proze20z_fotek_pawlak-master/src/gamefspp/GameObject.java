package gamefspp;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class GameObject {
protected int x;
protected int y;
protected ID id;
protected int Xspeed;
protected int Yspeed;
protected BufferedImage image;

    public GameObject(int x, int y, ID id, BufferedImage image)
    {
        this.x=x;
        this.y=y;
        this.id=id;
        this.image=image;
    }



    public abstract void tick() throws IOException;
    public abstract void render(Graphics2D g);

    /*getery i setery*/
    public int getX()
    {
        return x;
    }
    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }
    public void setY(int y)
    {
        this.y = y;
    }

    public ID getId()
    {
        return id;
    }
    public void setId(ID id)
    {
        this.id = id;
    }

    public int getXspeed() {
        return Xspeed;
    }
    public void setXspeed(int Xspeed)
    {
        this.Xspeed = Xspeed;
    }

    public int getYspeed() {
        return Yspeed;
    }
    public void setYspeed(int Yspeed)
    {
        this.Yspeed = Yspeed;
    }

    public void move(int x, int y) {

        int dx = getX() + x;
        int dy = getY() + y;

        setX(dx);
        setY(dy);
    }

    public boolean LeftCollision(GameObject gameObject) {

        return getX()-50  == gameObject.getX() && getY() == gameObject.getY();
    }

    public boolean RightCollision(GameObject gameObject) {

        return getX() + 50 == gameObject.getX() && getY() == gameObject.getY();
    }

    public boolean TopCollision(GameObject gameObject) {

        return getY() - 50 == gameObject.getY() && getX() == gameObject.getX();
    }
    public boolean BottomCollision(GameObject gameObject) {

        return getY() + 50 == gameObject.getY() && getX() == gameObject.getX();
    }
}
