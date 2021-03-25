package gamefspp;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas{

    public Window(int width, int height, String name, Game game) {

        JFrame frame = new JFrame(name); //tworzymy okno
        frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//zamknięcie
        frame.setLocationRelativeTo(null);//okno na środku
        frame.setResizable(true);//zmiana wielkości okna
        frame.setVisible(true);
        frame.add(game);//dodajemy naszą gre do okna
        game.start();
    }
    
}
