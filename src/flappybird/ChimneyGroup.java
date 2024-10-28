/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flappybird;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import pkg2dgamesframework.QueueList;

/**
 *
 * @author acer
 */
public class ChimneyGroup {
    
    private QueueList<Chimney> chimneys;
    
    private BufferedImage chimneyImage, chimneyImage2;
    
    public static int SIZE = 6;
    
    private int topChimneyY = -250;
    private int bottomChimneyY = 250;
    
    public Chimney getChimney(int i){
        return chimneys.get(i);
    }
    
    /*random vi do cao ong cong*/
    public int getRandomY(){
        Random random = new Random();
        int a;
        a = random.nextInt(10);
        
        return a*20;
    }
    
    public ChimneyGroup(){
        
        try {
            chimneyImage =  ImageIO.read(new File("Assets/chimneygreen.png"));/*link image ongcong*/
            chimneyImage2 =  ImageIO.read(new File("Assets/chimneygreen2.png"));/*link image ongcong*/
        } catch (IOException ex) {}
        
        chimneys = new QueueList<Chimney>();
        
        Chimney cn;
        
        /*Toa do, chieu dai, chieu rong ong cong*/
        for( int i = 0; i < SIZE/2 ; i++){
            
            int deltaY = getRandomY();
            
            cn = new Chimney(500+i*300, bottomChimneyY  + deltaY, 60, 323);/*ong duoi*/
            chimneys.push(cn);
            
            cn = new Chimney(500+i*300, topChimneyY  + deltaY, 60, 323);/*ong tren*/
            chimneys.push(cn);
        }
    }
    
    public void resetChimneys(){
         chimneys = new QueueList<Chimney>();
        
        Chimney cn;
        
        /*Toa do, chieu dai, chieu rong ong cong*/
               for( int i = 0; i < SIZE/2 ; i++){
            
            int deltaY = getRandomY();
            
            cn = new Chimney(500+i*300, bottomChimneyY  + deltaY, 60, 323);/*ong duoi*/
            chimneys.push(cn);
            
            cn = new Chimney(500+i*300, topChimneyY  + deltaY, 60, 323);/*ong tren*/
            chimneys.push(cn);
        }
    }
    
    public void update(){
        for( int i = 0; i < SIZE ; i++){
            chimneys.get(i).update();
        }
        
        if(chimneys.get(0).getPosX() < -60){
            
            int deltaY = getRandomY();
            
            Chimney cn;
            cn = chimneys.pop();
            cn.setPosX(chimneys.get(4).getPosX() + 300);
            cn.setPosY(bottomChimneyY + deltaY);
            cn.setIsBehindBird(false);/*tranh truong hop chim vuot qua 3 cap ong cong se khong di tiep duoc*/
            chimneys.push(cn);
            
            cn = chimneys.pop();
            cn.setPosX(chimneys.get(4).getPosX());
            cn.setPosY(topChimneyY + deltaY);
            cn.setIsBehindBird(false);
            chimneys.push(cn);
        }
    }
    
    public void paint(Graphics2D g2){
        for( int i = 0; i < 6 ; i++){
            if(i%2 == 0){
                g2.drawImage(chimneyImage, (int)chimneys.get(i).getPosX(), (int)chimneys.get(i).getPosY(), null);
            }
            else{
                g2.drawImage(chimneyImage2, (int)chimneys.get(i).getPosX(), (int)chimneys.get(i).getPosY(), null);
            }
            
        }
    }
}
