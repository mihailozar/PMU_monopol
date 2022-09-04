package com.example.pmu_monopol.gameplay;

import android.content.Context;
import android.graphics.ImageDecoder;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.pmu_monopol.MainActivity;
import com.example.pmu_monopol.MainMenu.NumberPlayersViewModel;

import java.util.Objects;

public class Shake {
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private MainActivity mainActivity;
    private ShakeViewModel shakeViewModel;
    private boolean flag=false;
    private boolean enable;


    public void  registerSensore(MainActivity mainActivity){
        this.mainActivity=mainActivity;

        ViewModelProvider modelProvider=new ViewModelProvider(mainActivity);
        shakeViewModel = modelProvider.get(ShakeViewModel.class);

        mSensorManager = (SensorManager) mainActivity.getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        enable=true;
    }
    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(enable==true){
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                mAccelLast = mAccelCurrent;
                mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
                float delta = mAccelCurrent - mAccelLast;
                mAccel = mAccel * 0.9f + delta;


                if (mAccel > 12 ) {
                    flag=true;

                }
                if (flag==true && Math.abs(delta)<0.1 ){
                    Toast.makeText(mainActivity.getApplicationContext(), "Shake event detected", Toast.LENGTH_SHORT).show();
                    flag=false;
                    mAccel=10;
                    shakeViewModel.setShaked("shaked");
                }
            }

        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public void unregisterSensore(){
        enable=false;
        Objects.requireNonNull(mSensorManager).unregisterListener(mSensorListener);
        shakeViewModel.setShaked("");
    }
}
