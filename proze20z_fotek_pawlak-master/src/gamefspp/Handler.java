package gamefspp;



import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Handler {

    private Game game;
    public int move_counter=0;
    public DisplayParameters parameters;

    public Handler(Game game) throws IOException {
        parameters=new DisplayParameters();
        this.game=game;
    }
    public LinkedList<GameObject> object = new LinkedList<GameObject>();


    public void tick() throws IOException
    {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics g)
    {
        for(int i=0;i<object.size();i++) {
            GameObject tempObject = null;
            try {
                tempObject = this.object.get(i);
            } catch (NullPointerException e) {
                System.out.println("Pointer is null");
            }
            if (game.gameState == Game.GAMESTATE.Game) {
                g.drawString("czas:", 20, 45);
                g.drawString("liczba ruchów:", 220, 45);
                g.drawString(String.valueOf(300 - AppUtils.STOPWATCH.getTime(TimeUnit.SECONDS)), 100, 45);
                g.drawString(String.valueOf(move_counter), 440, 45);
                g.setColor(Color.white);
                Font font3 = new Font("Times New Roman", 3, 36);
                g.setFont(font3);
                tempObject.render((Graphics2D) g);
            }
            if (KeyInput.PAUSED){
                g.drawString("PAUZA", 195, 240);
            }
        }

    }

    public void addObject(GameObject object)
    {
        this.object.add(object);
    }

    public void removeObject(GameObject object)
    {
        this.object.remove(object);
    }

    public void delete_everything()
    {
        object.clear();
    }

}
