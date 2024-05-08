package com.example.hw3;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
    private int count=0;
    private boolean quit=false;
    private MyServiceBinder myServiceBinder=new MyServiceBinder();
    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return myServiceBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(() -> {
            while (!quit) {
                try {
                    Thread.sleep(1000);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    class MyServiceBinder extends Binder {
        public int getCount() {
            return count;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        quit=true;
    }
}
