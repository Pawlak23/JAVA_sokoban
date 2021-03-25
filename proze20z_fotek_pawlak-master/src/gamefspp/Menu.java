package gamefspp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



import gamefspp.Game.GAMESTATE;

import javax.swing.*;

import static gamefspp.AppUtils.STOPWATCH;


public class Menu extends MouseAdapter implements ActionListener {

    Game game;
    Handler handler;

    JLabel l;
    JTextField tf;
    JFrame frame1;
    JButton Play, Back;
    DisplayParameters parameters;
    private int final_score; //suma punktów za gre
    private String player_name;//nazwa gracza
    public Menu(Game game, Handler handler)
    {
        this.game=game;
        this.handler=handler;

    }

    private boolean mousePosition(int posX, int posY, int x, int y, int width, int height){
        if(posX>x && posX< x+ width){
            if(posY>y && posY< y+ height){
                return true;
            }
            else return false;
        }
        else return false;
    }

    public void tick() {

    }

    public void render(Graphics2D g) {
        Font font = new Font("Times New Roman", 1, 40);
        Font font2 = new Font("Times New Roman", 2, 25);
        Font font3 = new Font("Times New Roman", 1, 30);
        Font font4 = new Font("Times New Roman", 2, 20);
        if (game.gameState == GAMESTATE.Menu) {
            g.setFont(font);
            g.setColor(Color.black);
            g.drawString("Witaj w grze!", 130, 60);
            g.setFont(font2);

            //GRAJ!
            g.setColor(Color.white);
            g.fillRect(100, 110, 300, 50);
            g.setColor(Color.black);
            g.drawRect(100, 110, 300, 50);
            g.drawString("GRAJ", 220, 145);

            //NAJLEPSZE WYNIKI
            g.setColor(Color.white);
            g.fillRect(100, 200, 300, 50);
            g.setColor(Color.black);
            g.drawRect(100, 200, 300, 50);
            g.drawString("NAJLEPSZE WYNIKI", 140, 235);
            //ZASADY GRY
            g.setColor(Color.white);
            g.fillRect(100, 290, 300, 50);
            g.setColor(Color.black);
            g.drawRect(100, 290, 300, 50);
            g.drawString("ZASADY GRY!", 180, 325);

            //WYJSCIE
            g.setColor(Color.white);
            g.fillRect(100, 380, 300, 50);
            g.setColor(Color.black);
            g.drawRect(100, 380, 300, 50);
            g.drawString("WYJSCIE!", 200, 415);
        }

        else if (game.gameState == GAMESTATE.BestResults) {

            g.setFont(font3);
            g.setColor(Color.black);
            g.drawString("Najlepsze wyniki:", 100, 60);

            //OK
            g.setFont(font2);
            g.setColor(Color.white);
            g.fillRect(200, 380, 100, 50);
            g.setColor(Color.black);
            g.drawRect(200, 380, 100, 50);
            g.drawString("OK", 230, 415);
        }

        else if (game.gameState == GAMESTATE.HowToPlay) {
            g.setFont(font3);
            g.setColor(Color.black);
            g.drawString("Zasady gry:", 100, 60);
            g.setFont(font2);
            g.drawString("Ułóż wszystkie skrzynki na swoje miejsce!", 50, 120);
            g.drawString("Sterowanie:", 100, 160);
            g.setFont(font4);
            g.drawString("A-idź w lewo", 100, 200);
            g.drawString("W-idź do góry", 100, 230);
            g.drawString("S-idź w dół", 100, 260);
            g.drawString("W-idź w prawo", 100, 290);
            g.drawString("E-pauza", 100, 320);
            //OK
            g.setFont(font2);
            g.setColor(Color.white);
            g.fillRect(200, 380, 100, 50);
            g.setColor(Color.black);
            g.drawRect(200, 380, 100, 50);
            g.drawString("OK", 230, 415);

        }
        else if (game.gameState==GAMESTATE.Game){

            g.drawString("liczba ruchów:",220, 45);

        }
        else if (game.gameState == GAMESTATE.GameEndWin||game.gameState == GAMESTATE.GameEndLose) {
                g.setFont(font);
                g.setColor(Color.black);
                if(game.gameState == GAMESTATE.GameEndWin)
                    g.drawString("Wygrałeś :)", 150, 70);
                else if(game.gameState == GAMESTATE.GameEndLose)
                    g.drawString("Przegrałeś :(", 150, 70);
                g.setFont(font3);
                g.drawString("Twój wynik: ", 130, 240);
                int timeValue = 0;
                for (Long TimeFromEachLevel : AppUtils.TIME_LIST) {
                    timeValue+=TimeFromEachLevel;
                }

                if (game.gameState == GAMESTATE.GameEndLose){
                    timeValue+=300; //jezeli nie skonczymy poziomu to nie dostaniemy czasu dlatego trzeba dodac 300
                }

                final_score=game.score-timeValue- handler.move_counter; //koncowy wynik
                g.drawString(String.valueOf(final_score), 300, 240); //wyswietlanie puntkow

                g.drawString("Nick:", 130, 160);
                player_name=handler.parameters.getPlayerName();
                g.drawString(player_name,210,160);

                g.setFont(font2);
                g.setColor(Color.white);
                g.fillRect(100, 290, 300, 50);
                g.setColor(Color.black);
                g.drawRect(100, 290, 300, 50);
                g.drawString("SPRÓBUJ PONOWNIE", 130, 325);

                g.setColor(Color.white);
                g.fillRect(100, 380, 300, 50);
                g.setColor(Color.black);
                g.drawRect(100, 380, 300, 50);
                g.drawString("WRÓC DO MENU", 155, 415);
        }
    }


