package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {
    public static BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType()); // tạo giấy trắng
        Graphics2D g2 = scaledImage.createGraphics(); // bút vẽ lên cái tờ giấy đó
        g2.drawImage(original, 0, 0, width, height, null); // vẽ lại cái tile với kích thước đã scale
        g2.dispose();

        return scaledImage;
    }
}
