package com.example.hw3;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnBindService, btnUnbindService, btnGetStatus;
    TextView tvServiceStatus,text1112;
    MyService.MyServiceBinder serviceBinder;
    boolean isServiceBind = false;

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceBinder = (MyService.MyServiceBinder) service;
            isServiceBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isServiceBind = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBindService = findViewById(R.id.btn_main_activity_bind_service);
        btnUnbindService = findViewById(R.id.btn_main_activity_unbind_service);
        btnGetStatus = findViewById(R.id.btn_main_activity_get_status);
        tvServiceStatus = findViewById(R.id.tv_main_activity_service_status);
        text1112 = findViewById(R.id.textView2);
        btnBindService.setOnClickListener(this);
        btnUnbindService.setOnClickListener(this);
        btnGetStatus.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MyService.class);
        int id = v.getId();
        if (id == R.id.btn_main_activity_bind_service) {
            if (!isServiceBind) {
                bindService(intent, conn, Context.BIND_AUTO_CREATE);
            }
        } else if (id == R.id.btn_main_activity_get_status) {
            if (isServiceBind) {
                int count = serviceBinder.getCount();
                tvServiceStatus.setText("服务次数: " + count);
            }
        } else if (id == R.id.btn_main_activity_unbind_service) {
            if (isServiceBind) {
                unbindService(conn);
                isServiceBind = false;
            }
        }
    }
}

