package com.example.pmu_monopol.gameplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by new on 28-02-2016.
 */
public class Manager {

    private GamePlayLayout game;
    private Bitmap rolling;
    private Bitmap finish;

    public Manager(){

    }
    public Manager(GamePlayLayout game, Bitmap roll, Bitmap fin){
        this.game = game;
        rolling = roll;
        finish = fin;
    }


    public void onDraw(Canvas canvas){
            //canvas.drawBitmap(GamePlay.playersign, 100, 100, null);
    }
}
