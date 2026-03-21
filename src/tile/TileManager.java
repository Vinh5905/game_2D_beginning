package tile;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];
        getTileImage();
        loadMap("/res/maps/world01.txt");
    }

    public void getTileImage() {
        setup(0, "grass", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "earth", false);
        setup(4, "tree", true);
        setup(5, "sand", false);
    }

    public void setup(int index, String imageName, boolean collision) {

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + imageName + ".png"));
            tile[index].image = UtilityTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapPath) {
        try {
            InputStream is = getClass().getResourceAsStream(mapPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int row = 0; row < gp.maxWorldRow; row++) {
                String line = br.readLine();
                if (line == null) break;

                String[] numbers = line.split(" ");

                for (int col = 0; col < gp.maxWorldCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[row][col] = num;
                }
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        // map cho world
        for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
            for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
                int tileNum = mapTileNum[worldRow][worldCol];

                int worldX = gp.tileSize * worldCol;
                int worldY = gp.tileSize * worldRow;

                // tính tọa độ vật thay đổi sau khi screen di chuyển
                // tưởng tượng là map di chuyển, nhân vật dứng im
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;
                
                // for performance (not show all world)
                int tileOutsideScreen = 2;
                int pixelOutsideScreen = tileOutsideScreen * gp.tileSize;
                if (
                    worldX + pixelOutsideScreen > gp.player.worldX - gp.player.screenX &&
                    worldX - pixelOutsideScreen < gp.player.worldX + gp.player.screenX &&
                    worldY + pixelOutsideScreen > gp.player.worldY - gp.player.screenY &&
                    worldY - pixelOutsideScreen < gp.player.worldY + gp.player.screenY
                )
                    // Ko hiệu quả vì vẫn tốn chút thời gian để draw scale tileSize
                    // g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

                    // Không cần scale vì đã scale setup ở trên rồi
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);

            }
        }
    }
}
