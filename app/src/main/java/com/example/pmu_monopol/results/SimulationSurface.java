package com.example.pmu_monopol.results;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;

import com.example.pmu_monopol.R;
import com.example.pmu_monopol.data.GameStep;
import com.example.pmu_monopol.data.MyData;
import com.example.pmu_monopol.gameplay.DataViewModel;
import com.example.pmu_monopol.gameplay.GamePlayFragmentDirections;
import com.example.pmu_monopol.gameplay.GamePlayLayout;
import com.example.pmu_monopol.gameplay.GameStepViewModle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dagger.hilt.android.AndroidEntryPoint;


public class SimulationSurface<privtae> extends SurfaceView {

    private DataViewModel dataViewModel;
    private GameStepViewModle gameStepViewModle;
    private MyData gameData=null;
    private static ArrayList<GameStep> steps=new ArrayList<>();
    private SurfaceHolder holder;
    private ExecutorService executorService;
    private boolean finished=false;
    private int count;
    private Future<Boolean> future;
    private  int idGame;
    WindowManager wm=(WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
    Display display=wm.getDefaultDisplay();
    private Bitmap background;
    public int[] colors ={Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED};
    int resouce[]={R.drawable.outline_casino_white_24,R.drawable.outline_sports_handball_white_24,R.drawable.player_3_24,R.drawable.outline_sports_mma_white_24};
    int signs[]={R.drawable.outline_casino_white_18,R.drawable.outline_sports_handball_white_18,R.drawable.player_3_18,R.drawable.outline_sports_mma_white_18};
    int  positionYArray[];
    int positionXArray[];
    ArrayList<Bitmap>icons=new ArrayList<>();
    boolean init=true;
    int positions[];
    GameStep currentGameStep;
    int pomPos=0;
    public NavController navController;

    public SimulationSurface(Context context,DataViewModel game, GameStepViewModle stepsVM, int idGame) {
        super(context);
        dataViewModel=game;
        gameStepViewModle=stepsVM;
        this.idGame=idGame;
        holder = getHolder();

        holder.addCallback(new SurfaceHolder.Callback() {
            @SuppressLint("WrongCall")
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                executorService= Executors.newFixedThreadPool(4);
                future=executorService.submit(()->{
                    gameData=dataViewModel.getGameById(idGame);

                    List<GameStep> steps1=gameStepViewModle.getStepsByIdGame((int) gameData.getIdGame());
                    for (GameStep step:steps1
                         ) {
                        SimulationSurface.steps.add(step);

                    }
                    currentGameStep= steps.get(0);
                    pomPos=currentGameStep.getNumberOfSteps();
                    int index=0;
                    String nameArrey[]=gameData.getNamePlayers().split(",");
                    positions=new int[nameArrey.length];
                    for (String name:nameArrey) {

                        icons.add(BitmapFactory.decodeResource(getResources(),resouce[index]));
                        positions[index]=0;
                        index++;

                    }
                    return true;
                });
                executorService.submit(()->{
                    try {
                        future.get();
                        while (!finished){
                            Canvas c = null;

                            Canvas canvas = holder.lockCanvas(null);
                            onDraw(canvas);
                            holder.unlockCanvasAndPost(canvas);

                            Thread.sleep(100);

                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                });
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                executorService.shutdown();
            }
        });
        int w=display.getHeight();
        int h=display.getWidth();
        background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg), (h / 12) * 10, w, true);

        int ax = ( ((h / 12) * 10)/12)*11;
        int ay = (w/12)*10;

        int diffX = ( ((h / 12) * 10)/12);
        int diffY = w/12;
        positionXArray = new int[36];

        positionXArray[0] = ax;
        positionXArray[1] = ax- (2*diffX);
        positionXArray[2] = ax- (3*diffX);
        positionXArray[3] = ax- (4*diffX);
        positionXArray[4] = ax- (5*diffX);
        positionXArray[5] = ax- (6*diffX);
        positionXArray[6] = ax- (7*diffX);
        positionXArray[7] = ax- (8*diffX);
        positionXArray[8] = ax- (9*diffX);
        positionXArray[9] = 0;
        positionXArray[10] = 0;
        positionXArray[11] = 0;
        positionXArray[12] = 0;
        positionXArray[13] = 0;
        positionXArray[14] = 0;
        positionXArray[15] = 0;
        positionXArray[16] = 0;
        positionXArray[17] = 0;
        positionXArray[18] = 0;
        positionXArray[19] = 2*diffX;
        positionXArray[20] = 3*diffX;
        positionXArray[21] = 4*diffX;
        positionXArray[22] = 5*diffX;
        positionXArray[23] = 6*diffX;
        positionXArray[24] = 7*diffX;
        positionXArray[25] = 8*diffX;
        positionXArray[26] = 9*diffX;
        positionXArray[27] = 11*diffX;
        positionXArray[28] = 11*diffX;
        positionXArray[29] = 11*diffX;
        positionXArray[30] = 11*diffX;
        positionXArray[31] = 11*diffX;
        positionXArray[32] = 11*diffX;
        positionXArray[33] = 11*diffX;
        positionXArray[34] = 11*diffX;
        positionXArray[35] = 11*diffX;


        //positon Y Array
        positionYArray = new int[36];

        positionYArray[0] = ay;
        positionYArray[1] = ay;
        positionYArray[2] = ay;
        positionYArray[3] = ay;
        positionYArray[4] = ay;
        positionYArray[5] = ay;
        positionYArray[6] = ay;
        positionYArray[7] = ay;
        positionYArray[8] = ay;
        positionYArray[9] = ay;
        positionYArray[10] = ay - 1*diffY;
        positionYArray[11] = ay - 2*diffY;
        positionYArray[12] = ay - 3*diffY;
        positionYArray[13] = ay - 4*diffY;
        positionYArray[14] = ay - 5*diffY;
        positionYArray[15] = ay - 6*diffY;
        positionYArray[16] = ay - 7*diffY;
        positionYArray[17] = ay - 8*diffY;
        positionYArray[18] = 0;
        positionYArray[19] = 0;
        positionYArray[20] = 0;
        positionYArray[21] = 0;
        positionYArray[22] = 0;
        positionYArray[23] = 0;
        positionYArray[24] = 0;
        positionYArray[25] = 0;
        positionYArray[26] = 0;
        positionYArray[27] = 0;
        positionYArray[28] = diffY;
        positionYArray[29] = 3*diffY;
        positionYArray[30] = 4*diffY;
        positionYArray[31] = 5*diffY;
        positionYArray[32] = 6*diffY;
        positionYArray[33] = 7*diffY;
        positionYArray[34] = 8*diffY;
        positionYArray[35] = 9*diffY;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        int x = (display.getWidth()/2);



        canvas.drawBitmap(background,(0),(0),null);
//        canvas.drawBitmap(icon1,(display.getWidth()/12)*10,0,null);
//        canvas.drawBitmap(icon2, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 10, null);
//        canvas.drawBitmap(diceroll, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 4, null);
//        canvas.drawBitmap(diceroll, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 6, null);

        if(init){
            int index=0;
            for (int pos:positions) {
                canvas.drawBitmap(icons.get(index), (this.positionXArray[pos]), (this.positionYArray[pos]), null);
                index++;
            }
            init=false;
        }else{

            if(positions[count]==pomPos){

                count=++count % icons.size();
                if (steps.size()>1){
                    steps.remove(0);
                    currentGameStep=steps.get(0);
                    pomPos=(positions[count]+currentGameStep.getNumberOfSteps())%36;
                }else{

                    Handler uiThreadHandler = new Handler(Looper.getMainLooper());
                    uiThreadHandler.post(()->{
                        Toast.makeText(getContext().getApplicationContext(), "Gotova simulacija, pobedio je "+gameData.getWinner(),Toast.LENGTH_SHORT).show();
                        NavDirections action= SimulationFragmentDirections.actionSimulationFragmentPop();
                        navController.navigate(action);
                    });

                    finished=true;
                }
            }else{
                positions[count]=++positions[count] %36;
            }

            int index=0;
            for (int pos:positions) {
                canvas.drawBitmap(icons.get(index), (this.positionXArray[pos]), (this.positionYArray[pos]), null);
                index++;
            }

        }


    }


}
