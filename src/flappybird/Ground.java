/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flappybird;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author acer
 */
public class Ground {
    private BufferedImage groundImage;
    
    private int x1, y1, x2, y2;
    
     public Ground(){
        try {
            groundImage = ImageIO.read(new File("Assets/ground.png"));
        } catch (IOException ex) {}
        x1 = 0;
        y1 = 500;
        x2= x1 + 830;
        y2 = 500;
     }
     public void Update(){
         x1 -= 3;/*van toc cua mat dat*/
         x2 -= 3;
         
         if(x2 < 0) x1 = x2 + 830;
         if(x1 < 0) x2 = x1 + 830;
     }
     
    public void Paint(Graphics2D g2){
        g2.drawImage(groundImage, x1, y1, null);
        g2.drawImage(groundImage, x2, y2, null);
    }
    public int getYGround(){
        return y1;
    }
}
