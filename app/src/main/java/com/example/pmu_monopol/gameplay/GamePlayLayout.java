package com.example.pmu_monopol.gameplay;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;

import com.example.pmu_monopol.MainActivity;
import com.example.pmu_monopol.MainMenu.NumberPlayersViewModel;
import com.example.pmu_monopol.R;
import com.example.pmu_monopol.data.GameStep;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Created by new on 20-02-2016.
 */

public class GamePlayLayout extends SurfaceView {

    private Bitmap compImg;
    private Bitmap icon1;
    private Bitmap icon2;
    private Bitmap diceroll;
    private Bitmap rolling;
    private Bitmap dice;
    private Bitmap one;
    private Bitmap two;
    private Bitmap three;
    private Bitmap four;
    private Bitmap five;
    private Bitmap six;
    private Bitmap finishturn;
    public Bitmap playersign;
    public Bitmap computersign;

    public static  TextView textview;
    public static  TextView textview2;
    private Bitmap background;
    private SurfaceHolder holder;
    private GameThread gameThread;
    public static ArrayList<Player> players;

    private Computer comp;
    private Manager manager;
    public static int number;
    private LinearLayout layout;

    public static int count;
    public static int dicenumber;
    private static int dicenumber1;
    private static int dicenumber2;
    private  MediaPlayer mp;
    public static int money[];
    public static int rent[];
    public static int positionXArray[];
    public static int positionYArray[];
    public static boolean playercompany[];
    public static boolean computercompany[];
    public static String[] company;
    public static String[] company_owner;
    public static boolean playerturn;
    public static boolean computerturn;
    public static int z;

    public static String text;
    public static int ax,ay;
    public static int diffX;
    public static int diffY;
    public static int maxH;
    public static int maxW;
    public static Dialog myDialog;
    public static Dialog playerDialog;
    public static Dialog computerDialog;
    public static ImageView playerImage;
    public static TextView status;
    public static TextView smscomp;
    public static Button Buy;
    public static Button click;
    public static Button pay_Rent;
    public boolean Hello,xyz;
    public static TextView playerBal;
    public static TextView companyPlayer;
    public static TextView playername;
    WindowManager wm=(WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
    Display display=wm.getDefaultDisplay();
    private String[] names;
    private int gameId;
    private Shake shake=new Shake();
    private MainActivity mainActivity;
    private ExecutorService executorService;
    private ShakeViewModel shakeViewModel;
    private LifecycleOwner lifecycleOwner;
    public int[] colors ={Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED};
    private GameStepViewModle gameStepViewModle;
    private DataViewModel dataViewModel;
    public NavController navController;

    public GamePlayLayout(Context context, int gameId, String gameNames, MainActivity mainActivity, LifecycleOwner lifecycleOwner){
        super(context);
        this.gameId=gameId;
        this.names=gameNames.split(",");
        this.mainActivity=mainActivity;
        ViewModelProvider modelProvider=new ViewModelProvider(this.mainActivity);
        shakeViewModel = modelProvider.get(ShakeViewModel.class);
        this.lifecycleOwner=lifecycleOwner;
        holder = getHolder();
        gameThread = new GameThread(this);

        gameStepViewModle=modelProvider.get(GameStepViewModle.class);
        dataViewModel=modelProvider.get(DataViewModel.class);


        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                executorService= Executors.newFixedThreadPool(4);
                gameThread.setRunning(true);
                try {
                    gameThread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                executorService.shutdown();
            }

        });
        company_owner = new String[36];
        for(int i=0;i<36;i++){
            company_owner[i]="Y";
        }
        company_owner[2]="N";company_owner[12]="N";company_owner[30]="N";company_owner[34]="N";
        company_owner[4]="N";company_owner[6]="N";company_owner[9]="N";company_owner[15]="N";
        company_owner[18]="N";company_owner[21]="N";company_owner[24]="N";company_owner[27]="N";
        company_owner[32]="N";company_owner[0]="N";
        company = new String[36];
        company[0]="Home";company[1]="NERF";company[2]="Rival-Tower-Tax";company[3]="TransFormers";
        company[4]="Monopoly-Gift";company[5]="Spotify";company[6]="CHANCE";company[7]="BeatsAudio";
        company[8]="Tender";company[9]="JAIL";company[10]="JET-BLUE";
        company[11]="EA";company[12]="Electric Company";company[13]="Hasbro";company[14]="Under-Armour";
        company[15]="CHANCE";company[16]="CARNIVAL";company[17]="YAHOO!";company[18]="Free-Parking";
        company[19]="Paramount";company[20]="CHEVROLOT";company[21]="CHANCE";company[22]="Ebay";company[23]="X-Games";
        company[24]="Monopoly-Gift";company[25]="Ducati";company[26]="McDonald's";company[27]="Go-To-JAIL";
        company[28]="intel";company[29]="X-BOX";company[30]="Water-Works";company[31]="Nestle";
        company[32]="CHANCE";company[33]="SAMSUNG";company[34]="Towar-Tax";company[35]="Coca-Cola";



