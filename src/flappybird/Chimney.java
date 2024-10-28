/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flappybird;

import java.awt.Rectangle;
import pkg2dgamesframework.Objects;

/**
 *
 * @author acer
 */
public class Chimney extends Objects{
    
        private Rectangle rect;
      /*   private int speedIncrease = 0;
        private int updateCount = 0; */
        private boolean isBehindBird = false;/*xac dinh chim da vuot qua ong cong*/

    public  Chimney ( int x, int y, int w, int h){
        super (x, y, w, h);
        rect = new Rectangle(x, y, w, h);
    }
    public void update(){
        setPosX(getPosX() -4 /*(2 +speedIncrease) */ );/*van toc chua ong cong*/
        rect.setLocation((int) this.getPosX(), (int) this.getPosY()); 
      /*    updateCount++;
        if (updateCount % 100 == 0) { // Chỉ tăng tốc độ sau mỗi 100 lần gọi phương thức update()
            speedIncrease++; 
        }*/
    
    }
    
    public Rectangle getRect(){
        return rect;
    }
    
    public void setIsBehindBird (boolean b){ //đặ
        isBehindBird = b;
    }
    
    public boolean getIsBehindBird(){
        return isBehindBird;
    }
}
