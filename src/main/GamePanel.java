package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // vì 16x16 nhỏ nên phải scale lên

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768px
    public final int screenHeight = tileSize * maxScreenRow; // 576px

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound music = new Sound();
    Sound se = new Sound();
    public UI ui = new UI(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight)); // lập kích thước màn hình
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // cho toàn khung vào buffer -> sau đó mới đán (ko phải là dán liên tục từng frame)
        this.addKeyListener(keyH);
        this.setFocusable(true); // ưu tiên nhật event cho panel
    }

    public void setupGame() {
        aSetter.setupObject();
        playMusic(0);
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
        player.update();
    }

    public void paintComponent(Graphics g) { // built-in func in java // tự động gọi khi call repaint()
        super.paintComponent(g); // Xóa màn hình cũ trước khi vẽ màn hình mới

        Graphics2D g2 = (Graphics2D) g;

        // DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

        // mấy drawImage của Graphics2D đều tính theo tọa độ screen panel
        // TILE
        tileM.draw(g2);

        // OBJECT
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        // PLAYER
        player.draw(g2);

        // UI
        ui.draw(g2);

        // DEBUG
        if (keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long timePassed = drawEnd - drawStart;
            g2.drawString("Time: " + timePassed, 10, 400);
            System.out.println("Draw time: " + timePassed);
        }

        g2.dispose(); // Giải phóng bộ nhớ của bút vẽ (tiết kiệm tài nguyên hệ thống)
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
