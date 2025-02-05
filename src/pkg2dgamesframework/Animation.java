/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dgamesframework;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author phamn
 */
public class Animation {
    
    private long beginTime = 0;
    
    private long mesure = 20;
    
    private AFrameOnImage[] frames;
    private int NumOfFrame = 0;/*so frame*/
    private int CurrentFrame = 0;
    
    /*Thoi gian hien thi khung hinh*/
    public Animation(long mesure){
        this.mesure = mesure;
    }
    
    public void Update_Me(long deltaTime){
        if(NumOfFrame>0)/*so frame > 0 thi bat dau */
        {
            /*deltaTime-beginTime> mesure thi tang so frame hien tai*/
            if(deltaTime - beginTime > mesure){
                CurrentFrame++;
                /*Frame hien tai >= so frame thi tra ve 0*/
                if(CurrentFrame>=NumOfFrame) 
                    CurrentFrame = 0;
                beginTime = deltaTime;
            }
        }
    }
    public void AddFrame(AFrameOnImage sprite){
        AFrameOnImage[] bufferSprites = frames;
        frames = new AFrameOnImage[NumOfFrame+1];
        for(int i = 0;i<NumOfFrame;i++) frames[i] = bufferSprites[i];
        frames[NumOfFrame] = sprite;
        NumOfFrame++;
    }
    
    public void PaintAnims(int x, int y, BufferedImage image, Graphics2D g2, int anchor, float rotation){
        frames[CurrentFrame].Paint(x, y, image, g2, anchor, rotation);
    }
}
