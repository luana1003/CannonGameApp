package com.example.cannongameapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class CanhaoInimigo {
    Context context;
    Bitmap canhaoInimigo; //enemySpaceShip
    int ex, ey;
    int velocidadeInimigo; //enemyVelocity
    Random random;

    public CanhaoInimigo(Context context){
        this.context = context;
        canhaoInimigo = BitmapFactory.decodeResource(context.getResources(), R.drawable.cannon2);
        random = new Random();
        resetCanhaoInimigo(); //resetEnemySpaceShip
    }

    public Bitmap getCanhaoInimigo(){
        return canhaoInimigo; //enemySpaceShip
    }

    int getLarguraCanhaoInimigo(){
        return canhaoInimigo.getWidth(); //enemySpaceShip
    }

    int getAlturaCanhaoInimigo(){
        return canhaoInimigo.getHeight(); //enemySpaceShip
    }
    private void resetCanhaoInimigo() {
        ex = 200 + random.nextInt(400);
        ey = 0;
        velocidadeInimigo = 14 + random.nextInt(10);
    }
}
