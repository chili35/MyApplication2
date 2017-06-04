package com.bignerdranch.android.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Ye Liuqing on 2017/5/30.
 */

public class MyOrientationListener implements SensorEventListener {
    private SensorManager mSensorManager;
    private Context mContext;
    private Sensor mSensor;

    //X轴
    private float lastX;

    public MyOrientationListener(Context context){
        this.mContext = context;
    }

    //开始监听和结束监听
    public void start(){
        //开始的时候先拿到传感器服务
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager!=null){
            //获得方向传感器
            mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        if(mSensor!=null){
            mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_UI);
        }
    }
    public void stop(){
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg,int arg1){

    }
    @SuppressWarnings({"deprecation"})
    @Override//方向发生变化
    public void onSensorChanged(SensorEvent e){
        if(e.sensor.getType()==Sensor.TYPE_ORIENTATION){
            float x = e.values[SensorManager.DATA_X];

            if(Math.abs(x-lastX)>1.0){
                if(mOnOrientationListener!=null){
                    mOnOrientationListener.OnOrientationChanged(x);
                }
            }
            lastX = x;
        }
    }

    private OnOrientationListener mOnOrientationListener;

    public void setOnOrientationListener(OnOrientationListener onOrientationListener) {
        mOnOrientationListener = onOrientationListener;
    }

    public interface OnOrientationListener{
        void OnOrientationChanged(float x);
    }
}
