package com.example.sergeyapp;

import static android.content.Context.WIFI_SERVICE;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;



public class MainActivity extends AppCompatActivity
{
    static String logTag = "logTag";
    InetAddress receiverAddress;
    DatagramSocket datagramSocket;
    DatagramPacket packet1;
    Button button1 = null;
    private static void initializeLocalSocket(Context context)
    {

        Thread thread1 = new Thread()
        {
            @Override
            public void run()

            {
                String logTag = "logTag";
                DatagramPacket packet2;
                DatagramSocket localSocket = null;
                InetAddress serverIp = null;

                byte[] buffer = {10, 23, 12, 31, 43, 32, 24};
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
                WifiManager wf = (WifiManager) context.getSystemService(WIFI_SERVICE);
                DhcpInfo dhcp = wf.getDhcpInfo();
                try
                {
                    serverIp = InetAddress.getByAddress(Formatter.formatIpAddress(dhcp.gateway).getBytes());
                } catch (UnknownHostException e)
                {
                    Log.d(logTag, "here1");
                }
                packet2 = new DatagramPacket(
                        buffer, buffer.length, serverIp, 9000);

                try
                {
                    localSocket = new DatagramSocket();
                } catch (SocketException e)
                {
                    Log.d(logTag, "here2");
                }

                try
                {
                    localSocket.setSoTimeout(2000);
                } catch (SocketException e)
                {
                    Log.d(logTag, "here3");
                }
                try
                {
                    localSocket.connect(new InetSocketAddress(serverIp, 9000));
                } catch (SocketException e)
                {
                    Log.d(logTag, "here4");
                }
                try
                {
                    localSocket.send(packet2);
                } catch (IOException e)
                {
                    Log.d(logTag, "here5");
                }


            }

        };
        thread1.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button);
        Context a = getApplicationContext();
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                initializeLocalSocket(a);
            }
        });
       /* ConnectivityManager cm;
        WifiManager wf;


        byte[] buffer = {10, 23, 12, 31, 43, 32, 24};
        byte[] IP = {(byte) 192, (byte) 168, 1, 106};


        try
        {
            receiverAddress = InetAddress.getByAddress(IP);
        } catch (UnknownHostException e)
        {
            throw new RuntimeException(e);
        }
        packet1 = new DatagramPacket(
                buffer, buffer.length, receiverAddress, 9000);
        try
        {
            datagramSocket = new DatagramSocket();
        } catch (SocketException e)
        {
            throw new RuntimeException(e);
        }
        try
        {
            datagramSocket.send(packet1);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }*/


    }


}





