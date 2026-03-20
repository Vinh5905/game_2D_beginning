package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        
        int tileOutsideScreen = 2;
        int pixelOutsideScreen = tileOutsideScreen * gp.tileSize;

        if (
            worldX + pixelOutsideScreen > gp.player.worldX - gp.player.screenX &&
            worldX - pixelOutsideScreen < gp.player.worldX + gp.player.screenX &&
            worldY + pixelOutsideScreen > gp.player.worldY - gp.player.screenY &&
            worldY - pixelOutsideScreen < gp.player.worldY + gp.player.screenY
        )
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
