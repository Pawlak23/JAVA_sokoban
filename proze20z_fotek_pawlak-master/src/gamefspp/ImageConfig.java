package gamefspp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageConfig {
    private BufferedImage image;

    public BufferedImage loadPicture(String path) throws IOException {
        try {
            image = ImageIO.read(getClass().getResource(path));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}