        myDialog = new Dialog(context);
        playerDialog = new Dialog(context);
        computerDialog = new Dialog(context);
        int w=display.getHeight();
        int h=display.getWidth();
        background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg), (h / 12) * 10, w, true);
        this.players=new ArrayList<>();
        int index=0;
        int resouce[]={R.drawable.outline_casino_white_24,R.drawable.outline_sports_handball_white_24,R.drawable.player_3_24,R.drawable.outline_sports_mma_white_24};
        int signs[]={R.drawable.outline_casino_white_18,R.drawable.outline_sports_handball_white_18,R.drawable.player_3_18,R.drawable.outline_sports_mma_white_18};


        if(names.length==1){
            compImg=BitmapFactory.decodeResource(getResources(), R.mipmap.player_2);
        }
        icon1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon1), (h / 12) * 2, (w / 12) * 2, true);
        icon2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon2), (h / 12) * 2, (w / 12) * 2, true);
        diceroll = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dice3droll), (h / 12) * 2, (w / 12) * 2, true);
        one = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.one), (h / 12) * 2, (w / 12) * 2, true);
        two = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.two), (h / 12) * 2, (w/12)*2, true);
        three = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.three), (h / 12) * 2, (w/12)*2, true);
        four = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.four), (h / 12) * 2, (w / 12) * 2, true);
        five = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.five), (h / 12) * 2, (w / 12) * 2, true);
        six = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.six), (h / 12) * 2, (w/12)*2, true);
        rolling = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rolling), (h / 12) * 2,(w / 12) * 4, true);
        finishturn = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.finish), (h / 12) * 2,(w / 12) * 4, true);
        playersign = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.playersign), (h / 40),(w / 40), true);
        computersign = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.index), (h / 40),(w / 40), true);

        ax = ( ((h / 12) * 10)/12)*11;
        ay = (w/12)*10;

        diffX = ( ((h / 12) * 10)/12);
        diffY = w/12;
        maxH = (display.getWidth() / 12) * 10;
        maxW = (display.getHeight() / 12) * 4;

        z=0;

        xyz = true;

       // Log.i(" x position ",Integer.toString(diffX));
        //Log.i(" x position ",Integer.toString(diffY));

        //position array calculating..........
        positionXArray = new int[36];

        positionXArray[0] = GamePlayLayout.ax;
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



        textview = new TextView(getContext());
        textview2 = new TextView(getContext());
        players=new ArrayList<>();

        for (int i=0;i<names.length;i++){
            players.add(new Player(this,BitmapFactory.decodeResource(getResources(),resouce[i]),names[i],BitmapFactory.decodeResource(getResources(),signs[i])));

        }
        if(names.length==1){

            comp = new Computer(this,compImg);
        }

        dicenumber = 0;
        dicenumber1 = 0;
        dicenumber2 = 0;
        playerturn = false;
        computerturn = false;
        manager = new Manager(this,rolling,finishturn);

        textview.setText(" ");
        number =40;


        count=0;

        money = new int[36];
        rent = new int[36];
        // complete the money array
        money[0] = 0;rent[0]=0;
        money[1] = 50;rent[1]=5;
        money[2] = 0;rent[2]=0;
        money[3] = 50;rent[3]=5;
        money[4] = 0;rent[4]=0;
        money[5] = 100;rent[5]=10;
        money[6] = 0;rent[6]=0;
        money[7] = 100;rent[7]=10;
        money[8] = 100;rent[8]=10;
        money[9] = 100;rent[9]=0;
        money[10] = 150;rent[10]=15;
        money[11] = 150;rent[11]=15;
        money[12] = 150;rent[12]=15;
        money[13] = 150;rent[13]=15;
        money[14] = 200;rent[14]=20;
        money[15] = 0;rent[15]=0;
        money[16] = 200;rent[16]=20;
        money[17] = 200;rent[17]=20;
        money[18] = 0;rent[18]=0;
        money[19] = 250;rent[19]=25;
        money[20] = 250;rent[20]=25;
        money[21] = 0;rent[21]=0;
        money[22] = 250;rent[22]=25;
        money[23] = 300;rent[23]=30;
        money[24] = 0;rent[24]=0;
        money[25] = 300;rent[25]=30;
        money[26] = 300;rent[26]=30;
        money[27] = 0;rent[27]=0;
        money[28] = 350;rent[28]=35;
        money[29] = 350;rent[29]=35;
        money[30] = 150;rent[30]=15;
        money[31] = 350;rent[31]=35;
        money[32] = 0;rent[32]=0;
        money[33] = 400;rent[33]=40;
        money[34] = 0;rent[34]=0;
        money[35] = 400;rent[35]=40;
        //complete money array

        myDialog.setContentView(R.layout.dialogfile);
        myDialog.setTitle("Player Turn");
        myDialog.setCanceledOnTouchOutside(true);
        myDialog.setCancelable(false);
        status=(TextView) myDialog.findViewById(R.id.status_id);
        Buy = (Button) myDialog.findViewById(R.id.popButton);
        pay_Rent = (Button) myDialog.findViewById(R.id.pay_Rent);

        //player profile dialog
        playerDialog.setContentView(R.layout.playerprofile);
        playerDialog.setTitle("Player 1");
        playerDialog.setCanceledOnTouchOutside(true);
        playerDialog.setCancelable(true);
        playerBal = (TextView)playerDialog.findViewById(R.id.textView3);
        playername = (TextView)playerDialog.findViewById(R.id.textView);
        companyPlayer = (TextView)playerDialog.findViewById(R.id.textView5);
        playerImage = (ImageView)playerDialog.findViewById(R.id.imageViewPlayer);
        //computer finish Turn Dialog
        computerDialog.setContentView(R.layout.compdialog);
        computerDialog.setTitle("Computer Turn");
        computerDialog.setCanceledOnTouchOutside(true);
        computerDialog.setCancelable(true);
        smscomp = (TextView)computerDialog.findViewById(R.id.sms);
        click = (Button)computerDialog.findViewById(R.id.click);
    }
    @Override
    public void onDraw(Canvas canvas){
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        int x = (display.getWidth()/2);

        canvas.drawBitmap(background,(0),(0),null);
        canvas.drawBitmap(icon1,(display.getWidth()/12)*10,0,null);
        canvas.drawBitmap(icon2, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 10, null);
        canvas.drawBitmap(diceroll, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 4, null);
        canvas.drawBitmap(diceroll, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 6, null);

        for(int i=0;i<36;i++){
            if(i<=10) {
                for (Player player: players
                     ) {
                    if (company_owner[i].equals(player.name)) {
                        canvas.drawBitmap(player.signPlayer, GamePlayLayout.positionXArray[i], GamePlayLayout.positionYArray[i], null);
                    }

                }

//                if (company_owner[i].equals("Computer")) {
//                    canvas.drawBitmap(computersign, GamePlayLayout.positionXArray[i], GamePlayLayout.positionYArray[i], null);
//                }
            }
            else if(i<19){
                for (Player player: players
                ) {
                    if (company_owner[i].equals(player.name)) {
                        canvas.drawBitmap(player.signPlayer, GamePlayLayout.positionXArray[i], GamePlayLayout.positionYArray[i], null);
                    }

                }
//                if (company_owner[i].equals("Computer")) {
//                    canvas.drawBitmap(computersign, GamePlayLayout.positionXArray[i - 1], GamePlayLayout.positionYArray[i - 1], null);
//                }
            }
            else if(i<28){
                for (Player player: players
                ) {
                    if (company_owner[i].equals(player.name)) {
                        canvas.drawBitmap(player.signPlayer, GamePlayLayout.positionXArray[i], GamePlayLayout.positionYArray[i], null);
                    }

                }
//                if (company_owner[i].equals("Computer")) {
//                    canvas.drawBitmap(computersign, GamePlayLayout.positionXArray[i], GamePlayLayout.positionYArray[i], null);
//                }
            }
            else if(i<35){
                for (Player player: players
                ) {
                    if (company_owner[i].equals(player.name)) {
                        canvas.drawBitmap(player.signPlayer, GamePlayLayout.positionXArray[i], GamePlayLayout.positionYArray[i], null);
                    }

                }
//                if (company_owner[i].equals("Computer")) {
//                    canvas.drawBitmap(computersign, GamePlayLayout.positionXArray[i + 1], GamePlayLayout.positionYArray[i + 1], null);
//                }
            }
            else{

            }
        }
        switch(dicenumber1){
            case 0:
                canvas.drawBitmap(diceroll, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 4, null);
                canvas.drawBitmap(diceroll, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 6, null);
                break;
            case 1:
                canvas.drawBitmap(one, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 4, null);
                break;
            case 2:
                canvas.drawBitmap(two, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 4, null);
                break;
            case 3:
                canvas.drawBitmap(three, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 4, null);
                break;
            case 4:
                canvas.drawBitmap(four, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 4, null);
                break;
            case 5:
                canvas.drawBitmap(five, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 4, null);
                break;
            case 6:
                canvas.drawBitmap(six, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 4, null);
                break;

        }
        switch(dicenumber2){
            case 0:
                break;
            case 1:
                canvas.drawBitmap(one, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 6, null);
                break;
            case 2:
                canvas.drawBitmap(two, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 6, null);
                break;
            case 3:
                canvas.drawBitmap(three, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 6, null);
                break;
            case 4:
                canvas.drawBitmap(four, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 6, null);
                break;
            case 5:
                canvas.drawBitmap(five, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 6, null);
                break;
            case 6:
                canvas.drawBitmap(six, (display.getWidth() / 12) * 10, (display.getHeight() / 12) * 6, null);
                break;

        }
        int index=0;
        switch (count){
            case 0:{
                for (Player player: players
                     ) {
                    if (index==count){

                    }else{
                        canvas.drawBitmap(players.get(index).img,(players.get(index).positionX),(players.get(index).positionY),null);
                    }
                    index++;
                }
                if (names.length==1){
                    canvas.drawBitmap(compImg,(Computer.posX),(Computer.posY),null);
                }
                players.get(0).onDraw(canvas);
            }break;
            case 1:{

                if(names.length==1){
                    comp.onDraw(canvas);
                    canvas.drawBitmap(players.get(0).img,(players.get(0).positionX),(players.get(0).positionY),null);

                }else{
                    for (Player player: players
                    ) {
                        if (index==count){

                        }else{
                            canvas.drawBitmap(players.get(index).img,(players.get(index).positionX),(players.get(index).positionY),null);
                        }
                        index++;
                    }
                    players.get(1).onDraw(canvas);
                }
            }break;
            case 2:{
                for (Player player: players
                ) {
                    if (index==count){

                    }else{
                        canvas.drawBitmap(players.get(index).img,(players.get(index).positionX),(players.get(index).positionY),null);
                    }
                    index++;
                }
                players.get(2).onDraw(canvas);
            }break;
            case 3:{
                for (Player player: players
                ) {
                    if (index==count){

                    }else{
                        canvas.drawBitmap(players.get(index).img,(players.get(index).positionX),(players.get(index).positionY),null);
                    }
                    index++;
                }
                players.get(3).onDraw(canvas);
            }break;
        }
//        if(count==0) {
//            canvas.drawBitmap(compImg,(Computer.posX),(Computer.posY),null);
//            players.get(0).onDraw(canvas);
//        }
//        else{
//            canvas.drawBitmap(playersImages.get(0),(Player.positionX),(Player.positionY),null);
//            comp.onDraw(canvas);
//        }



        LinearLayout layout = new LinearLayout(getContext());

        //DisplayMetrics displaymetrics = Resources.getSystem().getDisplayMetrics();
        if(players.size()==1){
            textview.setVisibility(View.VISIBLE);
            textview.setText(" $ " + comp.money);
            textview.setTextColor(Color.BLUE);
            textview.setTextSize(25);

        }

        if(textview.getParent()!=null)
            ((ViewGroup)textview.getParent()).removeView(textview); // <- fix
        layout.addView(textview);
        layout.measure(0, 0);
        layout.layout(0, 0, 0, 0);
        canvas.translate((display.getWidth() / 12) * 10, (display.getHeight() / 12) * 2);
        layout.draw(canvas);

        if(players.size()==1  && count==1){

        }else{
            LinearLayout layout2 = new LinearLayout(getContext());
            textview2.setVisibility(View.VISIBLE);
            //DisplayMetrics displaymetrics = Resources.getSystem().getDisplayMetrics();
            textview2.setText(" $ " + players.get(count).money);
            textview2.setTextColor(colors[count]);
            textview2.setTextSize(25);

            if(textview2.getParent()!=null)
                ((ViewGroup)textview2.getParent()).removeView(textview2); // <- fix
            layout2.addView(textview2);
            layout2.measure(200, 200);
            layout2.layout(100, 100, 200, 200);
            canvas.translate(0, (display.getHeight() / 12) * 7);
            layout2.draw(canvas);
        }



        canvas.translate(-((display.getWidth() / 12) * 10), -(((display.getHeight() / 12) * 7) + ((display.getHeight() / 12) * 2)));
    }


