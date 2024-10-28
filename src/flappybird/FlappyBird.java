/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import pkg2dgamesframework.AFrameOnImage;
import pkg2dgamesframework.Animation;
import pkg2dgamesframework.GameScreen;
import java.sql.*;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 *
 * @author acer
 */
public class FlappyBird extends GameScreen {

    private BufferedImage birds;
    private BufferedImage playButton;
    private BufferedImage highscoreButton;
    private Animation bird_anim;/*hoat anh cua chim*/
    private String playerName;

    public static float g = 0.3f;
    /*gia toc huong tam*/
    private Bird bird;
    private Ground ground;/*mat dat*/
    private ChimneyGroup chimneyGroup;/*ong cong*/
    private int Point = 0;/*diem so*/

    private int START_SCREEN = 0;
    private int BEGIN_SCREEN = 1;
    private int GAMEPLAY_SCREEN = 2;
    private int GAMEOVER_SCREEN = 3;

    private int CurrentScreen = START_SCREEN;

    public FlappyBird() {
        super(500, 600);/*khung man hinh*/
        setResizable(false);
        
        // Load hình ảnh cho chim
        try {
            birds = ImageIO.read(new File("Assets/bird_sprite.png"));
        } catch (IOException ex) {
        }

        /* AFrameOnImage--Sizeimg = 180x60px */
        bird_anim = new Animation(70);/*Time hien thi 1 hinh (ms)*/
        AFrameOnImage f;
        f = new AFrameOnImage(0, 0, 60, 60);
        bird_anim.AddFrame(f);
        f = new AFrameOnImage(60, 0, 60, 60);
        bird_anim.AddFrame(f);
        f = new AFrameOnImage(120, 0, 60, 60);
        bird_anim.AddFrame(f);
        f = new AFrameOnImage(60, 0, 60, 60);
        bird_anim.AddFrame(f);
        
        /*khoi tao toa do cua chim*/
        bird = new Bird(50, 250, 50, 50);

        /*khoi tao toa do mat dat*/
        ground = new Ground();

        /*khoi tao Ong cong*/
        chimneyGroup = new ChimneyGroup();
        
        /*Nút play*/
        // Load hình ảnh cho nút Playbtn
        try {
            playButton = ImageIO.read(new File("Assets/startbtn.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        /*Nút highscore*/
        // Load hình ảnh cho nút highscore
        try {
            highscoreButton = ImageIO.read(new File("Assets/highscorebtn.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
            addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        // Lấy tọa độ chuột
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Kiểm tra nếu chuột được nhấp vào nút Play
        if (CurrentScreen == START_SCREEN && mouseX >= 100 && mouseX <= 100 + playButton.getWidth()
                && mouseY >= 350 && mouseY <= 350 + playButton.getHeight()) {
            // Hiện hộp thoại nhập tên
            playerName = JOptionPane.showInputDialog(FlappyBird.this, "Nhập tên của bạn:", "Nhập Tên", JOptionPane.PLAIN_MESSAGE);
            
            // Chuyển sang màn hình BEGIN_SCREEN nếu tên không rỗng
            if (playerName != null && !playerName.isEmpty()) {
                CurrentScreen = BEGIN_SCREEN;
            }
        }

        // Kiểm tra nếu chuột được nhấp vào nút Highscore
        if (CurrentScreen == START_SCREEN && mouseX >= 280 && mouseX <= 280 + highscoreButton.getWidth()
                && mouseY >= 350 && mouseY <= 350 + highscoreButton.getHeight()) {
            // Hiển thị bảng xếp hạng
            showHighscoreDialog();
        }
    }
});
      
        BeginGame();
    }

    public static void main(String[] args) {
        new FlappyBird();
    }

    /*restart*/
    private void resetGame() {
        bird.setPos(50, 250);
        bird.setVt(0);
        bird.setLive(true);
        Point = 0;
        chimneyGroup.resetChimneys();
    }

    @Override
    public void GAME_UPDATE(long deltaTime) {
        if (CurrentScreen == START_SCREEN) {
            //Code
        } 
        if (CurrentScreen == BEGIN_SCREEN) {
            resetGame();
        } 
        else if (CurrentScreen == GAMEPLAY_SCREEN) {

            if (bird.getLive()) {
                bird_anim.Update_Me(deltaTime);
            }/*update animation*/

            bird.update(deltaTime);/*update van toc roi*/
            ground.Update();/*update mat dat*/

            chimneyGroup.update();/*update chimney*/

            for (int i = 0; i < ChimneyGroup.SIZE; i++) {
                if (bird.getRect().intersects(chimneyGroup.getChimney(i).getRect())) /*intersects kiem tra bird.getRect và chimneyGroup.getRect co giao nhau hay khong*/ {
                    if (bird.getLive()) {
                        bird.fallSound.play();
                    }
                    bird.setLive(false);
                    CurrentScreen = GAMEOVER_SCREEN;
                    //Lưu dữ liệu vào database
                     ScoreManager.addPlayerScore(playerName, Point);
                }
            }

            for (int i = 0; i < ChimneyGroup.SIZE; i++) {
                if (bird.getPosX() > chimneyGroup.getChimney(i).getPosX() && !chimneyGroup.getChimney(i).getIsBehindBird() && i % 2 == 0) /*neu toa do chim > toa do ong khoi && ong cong nam sau chim; i%2=0 de khi chim vuot qua 1 cap ong khoi se +1 point*/ {
                    Point++;
                    chimneyGroup.getChimney(i).setIsBehindBird(true);
                    bird.getpointSound.play();
                }
            }

            if (bird.getPosY() + bird.getH() > ground.getYGround()) {
                bird.fallSound.play();
                CurrentScreen = GAMEOVER_SCREEN;
                //Lưu dữ liệu vào database
                ScoreManager.addPlayerScore(playerName, Point);
            }

        } else { }

    }

    @Override
    public void GAME_PAINT(Graphics2D g2) {

        /*Background*/
        BufferedImage backgroundImage = loadImage("Assets/bglight.png");
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, null);
        } else {
            // Nếu không thể tải hình ảnh, bạn có thể vẽ một màu nền thay thế.
            g2.setColor(Color.decode("#FF99FF"));
            g2.fillRect(0, 0, MASTER_WIDTH, MASTER_HEIGHT);
        }

        /*Ong Cong*/
        chimneyGroup.paint(g2);

        /*mat dat*/
        ground.Paint(g2);
        
        /*man hinh bat dau*/
        if (CurrentScreen == START_SCREEN) {
            g2.drawImage(playButton, 100, 350, null);
            g2.drawImage(highscoreButton, 280,350, null);
        }

        /*do xoay cua chim khi bay*/
        if (CurrentScreen == BEGIN_SCREEN) {
            bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, 0);
        } 
        else if (bird.getIsFlying()) {
            bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, (float) -0.5);
        } 
        else {
            bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, 0);
        }

        if (CurrentScreen == BEGIN_SCREEN) {
            
//            g2.setColor(Color.red);
//            g2.setFont(new Font("Arial", Font.BOLD, 20));
//            g2.drawString("Press SPACE to START", 150, 250);

            BufferedImage taptap = loadImage("Assets/taptap.png");
            g2.drawImage(taptap, 200, 250, null);
        }
        if (CurrentScreen == GAMEOVER_SCREEN) {
            g2.setColor(Color.red);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString("Press SPACE to RETRY", 150, 250);
        }
            /* point*/
            g2.setColor(Color.red);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.drawString(playerName+"'s Point: " + Point, 30, 50);/*vi tri cua point*/
    }

    @Override
    public void KEY_ACTION(KeyEvent e, int Event) {
        /*tao su kien nhan nut*/
        if (Event == KEY_PRESSED) {
            if (CurrentScreen == BEGIN_SCREEN) {
                CurrentScreen = GAMEPLAY_SCREEN;
            } /*Bat dau*/ else if (CurrentScreen == GAMEPLAY_SCREEN) {
                if (bird.getLive()) {
                    bird.fly();
                }/*chim bay len*/ } else if (CurrentScreen == GAMEOVER_SCREEN) {
                CurrentScreen = BEGIN_SCREEN;
            }/*restart*/
        }
    }
    
    // Phương thức tải hình ảnh từ tệp
    private BufferedImage loadImage(String fileName) {
        try {
            return ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
        // Phương thức hiển thị JFrame cho bảng xếp hạng
    private void showHighscoreDialog() {
        SwingUtilities.invokeLater(() -> {
            HighscoreDialog highscoreFrame = new HighscoreDialog();
            highscoreFrame.setVisible(true);
        });
    }

}
