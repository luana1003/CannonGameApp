package com.example.cannongameapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class CannonShooter extends View {
    Context context;
    Bitmap background, lifeImage;
    Handler handler;
    long UPDATE_MILIS = 30;
    static int screenWidth, screenHeight;
    int points = 0;
    int life = 3;
    Paint scorePaint;
    int TEXT_SIZE = 60;
    boolean paused = false;
    CanhaoHeroi canhaoHeroi;
    CanhaoInimigo canhaoInimigo;
    Random random;
    ArrayList<Tiro> tirosCanhaoInimigo, tirosCanhaoHeroi; //enemyShots, ourShots;
    boolean explosaoCanhaoInimigo = false; //enemyExplosion
    Explosao explosao;
    ArrayList<Explosao> explosoes;
    boolean acaoTiroCanhaoInimigo = false; //enemyShotAction
    final Runnable runnable = this::invalidate;

    public CannonShooter(Context context){
        super(context);
        this.context = context;
        random = new Random();
        tirosCanhaoInimigo = new ArrayList<>();
        tirosCanhaoHeroi = new ArrayList<>();
        explosoes = new ArrayList<>();
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        canhaoHeroi = new CanhaoHeroi(context);
        canhaoInimigo = new CanhaoInimigo(context);
        background = BitmapFactory.decodeResource(context.getResources(),R.drawable.background);
        lifeImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.life);
        handler = new Handler();
        scorePaint = new Paint();
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(background, 0,0,null);
        canvas.drawText("Pontos " + points, 0, TEXT_SIZE, scorePaint);
        for (int i = life; i >=1; i--){
            canvas.drawBitmap(lifeImage, screenWidth - lifeImage.getWidth()* i, 0, null);
        }
        if (life == 0){
            paused = true;
            handler = null;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("points", points);
            context.startActivity(intent);
            ((Activity) context).finish();
        }

        canhaoInimigo.ex += canhaoInimigo.velocidadeInimigo;
        if(canhaoInimigo.ex + canhaoInimigo.getLarguraCanhaoInimigo() >= screenWidth){
            canhaoInimigo.velocidadeInimigo *= -1;
        }

        if(canhaoInimigo.ex <= 0){
            canhaoInimigo.velocidadeInimigo *= -1;
        }

        if((!acaoTiroCanhaoInimigo) && (canhaoInimigo.ex >= 200 + random.nextInt(400))){
            Tiro tiroCanhaoInimigo = new Tiro(context, canhaoInimigo.ex + canhaoInimigo.getLarguraCanhaoInimigo()/2, canhaoInimigo.ey);
            tirosCanhaoInimigo.add(tiroCanhaoInimigo);
            acaoTiroCanhaoInimigo = true;

            }

        if(!explosaoCanhaoInimigo){
            canvas.drawBitmap(canhaoInimigo.getCanhaoInimigo(), canhaoInimigo.ex, canhaoInimigo.ey, null);
        }

        if (canhaoHeroi.estaVivo){
            if(canhaoHeroi.ox > screenWidth - canhaoHeroi.getLarguraCanhaoHeroi()){
                canhaoHeroi.ox = screenWidth - canhaoHeroi.getLarguraCanhaoHeroi();
            } else if (canhaoHeroi.ox < 0){ //
                canhaoHeroi.ox = 0;
            }
            canvas.drawBitmap(canhaoHeroi.getCanhaoHeroi(), canhaoHeroi.ox, canhaoHeroi.oy, null);
        }

        for (int i = 0; i<tirosCanhaoInimigo.size(); i++){
            tirosCanhaoInimigo.get(i).shy += 15;
            canvas.drawBitmap(tirosCanhaoInimigo.get(i).getTiro(), tirosCanhaoInimigo.get(i).shx, tirosCanhaoInimigo.get(i).shy, null);
            if ((tirosCanhaoInimigo.get(i).shx >= canhaoHeroi.ox) && (tirosCanhaoInimigo.get(i).shx <= canhaoHeroi.ox + canhaoHeroi.getLarguraCanhaoHeroi()) && (tirosCanhaoInimigo.get(i).shy >= canhaoHeroi.oy) && (tirosCanhaoInimigo.get(i).shy <= screenHeight)){
                life --;
                tirosCanhaoInimigo.remove(i);
                explosao = new Explosao(context, canhaoHeroi.ox, canhaoHeroi.oy);
                explosoes.add(explosao);
            }else if(tirosCanhaoInimigo.get(i).shy >= screenHeight){
                tirosCanhaoInimigo.remove(i);
            }

            if(tirosCanhaoInimigo.size() < 1){ // == 0
                acaoTiroCanhaoInimigo = false;
            }
        }

        for (int i = 0; i < tirosCanhaoHeroi.size(); i++){
            tirosCanhaoHeroi.get(i).shy -= 15;
            canvas.drawBitmap(tirosCanhaoHeroi.get(i).getTiro(), tirosCanhaoHeroi.get(i).shx, tirosCanhaoHeroi.get(i).shy, null);
            if((tirosCanhaoHeroi.get(i).shx >= canhaoInimigo.ex)
                    && (tirosCanhaoHeroi.get(i).shx <= canhaoInimigo.ex + canhaoInimigo.getLarguraCanhaoInimigo())
                    &&(tirosCanhaoHeroi.get(i).shy <= canhaoInimigo.getAlturaCanhaoInimigo())
                    && (tirosCanhaoHeroi.get(i).shy >= canhaoInimigo.ey)){
                points++;
                tirosCanhaoHeroi.remove(i);
                explosao = new Explosao(context, canhaoInimigo.ex, canhaoInimigo.ey);
                explosoes.add(explosao);
            }else if(tirosCanhaoHeroi.get(i).shy <=0){
                tirosCanhaoHeroi.remove(i);
            }
        }

        for (int i = 0; i <explosoes.size(); i++){
            canvas.drawBitmap(explosoes.get(i).getExplosao(explosoes.get(i).explosaoFrame), explosoes.get(i).eX, explosoes.get(i).eY, null);
            explosoes.get(i).explosaoFrame++;
            if(explosoes.get(i).explosaoFrame > 8){
                explosoes.remove(i);
            }
        }

        if (!paused){
            handler.postDelayed(runnable, UPDATE_MILIS);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        if(event.getAction() == MotionEvent.ACTION_UP){
            if(tirosCanhaoHeroi.size() < 3){
                Tiro tiroCanhaoHeroi = new Tiro(context, canhaoHeroi.ox + canhaoHeroi.getLarguraCanhaoHeroi()/2, canhaoHeroi.oy);
                tirosCanhaoHeroi.add(tiroCanhaoHeroi);
            }
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            canhaoHeroi.ox = touchX;
        }

        if(event.getAction() == MotionEvent.ACTION_MOVE){
            canhaoHeroi.ox = touchX;
        }

        return true;
    }
}
