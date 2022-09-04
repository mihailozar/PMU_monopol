package com.example.pmu_monopol.gameplay;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by new on 20-02-2016.
 */
public class Player {

    public Bitmap img ;
    private GamePlayLayout game;
    public  int positionX;
    public  int positionY;
    private TextView textview;
    private int x;
    private int y;
    public  int playerPos;
    public int money;
    public String name;
    public int score;
    public Bitmap signPlayer;
    public boolean increment=false;

    public Player(GamePlayLayout game, Bitmap player1, String name, Bitmap sign){
        this.game = game;
        this.img = player1;
        positionX = GamePlayLayout.ax;
        positionY = GamePlayLayout.ay;
        x=0;
        y=0;
        playerPos = 0;
        this.name=name;
        money=1000;
        score=0;
        signPlayer=sign;

    }

    private void update(){
        if(increment==true){
            playerPos  = playerPos + 1;
        }

        if(playerPos > 35){
            playerPos = 0;
            money = money + 200;
        }
        if(GamePlayLayout.z == GamePlayLayout.dicenumber) {
            String text = GamePlayLayout.company[GamePlayLayout.z>playerPos?GamePlayLayout.z-playerPos:playerPos-GamePlayLayout.z]+" to ";
            if(playerPos == 27){
                playerPos = 9;
            }
            text += GamePlayLayout.company[playerPos];
            if(GamePlayLayout.company[playerPos].equals("CHANCE") || GamePlayLayout.company[playerPos].equals("Monopoly-Gift")){
                Random r = new Random();
                int z= r.nextInt()%2;
                int x = (r.nextInt()%30);
                x=x<0?-x:x;
                x=x+20;
                if(z==0){
                    text = "You Get $"+x;
                    money += x;
                    score+= x;
                }
                else{
                    text = "You Pay Tax $"+x;
                    money -= x;
                }
            }
            GamePlayLayout.status.setText(text);
            if(GamePlayLayout.company_owner[playerPos].equals("Y")){
                //do nothing
            }
            else if(GamePlayLayout.company_owner[playerPos].equals("Computer")){
                GamePlayLayout.Buy.setVisibility(View.INVISIBLE);
            }
            else{
                GamePlayLayout.Buy.setVisibility(View.INVISIBLE);
                GamePlayLayout.pay_Rent.setText("OK");
            }
            GamePlayLayout.myDialog.show();


        }
    }
    public void onDraw(Canvas canvas){
        if(GamePlayLayout.dicenumber != 0)
            update();
            positionX = (GamePlayLayout.positionXArray[playerPos]);
            positionY = (GamePlayLayout.positionYArray[playerPos]);
            canvas.drawBitmap(img, (GamePlayLayout.positionXArray[playerPos]), (GamePlayLayout.positionYArray[playerPos]), null);

    }

    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
}
