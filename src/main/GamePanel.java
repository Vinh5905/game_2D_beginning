package main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // vì 16x16 nhỏ nên phải scale lên

    final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768px
    final int screenHeight = tileSize * maxScreenRow; // 576px

    Thread gameThread;
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight)); // lập kích thước màn hình
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // cho toàn khung vào buffer -> sau đó mới đán (ko phải là dán liên tục từng frame)
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // gọi run() bên dưới
    }

    @Override
    public void run() {
        // game loop - core of game
    }
}
