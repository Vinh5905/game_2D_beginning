package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); // tính toán co giãn vừa với panel trên (kiểu tự thêm border, ... mà không chiếm size của game đã set trong panel)

        window.setLocationRelativeTo(null); // căn giữa màn hình
        window.setVisible(true); // mặc định ẩn
    }
}