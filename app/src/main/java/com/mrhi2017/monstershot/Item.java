package com.mrhi2017.monstershot;

import android.graphics.Bitmap;

import java.util.Random;

/**
 * Created by alfo06-19 on 2017-06-29.
 */

public class Item {

    public static final int COIN=0,GEM=1,FAST=2,PROTECT=3,MAGNET=4,BOMB=5,STRONG=6;

    int Width, Height;
    Bitmap img;
    int x, y;
    int w, h;
    boolean isDead = false;
    int dx, dy;
    int kind;

    int lifeTime = 60 * 9;

    Random rnd = new Random();

    public Item(int width, int height, Bitmap[] Items, int x, int y) {
        Width = width;
        Height = height;
        this.x = x;
        this.y = y;

        //1.coin  2.gem  3.fast  4.protect 5.bomb  6.strong
        int n = rnd.nextInt(100);
        kind = n < 70 ? 0 : n < 71 ? 1 : n < 79 ? 2 : n < 84 ? 3 : n < 92 ? 4 : n < 95 ? 5 : 6;

        img = Items[kind];
        w = img.getWidth() / 2;
        h = img.getHeight() / 2;

        int k;
        k = rnd.nextBoolean() ? 1 : -1;
        dx = (w / 6) * k;

        k = rnd.nextBoolean() ? 1 : -1;
        dy = (w / 6) * k;

    }

    void move(int chx,int chy){

        //이 아이템이 플레이어를 바라보는 각도!
        double radian=Math.atan2(y-chy,chx-x);
        x=(int)(x+Math.cos(radian)*(w/2));
        y=(int)(y-Math.sin(radian)*(w/2));


    }

    void move() {

        x += dx;
        y += dy;

        lifeTime--;
        if(lifeTime>0){
            if (x <= w) {
                dx = -dx;
                x = w;
            }
            if (x >= Width - w) {
                dx = -dx;
                x = Width - w;
            }
            if (y <= h) {
                dy = -dy;
                y = h;
            }
            if (y >= Height - h) {
                dy = -dy;
                y = Height - h;
            }
        }else{
            if(x<-w||x>Width+w||y<-h||y>Height+h) isDead=true;
        }





    }
}
