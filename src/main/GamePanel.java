package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

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

    // FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight)); // lập kích thước màn hình
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // cho toàn khung vào buffer -> sau đó mới đán (ko phải là dán liên tục từng frame)
        this.addKeyListener(keyH);
        this.setFocusable(true); // ưu tiên nhật event cho panel
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // gọi run() bên dưới
    }

    // Contruct the first game loop (Sleep method)
    // @Override
    // public void run() {
    //     // game loop - core of game
    //     double drawInterval = 1000000000/FPS; // sử dụng nano second
    //     double nextDrawTime = System.nanoTime() + drawInterval;

    //     while (gameThread != null) {
    //         // 1: UPDATE: update information such as character positions (X, Y)
    //         update();

    //         // 2. DRAW: draw the screen with the updated information (FPS)
    //         repaint();

    //         try {
    //             double remainingTime = nextDrawTime - System.nanoTime();
    //             remainingTime = remainingTime / 1000000; // from nanos to millis (do sleep cần millis)

    //             if (remainingTime < 0) {
    //                 remainingTime = 0;
    //             }

    //             Thread.sleep((long) remainingTime);

    //             nextDrawTime += drawInterval;

    //         } catch (InterruptedException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

    // Contruct the first game loop (Delta/Accumulator method)
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastime = System.nanoTime();
        long currentTime;
        
        while (gameThread != null) {
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastime) / drawInterval; // tính mỗi lần tăng bn % -> nào đủ 100% (= 1) thì new frame)
            lastime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if (keyH.upPressed == true) {
            playerY -= playerSpeed;
        }
        else if (keyH.downPressed == true) {
            playerY += playerSpeed;
        }
        else if (keyH.leftPressed == true) {
            playerX -= playerSpeed;
        }
        else if (keyH.rightPressed == true) {
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g) { // built-in func in java // tự động gọi khi call repaint()
        super.paintComponent(g); // Xóa màn hình cũ trước khi vẽ màn hình mới

        Graphics g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose(); // Giải phóng bộ nhớ của bút vẽ (tiết kiệm tài nguyên hệ thống)
    }
}