/////////////////////////<!--android:theme="@style/AppTheme" > -->

    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }


    public void play()
    {
        mp=MediaPlayer.create(getContext(), R.raw.shake_dice);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setLooping(true);
        mp.start();

    }
    public void stop()
    {
                mp.stop();
    }


    @SuppressLint("WrongCall")
    protected void checkMovement(float x2,float y2){

        int xRight = (display.getWidth() / 12) * 10;
        int xLeft = display.getWidth();
        int yRight = (display.getHeight() / 12) * 4;
        int yLeft = (display.getHeight() / 12) * 8;
        int y1Right = 0 ;
        int y1Left = (display.getHeight() / 12) * 2;
        int y2Right = (display.getHeight() / 12) * 10;
        int y2Left = (display.getHeight() / 12) * 12;
        //System.out.print("AA"+xRight+"B"+xLeft);
        if(x2>xRight && x2<xLeft  && y2>y1Right && y2<y1Left  ){
//            ************************************
//            kliknuli smo na gornju slicicu
//            ************************************
            playerDialog.setTitle("Computer");
            playerImage.setImageResource(R.drawable.icon1);
            playername.setText("Vision");
            String company_by_player = "";
            for(int i=0;i<36;i++){
                if(company_owner[i].equals("Computer"))
                    company_by_player += company[i]+"\n";
            }
            companyPlayer.setText(company_by_player);
            playerBal.setText("  $ "+Integer.toString(comp.money));
            playerDialog.show();

        }
        if(x2>xRight && x2<xLeft  && y2>y2Right && y2<y2Left  ){
//            ********************************
//            kliknuli smo na nekog pleyera
//            ********************************
            playerDialog.setTitle(players.get(count).name);
            playerImage.setImageResource(R.drawable.icon2);
            playername.setText(players.get(count).name);
            String company_by_player = "";
            for(int i=0;i<36;i++){
                if(company_owner[i].equals(players.get(count).name))
                    company_by_player += company[i]+"\n";
            }
            companyPlayer.setText(company_by_player);
            playerBal.setText("  $ "+Integer.toString(players.get(count).money));
            playerDialog.show();
        }
        if(x2>xRight && x2<xLeft  && y2>yRight && y2<yLeft  ) {
//            *******************************
//            stisnuli smo na kockice
//            ********************************
            shake.registerSensore(mainActivity);
            this.play();

            shakeViewModel.getShaked().observe(lifecycleOwner, str -> {

                if(str=="shaked"){

                        stop();
                        shake.unregisterSensore();
                        dicenumber = 0;
                        Random r = new Random();
                        dicenumber1 = (r.nextInt(6) + 1);
                        dicenumber += dicenumber1;
                        dicenumber2 = (r.nextInt(6) + 1);
                        dicenumber += dicenumber2;
                        z=1;
                        players.get(count).increment=true;
                        while(z<=dicenumber) {

                            Canvas canvas = holder.lockCanvas(null);
                            onDraw(canvas);
                            holder.unlockCanvasAndPost(canvas);

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            z++;
                        }
                        //computerturn = true;
//                        if(players.size()==1){
//                            count = ++count % 2;
//                        }else{
//                            count = ++count % names.length;
//                        }

                        //pay_Rent.setText(company[dicenumber] + " to " + company[dicenumber + 1]);
                        //myDialog.show();
                        check_RentIncrement();
                }

            });


            Buy.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    players.get(count).money=players.get(count).money-GamePlayLayout.money[players.get(count).playerPos];
                    company_owner[players.get(count).playerPos] = players.get(count).name;
                    players.get(count).score += GamePlayLayout.money[players.get(count).playerPos];
                    myDialog.dismiss();

                    GamePlayLayout.Buy.setVisibility(View.VISIBLE);
                    GamePlayLayout.pay_Rent.setText("Rent");
                    if (players.get(count).money < 0) {
                        gameOver();
                    }
                    players.get(count).increment=false;
                    Canvas canvas = holder.lockCanvas(null);
                    onDraw(canvas);
                    holder.unlockCanvasAndPost(canvas);

                    gameStepViewModle.instertData(new GameStep(gameId,
                            players.get(count).name,
                            dicenumber,
                            players.get(count).score));
                    count=++count% players.size();
//                    computerTurn();
//                    count = ++count % 2;
                }
            });
            pay_Rent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                   // System.out.println("HHHHHHHHHHHHHH");
                    if(pay_Rent.getText().equals("Rent")){
                        players.get(count).money=players.get(count).money-GamePlayLayout.rent[players.get(count).playerPos];

                        for (int i=0;i<players.size();i++){
                            if (company_owner[players.get(count).playerPos].equals(players.get(i).name)){
                                players.get(i).money+=GamePlayLayout.rent[players.get(count).playerPos];
                            }
                        }
                    }
                    else{

                        players.get(count).money-=GamePlayLayout.money[players.get(count).playerPos];
                    }
                    myDialog.dismiss();
                    GamePlayLayout.Buy.setVisibility(View.VISIBLE);
                    GamePlayLayout.pay_Rent.setText("Rent");
                    if(players.get(count).money < 0){
                        gameOver();
                    }
                    players.get(count).increment=false;
                    Canvas canvas = holder.lockCanvas(null);
                    onDraw(canvas);
                    holder.unlockCanvasAndPost(canvas);

                    gameStepViewModle.instertData(new GameStep(gameId,
                            players.get(count).name,
                            dicenumber,
                            players.get(count).score));

                    count = ++count % players.size();
