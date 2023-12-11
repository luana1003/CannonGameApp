package com.example.cannongameapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Explosao {
    Bitmap explosao[] = new Bitmap[9];
    int explosaoFrame;
    int eX, eY;

    public Explosao(Context context, int eX, int eY){
        explosao[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion0);
        explosao[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion1);
        explosao[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion2);
        explosao[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion3);
        explosao[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion4);
        explosao[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion5);
        explosao[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion6);
        explosao[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion7);
        explosao[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion8);
        explosaoFrame = 0;
        this.eX = eX;
        this.eY = eY;
    }

    public Bitmap getExplosao(int explosaoFrame){
        return explosao[explosaoFrame];
    }
}
