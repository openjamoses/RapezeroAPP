package com.example.john.rapezeroapp.services;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.john.rapezeroapp.activities.ConcentActivity;
import com.example.john.rapezeroapp.core.SharedPreference;
import com.example.john.rapezeroapp.util.DateTime;
import com.google.android.gms.location.LocationListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * Created by john on 2/4/18.
 */

public class Bluetooth_Service extends Service{

    private Context context =  this;
    Handler bluetoothIn;
    private Timer timer = new Timer();

    final int handlerState = 0;                        //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();
    static Handler mainHandler = new Handler(Looper.getMainLooper());

    private ConnectedThread mConnectedThread;
    private SharedPreference sharedPreferenceObj; // Declare Global
    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static String address;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBTState();
        String address = intent.getStringExtra("address");
        Toast.makeText(context, address,Toast.LENGTH_SHORT).show();
        startBluetooth(address);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                startHandler();
            }
        }, 0, 1000);//1 minutes

        return START_STICKY;
    }

    private void startBluetooth(String address){
        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);
        Log.e("Service","Service Started with bluetooth connected at address: "+address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");
    }
    private void startHandler(){
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {                                     //if message is what we want
                    String readMessage = (String) msg.obj;
                    // msg.arg1 = bytes from connect thread
                    if (readMessage.contains("i")){
                        showDialog();
                    }
                    Log.e("Bluetooth_Service"," Recieved Data: "+readMessage);
                    recDataString.append(readMessage);                                      //keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                        //txtString.setText("Data Received = " + dataInPrint);
                        int dataLength = dataInPrint.length();                          //get length of data received
                        //txtStringLength.setText("String Length = " + String.valueOf(dataLength));

                        Log.e("Bluetooth_Service", "Recieved Value: "+recDataString);
                        if (recDataString.charAt(0) == '#')                             //if it starts with # we know it is what we are looking for
                        {
                            //String sensor0 = recDataString.substring(1, 5);             //get sensor value from string between indices 1-5
                            //String sensor1 = recDataString.substring(6, 10);            //same again...
                            //String sensor2 = recDataString.substring(11, 15);
                            //String sensor3 = recDataString.substring(16, 20);
                            String data_recieved = recDataString.substring(1,recDataString.length()-1);

                            showDialog();

                            //sensorView0.setText(" Sensor 0 Voltage = " + sensor0 + "V");    //update the textviews with sensor values
                            //sensorView1.setText(" Sensor 1 Voltage = " + sensor1 + "V");
                            //sensorView2.setText(" Sensor 2 Voltage = " + sensor2 + "V");
                            //sensorView3.setText(" Sensor 3 Voltage = " + sensor3 + "V");
                        }
                        recDataString.delete(0, recDataString.length());                    //clear all string data
                        // strIncom =" ";
                        dataInPrint = " ";
                    }
                }
            }
        };
        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();
            }
        };
        mainHandler.post(myRunnable);
    }
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    private void showDialog(){
        try{//
            sharedPreferenceObj=new SharedPreference(context);
           if (sharedPreferenceObj.getApp_runFirst() == false){
                Intent dialogIntent = new Intent(this, ConcentActivity.class);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dialogIntent);

                sharedPreferenceObj.setApp_runFirst(true,DateTime.getCurrentTime());
            }else {
                String time = sharedPreferenceObj.getApp_Time();
                String time2 = DateTime.getCurrentTime();
                Long dif = Long.parseLong(time2.split(":")[1]) - Long.parseLong(time.split(":")[1]);

                Log.e("Bluetooth", "Time: "+time+"\t"+time2);
                Log.e("Bluetooth", "Time_Diff: "+dif);
                if (dif != 0){
                    Intent dialogIntent = new Intent(this, ConcentActivity.class);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(dialogIntent);
                    sharedPreferenceObj.setApp_runFirst(true,DateTime.getCurrentTime());
                    Log.e("Bluetooth", "Time_Diff: "+dif);
                }else {
                    Log.e("Bluetooth", "Time_Diff: "+dif);
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //startActivityForResult(enableBtIntent, 1);
            }
        }
    }
    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                //finish();

            }
        }
    }
}