//                    computerTurn();
//                    count = ++count % 2;
                }
            });

//            count = ++count % 2;
        }

    }

    public void gameOver(){
        final Dialog exitGame = new Dialog(getContext());

        exitGame.setContentView(R.layout.nameinput);
        Button b = (Button) exitGame.findViewById(R.id.nameButton);
        TextView tv = (TextView)exitGame.findViewById(R.id.textView18);
        String res="";

        int max=0;
        Player pomPlayer=null;
        for (Player player:players) {
            if(max<=player.money){
                max=player.money;
                pomPlayer=player;
            }

        }

        res="Player "+pomPlayer.name+" win";
        executorService = Executors.newFixedThreadPool(1);

        Player finalPomPlayer = pomPlayer;
        executorService.submit(()->{
            dataViewModel.updateGameById(this.gameId, finalPomPlayer.name);
        });

//        if(money1 > money2){
//            res = "You Lose the Game.";
//        }
//        else{
//            res = "You Win the Game.";
//        }
//        res="trba da nadjem ko je pobedio";
        exitGame.setTitle(res);
//        String result = "Vision  "+scoreComputer+"\n"+MainActivity.playerName +"  "+scorePlayer;
        String result="treba popraviti oko scorova";
        tv.setText(res);
        Toast.makeText(getContext(),"Geme Over",Toast.LENGTH_SHORT).show();
        exitGame.setCanceledOnTouchOutside(false);
        exitGame.show();
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                exitGame.dismiss();
                NavDirections action=GamePlayFragmentDirections.actionGamePlayFragmentPop();
                navController.navigate(action);
            }
        });
    }
    @SuppressLint("WrongCall")
    public void computerTurn(){
        dicenumber = 0;
        Random r = new Random();
        dicenumber1 = (r.nextInt(6) + 1);
        dicenumber += dicenumber1;
        dicenumber2 = (r.nextInt(6) + 1);
        dicenumber += dicenumber2;
        text = company[Computer.playerPos];
        z = 1;
        while(z <= dicenumber) {
            Canvas canvas = holder.lockCanvas(null);
            onDraw(canvas);
            holder.unlockCanvasAndPost(canvas);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            z++;
        }
        if(text.contains("Vision")){

        }
        else {
            text += " to " + company[Computer.playerPos];
        }
        //Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show();
        smscomp.setText(text);
        computerDialog.show();
        click.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                computerDialog.dismiss();
                if(comp.money < 0){
                    gameOver();
                }
            }
        });
        check_RentIncrement();
    }
    public void check_RentIncrement(){
        String str1 = company_owner[10];
        String str2 = company_owner[11];
        String str3 = company_owner[13];
        if(str1.equals(str2) && str1.equals(str3) && (str1.equals("Player")|| str1.equals("Computer"))){
            rent[10] = rent[10]*3;
            rent[11] = rent[11]*3;
            rent[13] = rent[13]*3;
          //  Toast.makeText(getContext(),"[10-11-13]Rent Increased for "+company[10]+";",Toast.LENGTH_SHORT).show();
        }
        str1 = company_owner[5];
        str2 = company_owner[7];
        str3 = company_owner[8];
        if(str1.equals(str2) && str1.equals(str3) && (str1.equals("Player")|| str1.equals("Computer"))){
            rent[5] = rent[5]*3;
            rent[7] = rent[7]*3;
            rent[8] = rent[8]*3;
           // Toast.makeText(getContext(),"[5-7-8]HEllo Rent Increased",Toast.LENGTH_SHORT).show();
        }
        str1 = company_owner[14];
        str2 = company_owner[16];
        str3 = company_owner[17];
        if(str1.equals(str2) && str1.equals(str3) && (str1.equals("Player")|| str1.equals("Computer"))){
            rent[14] = rent[14]*3;
            rent[16] = rent[16]*3;
            rent[17] = rent[17]*3;
           // Toast.makeText(getContext(),"[14-16-17]HEllo Rent Increased",Toast.LENGTH_SHORT).show();
        }
        str1 = company_owner[19];
        str2 = company_owner[20];
        str3 = company_owner[22];
        if(str1.equals(str2) && str1.equals(str3) && (str1.equals("Player")|| str1.equals("Computer"))){
            rent[19] = rent[19]*3;
            rent[20] = rent[20]*3;
            rent[22] = rent[22]*3;
          //  Toast.makeText(getContext(),"[19-20-22]HEllo Rent Increased",Toast.LENGTH_SHORT).show();
        }

        str1 = company_owner[23];
        str2 = company_owner[25];
        str3 = company_owner[26];
        if(str1.equals(str2) && str1.equals(str3) && (str1.equals("Player")|| str1.equals("Computer"))){
            rent[23] = rent[23]*3;
            rent[25] = rent[25]*3;
            rent[26] = rent[26]*3;
          //  Toast.makeText(getContext(),"[23-25-26]HEllo Rent Increased",Toast.LENGTH_SHORT).show();
        }

        str1 = company_owner[28];
        str2 = company_owner[29];
        str3 = company_owner[31];
        if(str1.equals(str2) && str1.equals(str3) && (str1.equals("Player")|| str1.equals("Computer"))){
            rent[28] = rent[28]*3;
            rent[29] = rent[29]*3;
            rent[31] = rent[31]*3;
          //  Toast.makeText(getContext(),"[28-29-31]HEllo Rent Increased",Toast.LENGTH_SHORT).show();
        }

        str1 = company_owner[33];
        str2 = company_owner[35];
        //str3 = company_owner[8];
        if(str1.equals(str2) && (str1.equals("Player")|| str1.equals("Computer"))){
            rent[33] = rent[33]*3;
            rent[35] = rent[35]*3;
            //rent[8] = rent[8]*3;
          //  Toast.makeText(getContext(),"[33-35]HEllo Rent Increased",Toast.LENGTH_SHORT).show();
        }
        str1 = company_owner[1];
        str2 = company_owner[3];
        //str3 = company_owner[8];
        if(str1.equals(str2) && (str1.equals("Player")|| str1.equals("Computer"))){
            rent[1] = rent[1]*3;
            rent[3] = rent[3]*3;
            //rent[8] = rent[8]*3;
          //  Toast.makeText(getContext(),"[1-3]HEllo Rent Increased",Toast.LENGTH_SHORT).show();
        }
       // Toast.makeText(getContext(),"Nothing",Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        super.onTouchEvent(event);
        if(event.getAction() == MotionEvent.ACTION_DOWN){

        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(!holder.getSurface().isValid()) {

            }
            else{
                checkMovement(event.getX(), event.getY());
            }
        }
        event.setAction(0);
        return true;
    }
}