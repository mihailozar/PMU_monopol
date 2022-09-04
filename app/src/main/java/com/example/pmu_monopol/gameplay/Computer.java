package com.example.pmu_monopol.gameplay;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by new on 26-02-2016.
 */
public class Computer {
    private Bitmap player2 ;
    private GamePlayLayout game;
    public static int posX;
    public static int posY;
    private TextView textview;
    private int x;
    private int y;
    public static int playerPos;
    public int money;
    public int score;



    public Computer(GamePlayLayout game, Bitmap player1){
        this.game = game;
        this.player2 = player1;
        posX = GamePlayLayout.ax;     //GamePlay.posarray[0][0];
        posY = GamePlayLayout.ay;     /// GamePlay.posarray[0][1];
        x=0;
        y=0;
        playerPos = 0;
        money=1500;
        score=0;
    }

    private void update(){

        playerPos  = playerPos + 1;
        if(playerPos > 35){
            playerPos = 0;
           money = money + 200;
        }

        if(GamePlayLayout.z == GamePlayLayout.dicenumber){
            if(playerPos == 27){
                playerPos = 9;
            }
            if(GamePlayLayout.company[Computer.playerPos].equals("CHANCE") || GamePlayLayout.company[Computer.playerPos].equals("Monopoly-Gift")){
                Random r = new Random();
                int z= r.nextInt()%2;
                int y = r.nextInt();
                if(y<0){
                    y=Math.abs(y);
                }
                int x = (y%30);
                x=x+20;
                if(z==0){
                    GamePlayLayout.text = "Vision Get $"+x;
                    money += x;
                    score += x;
                }
                else{
                    GamePlayLayout.text = "Vision Pay Tax $"+x;
                    money-= x;
                }
            }
            if(!GamePlayLayout.company_owner[playerPos].equals("Computer") || !GamePlayLayout.company_owner[playerPos].equals("N")
            || GamePlayLayout.company_owner[playerPos].equals("Y")){
                money = money - GamePlayLayout.rent[playerPos];
                GamePlayLayout.players.get(GamePlayLayout.count).money += GamePlayLayout.rent[playerPos];
            }
            else if(GamePlayLayout.company_owner[playerPos].equals("Y")){
                Random r = new Random();
                int x = r.nextInt();
                if(x%2==0){
                    money = money- GamePlayLayout.money[playerPos];
                    GamePlayLayout.company_owner[playerPos]="Computer";
                    score += GamePlayLayout.money[playerPos];
                }
                else{
                    money = money - GamePlayLayout.rent[playerPos];
                }
            }
            else{
                money = money - GamePlayLayout.money[playerPos];
            }
        }


    }
    public void onDraw(Canvas canvas){
        if(GamePlayLayout.dicenumber != 0)
                update();
        posX = (GamePlayLayout.positionXArray[playerPos]);
        posY = (GamePlayLayout.positionYArray[playerPos]);
        canvas.drawBitmap(player2,(GamePlayLayout.positionXArray[playerPos]),(GamePlayLayout.positionYArray[playerPos]),null);

    }

    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
}
