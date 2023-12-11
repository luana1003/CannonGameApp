package com.example.cannongameapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class CanhaoHeroi {
    Context context;
    Bitmap canhaoHeroi; //ourSpaceShip
    int ox, oy;
    boolean estaVivo = true;
    int canhaoHeroiVelocidade; //ourVelocity
    Random random;

    public CanhaoHeroi(Context context){
        this.context = context;
        canhaoHeroi = BitmapFactory.decodeResource(context.getResources(), R.drawable.cannon1);
        random = new Random();
        resetCanhaoHeroi();

    }

    public Bitmap getCanhaoHeroi(){
        return canhaoHeroi; //ourSpaceShip
    }

    int getLarguraCanhaoHeroi(){
        return canhaoHeroi.getWidth(); //ourSpaceShip
    }

    int getAlturaCanhaoHeroi(){
        return canhaoHeroi.getHeight(); //ourSpaceShip
    }

    private void resetCanhaoHeroi() {
        ox = random.nextInt(CannonShooter.screenWidth);//SpaceShooter
        oy = CannonShooter.screenHeight - canhaoHeroi.getHeight();//SpaceShooter
        canhaoHeroiVelocidade = 10 + random.nextInt(6);
    }
}