    public void mousePressed(MouseEvent e) {
        int posX = e.getX();
        int posY = e.getY();

        if (game.gameState == GAMESTATE.Menu) {
            //GRAJ
            if (mousePosition(posX, posY, (int) (100 * game.get_x_scale()), (int) (110 * game.get_y_scale()), (int) (300 * game.get_x_scale()), (int) (50 * game.get_y_scale()))) {

                frame1 = new JFrame("Ustawienia");
                frame1.setSize(500, 500);
                frame1.setTitle("Ustawienia gry");
                frame1.setVisible(true);
                frame1.setLocationRelativeTo(null);
                frame1.setResizable(false);
                frame1.setLayout(null);
                frame1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                frame1.getContentPane().setBackground(Color.gray);
                Font font4 = new Font("Times New Roman", 1, 30);
                Font font5 = new Font("Times New Roman", 3, 24);
                Font font6 = new Font("Times New Roman", 2, 22);

                l = new JLabel("Podaj swój nick:");
                l.setFont(font4);
                l.setBounds(130, 160, 300, 30);
                tf = new JTextField("");
                tf.setFont(font5);
                tf.setBounds(100, 200, 300, 50);

                Play = new JButton("OK");
                Play.setBackground(Color.WHITE);
                Play.setFont(font6);
                Play.setBounds(100, 300, 120, 50);
                Play.addActionListener(this);

                Back = new JButton("ANULUJ");
                Back.setBackground(Color.WHITE);
                Back.setFont(font6);
                Back.setBounds(280, 300, 120, 50);
                Back.addActionListener(this);
                frame1.add(l);
                frame1.add(tf);
                frame1.add(Play);
                frame1.add(Back);


            }

            //NAJLEPSZE WYNIKI
            if (mousePosition(posX, posY, (int) (100 * game.get_x_scale()), (int) (200 * game.get_y_scale()), (int) (300 * game.get_x_scale()), (int) (50 * game.get_y_scale()))) {
                game.gameState = GAMESTATE.BestResults;

            }

            //ZASADY GRY
            if (mousePosition(posX, posY, (int) (100 * game.get_x_scale()), (int) (290 * game.get_y_scale()), (int) (300 * game.get_x_scale()), (int) (50 * game.get_y_scale()))) {
                game.gameState = GAMESTATE.HowToPlay;

            }

            //WYJESCIE
            if (mousePosition(posX, posY, (int) (100 * game.get_x_scale()), (int) (380 * game.get_y_scale()), (int) (300 * game.get_x_scale()), (int) (50 * game.get_y_scale()))) {
                System.exit(1);
            }
        }

        if (game.gameState == GAMESTATE.BestResults || game.gameState == GAMESTATE.HowToPlay) {
            if (mousePosition(posX, posY, (int) (200 * game.get_x_scale()), (int) (380 * game.get_y_scale()), (int) (100 * game.get_x_scale()), (int) (50 * game.get_y_scale()))) {
                game.gameState = GAMESTATE.Menu;
            }
        }

        if (game.gameState == GAMESTATE.GameEndWin || game.gameState == GAMESTATE.GameEndLose){
            //sprobuj ponownie
            if (mousePosition(posX, posY, (int) (100 * game.get_x_scale()), (int) (290 * game.get_y_scale()), (int) (300 * game.get_x_scale()), (int) (50 * game.get_y_scale()))) {
                game.gameState = GAMESTATE.Game;
            }

            //wroc do menu
            if (mousePosition(posX, posY, (int) (100 * game.get_x_scale()), (int) (380 * game.get_y_scale()), (int) (300 * game.get_x_scale()), (int) (50 * game.get_y_scale()))) {
                game.gameState = GAMESTATE.Menu;
            }
        }

    }

    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source==Play) {
            if (tf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(game, "Należy podać nick", "Błąd", JOptionPane.WARNING_MESSAGE);
                return;
            }
            game.gameState = GAMESTATE.Game;
            if (!STOPWATCH.isStarted()){
                STOPWATCH.start();
            }
            handler.parameters.setPlayerName(tf.getText());
            frame1.dispose();
        }
        if(source==Back){
            game.gameState= GAMESTATE.Menu;
            frame1.dispose();


        }
    }
}
