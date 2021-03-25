package gamefspp;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GoldenChest extends GameObject{
    public GoldenChest(int x, int y, ID id, BufferedImage image) {
        super(x, y, id, image);
    }

    @Override
    public void tick() throws IOException {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(image,x,y,null);
    }
}
