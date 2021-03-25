package gamefspp;

import org.apache.commons.lang3.time.StopWatch;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


import static gamefspp.AppUtils.STOPWATCH;


public class KeyInput extends KeyAdapter {

    private Handler handler;
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;
    private GameObject player;
    private Game game;
    public static boolean PAUSED;

    public KeyInput(Handler handler, Game game) {
        this.handler = handler;

        this.game = game;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (game.gameState == Game.GAMESTATE.Game) {
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getId() == ID.Player) {
                    player = tempObject;//szukamy Playera sposród wszystkich obiektów

                }
            }
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);

                if (tempObject.getId() == ID.Player || tempObject.getId() == ID.Chest || tempObject.getId() == ID.GoldenChest) {
                    if (key == KeyEvent.VK_A) {

                        if (checkWallCollision(player, LEFT_COLLISION)) {
                            return; //jeżeli nastąpi kolizja to checkWallCollision zwraca prawdę, co przez co wychodzimy z ifa i nie zmieniamy położenia gracza
                        }
                        if (checkChestCollision(player, LEFT_COLLISION)) {
                            return;
                        }

                        if (tempObject.getId() == ID.Chest || tempObject.getId() == ID.GoldenChest) {
                            if (tempObject.getX() + 50 == player.getX() && tempObject.getY() == player.getY() && !KeyInput.PAUSED) {
                                tempObject.setX(tempObject.getX() - 50);

                            }
                        }
                        if (tempObject.getId() == ID.Player) {
                            //  tempObject.setX(tempObject.getX() - 50);//jeżeli nie nastąpi kolizja to zmieniamy położenie gracza
                            if (!KeyInput.PAUSED) {
                                handler.move_counter++;
                            }
                            for (int j = 0; j < 50; j++) {
                                player.move(-1, 0);
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
                            }
                        }

                    } else if (key == KeyEvent.VK_W) {
                        if (checkWallCollision(player, TOP_COLLISION)) {
                            return;
                        }
                        if (checkChestCollision(player, TOP_COLLISION)) {
                            return;
                        }
                        if (tempObject.getId() == ID.Chest || tempObject.getId() == ID.GoldenChest) {
                            if (tempObject.getX() == player.getX() && tempObject.getY() + 50 == player.getY() && !KeyInput.PAUSED) {
                                tempObject.setY(tempObject.getY() - 50);
                            }
                        }

                        if (tempObject.getId() == ID.Player) {
                            if (!KeyInput.PAUSED) {
                                handler.move_counter++;
                            }
                            for (int j = 0; j < 50; j++) {
                                player.move(0, -1);
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
                            }
                        }
                    } else if (key == KeyEvent.VK_S) {
                        if (checkWallCollision(player, BOTTOM_COLLISION)) {
                            return;
                        }
                        if (checkChestCollision(player, BOTTOM_COLLISION)) {
                            return;
                        }

                        if (tempObject.getId() == ID.Chest || tempObject.getId() == ID.GoldenChest) {
                            if (player.getX() == tempObject.getX() && player.getY() == tempObject.getY() - 50 && !KeyInput.PAUSED) {
                                tempObject.setY(tempObject.getY() + 50);
                            }
                        }

                        if (tempObject.getId() == ID.Player) {
                            if (!KeyInput.PAUSED) {
                                handler.move_counter++;
                            }
                            for (int j = 0; j < 50; j++) {
                                player.move(0, 1);
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
                            }
                        }
                    } else if (key == KeyEvent.VK_D) {
                        if (checkWallCollision(player, RIGHT_COLLISION)) {
                            return;
                        }
                        if (checkChestCollision(player, RIGHT_COLLISION)) {
                            return;
                        }

                        if (tempObject.getId() == ID.Chest || tempObject.getId() == ID.GoldenChest) {
                            if (player.getX() == tempObject.getX() - 50 && tempObject.getY() == player.getY() && !KeyInput.PAUSED) {
                                tempObject.setX(tempObject.getX() + 50);
                            }
                        }
                        if (tempObject.getId() == ID.Player) {
                            if (!KeyInput.PAUSED) {
                                handler.move_counter++;
                            }
                            for (int j = 0; j < 50; j++) {
                                player.move(1, 0);
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
                            }
                        }
                    } else if (key == KeyEvent.VK_E) {

                        if (STOPWATCH.isStarted() && !PAUSED) {
                            STOPWATCH.suspend();
                            PAUSED = true;

                        } else if (STOPWATCH.isSuspended() && PAUSED) {
                            STOPWATCH.resume();
                            PAUSED = false;
                        }

                    }
                }
            }
            //System.out.println(game.gameState);

        }
    }

    private boolean checkWallCollision(GameObject player, int type) {

        switch (type) {

            case LEFT_COLLISION:


                for (int i = 0; i < handler.object.size(); i++) {
                    if (handler.object.get(i).getId() == ID.Wall) {
                        if (player.LeftCollision(handler.object.get(i))) {
                            return true;
                        }
                    }
                }

                return false;

            case RIGHT_COLLISION:

                for (int i = 0; i < handler.object.size(); i++) {
                    if (handler.object.get(i).getId() == ID.Wall) {

                        if (player.RightCollision(handler.object.get(i))) {

                            return true;
                        }
                    }
                }


                return false;

            case TOP_COLLISION:

                for (int i = 0; i < handler.object.size(); i++) {
                    if (handler.object.get(i).getId() == ID.Wall) {

                        if (player.TopCollision(handler.object.get(i))) {

                            return true;
                        }
                    }
                }

                return false;

            case BOTTOM_COLLISION:

                for (int i = 0; i < handler.object.size(); i++) {
                    if (handler.object.get(i).getId() == ID.Wall) {

                        if (player.BottomCollision(handler.object.get(i))) {

                            return true;
                        }
                    }
                }
                return false;

            default:
                break;
        }
        return false;
    }

    private boolean checkChestCollision(GameObject player, int type) {

        switch (type) {

            case LEFT_COLLISION:


                for (int i = 0; i < handler.object.size(); i++) {
                    if (handler.object.get(i).getId() == ID.Chest || handler.object.get(i).getId() == ID.GoldenChest) {
                        if (player.LeftCollision(handler.object.get(i)) && ChestCollision(handler.object.get(i), 1)) {
                            return true;

                        }
                    }
                }

                return false;

            case RIGHT_COLLISION:

                for (int i = 0; i < handler.object.size(); i++) {
                    if (handler.object.get(i).getId() == ID.Chest || handler.object.get(i).getId() == ID.GoldenChest) {
                        if (player.RightCollision(handler.object.get(i)) && ChestCollision(handler.object.get(i), 2)) {
                            return true;

                        }
                    }
                }


                return false;

            case TOP_COLLISION:

                for (int i = 0; i < handler.object.size(); i++) {
                    if (handler.object.get(i).getId() == ID.Chest || handler.object.get(i).getId() == ID.GoldenChest) {
                        if (player.TopCollision(handler.object.get(i)) && ChestCollision(handler.object.get(i), 3)) {
                            return true;

                        }
                    }
                }

                return false;

            case BOTTOM_COLLISION:

                for (int i = 0; i < handler.object.size(); i++) {
                    if (handler.object.get(i).getId() == ID.Chest || handler.object.get(i).getId() == ID.GoldenChest) {
                        if (player.BottomCollision(handler.object.get(i)) && ChestCollision(handler.object.get(i), 4)) {
                            return true;

                        }
                    }
                }
                return false;

            default:
                break;
        }
        return false;
    }

    private boolean ChestCollision(GameObject chest, int type) {

        switch (type) {

            case LEFT_COLLISION:


                for (int i = 0; i < handler.object.size(); i++) {
                    if (handler.object.get(i).getId() == ID.Chest || handler.object.get(i).getId() == ID.Wall || handler.object.get(i).getId() == ID.GoldenChest) {
                        if (chest.LeftCollision(handler.object.get(i))) {

                            return true;

                        }
                    }
                }

                return false;

            case RIGHT_COLLISION:

                for (int i = 0; i < handler.object.size(); i++) {
                    if (handler.object.get(i).getId() == ID.Chest || handler.object.get(i).getId() == ID.Wall || handler.object.get(i).getId() == ID.GoldenChest) {
                        if (chest.RightCollision(handler.object.get(i))) {

                            return true;

                        }
                    }
                }

                return false;

            case TOP_COLLISION:

                for (int i = 0; i < handler.object.size(); i++) {
                    if (handler.object.get(i).getId() == ID.Chest || handler.object.get(i).getId() == ID.Wall || handler.object.get(i).getId() == ID.GoldenChest) {
                        if (chest.TopCollision(handler.object.get(i))) {

                            return true;

                        }
                    }
                }

                return false;

            case BOTTOM_COLLISION:

                for (int i = 0; i < handler.object.size(); i++) {
                    if (handler.object.get(i).getId() == ID.Chest || handler.object.get(i).getId() == ID.Wall || handler.object.get(i).getId() == ID.GoldenChest) {
                        if (chest.BottomCollision(handler.object.get(i))) {

                            return true;

                        }
                    }
                }
                return false;

            default:
                break;
        }
        return false;
    }

}
