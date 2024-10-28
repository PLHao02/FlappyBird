/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flappybird;

import java.awt.Rectangle;
import java.io.File;
import pkg2dgamesframework.Objects;
import pkg2dgamesframework.SoundPlayer;

/**
 *
 * @author acer
 */
public class Bird extends Objects {

    private float vt = 0;
    /*van toc roi = 0*/

    private boolean isFlying = false;
    /*kiem tra co dang bay khong?*/

    private Rectangle rect;

    private boolean isLive = true;/*kiem tra chim con song khong*/

    public SoundPlayer fapSound, fallSound, getpointSound;/*tao am thanh vo canh, va cham, ghi diem*/

    public Bird(int x, int y, int w, int h) {
        super(x, y, w, h);
        rect = new Rectangle(x, y, w, h);

        fapSound = new SoundPlayer(new File("Assets/fap.wav"));
        fallSound = new SoundPlayer(new File("Assets/fall.wav"));
        getpointSound = new SoundPlayer(new File("Assets/getpoint.wav"));
    }

    public void setLive(boolean b) {
        isLive = b;
    }

    public boolean getLive() {
        return isLive;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setVt(float vt) {
        this.vt = vt;
    }

    public void update(long deltaTime) {

        vt += FlappyBird.g;
        this.setPosY(this.getPosY() + vt);
        /*van toc roi theo gia toc*/
        this.rect.setLocation((int) this.getPosX(), (int) this.getPosY());/*cap nhat lien tuc doi tuong bird.getRect*/

 /*kiem tra chim bay len/roi xuong*/
        if (vt < 0) {
            isFlying = true;
        } else {
            isFlying = false;
        }

    }

    public void fly() {
        vt = -6;/*bay len*/
        fapSound.play();
    }

    public boolean getIsFlying() {
        return isFlying;
    }

}
