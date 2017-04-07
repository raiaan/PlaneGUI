package com.example.ryaan.plangui;

import android.content.Context;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    double pitch,roll,throttle,yaw;
    UsbDevice device;
    UsbDeviceConnection connection;
    Button start;
    RelativeLayout relativeLayout,throttleContainer, yawContainer,rollAndPitch;
    ImageView img;
    double val;
    View throttleContent,yawContent;
    TextView yawValue,throttleValue,pitchTxt,rollTxt;
    int x;
    int y;
    @Override
    protected void onStart() {
        super.onStart();
        pitch=1500;
        roll=1500;
        throttle=1000;
        yaw=1000;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        throttleContent =(View)findViewById(R.id.throttleContent);
        yawContent=(View)findViewById(R.id.yawContent);
        yawContainer =(RelativeLayout)findViewById(R.id.YawContainer);
        img=(ImageView)findViewById(R.id.point);
        yawValue=(TextView)findViewById(R.id.yawValue);
        throttleValue=(TextView)findViewById(R.id.throttleText);
        pitchTxt=(TextView)findViewById(R.id.pitch);
        rollTxt=(TextView)findViewById(R.id.roll);

        throttleContainer=(RelativeLayout)findViewById(R.id.throttleContainer);


        yawContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                val=1000+Math.round(1000*(event.getX()/yawContainer.getMeasuredWidth()));
                if(val>=1000&&val<=2000)
                {
                    yawContent.setX((int)event.getX());
                    yawValue.setText("Yaw"+"\n"+(int)val);
                    yaw=val;
                    serial();
                }
                return true;
            }
        });
        throttleContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                val=2000-Math.round(1000*(event.getY()/throttleContainer.getMeasuredHeight()));
                if(val>=1000&&val<=2000)
                {
                    throttleContent.setY((int)event.getY());
                    throttleValue.setText("Throttle\n"+(int)val);
                    throttle=val;
                    serial();

                }

                return true;
            }
        });
        relativeLayout=(RelativeLayout)findViewById(R.id.rollAndPitch);
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x= relativeLayout.getWidth();
                y= relativeLayout.getHeight();
                roll=1000+Math.floor(1000*event.getX()/x);
                pitch=2000-Math.floor(1000*event.getY()/y);
                Boolean atBoundary=roll>=1000&&roll<=2000
                        &&pitch>=1000&&pitch<=2000;
                if(atBoundary)
                {
                    img.setX(event.getX());
                    img.setY(event.getY());
                    rollTxt.setText("Roll\n"+(int)roll);
                    pitchTxt.setText("Pitch\n"+(int)pitch);
                    serial();
                }
                return true;
            }
        });

        start=(Button)findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setX(relativeLayout.getPivotX()-20);
                img.setY(relativeLayout.getPivotY()-20);
                rollTxt.setText("Roll\n1500");
                pitchTxt.setText("Pitch\n1500");
                start.setBackgroundColor(Color.GRAY);
                start.setTextColor(Color.RED);
                throttleValue.setText("Throttle\n1000");
                yawValue.setText("Yaw\n1000");
                start.setText("defualt values");
                throttle=1000;
                yaw=1000;
                roll=1500;
                pitch=1500;
                throttleContent.setY(throttleContainer.getHeight()-10);
                yawContent.setX(yawContainer.getLeft());
//                start.setEnabled(false);
                serial();
            }
        });
    }
    public void serial(){
        // This snippet will open the first usb device connected, excluding usb root hubs
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        if(!usbDevices.isEmpty())
        {
            boolean keep = true;
            for(Map.Entry<String, UsbDevice> entry : usbDevices.entrySet())
            {
                device = entry.getValue();
                int deviceVID = device.getVendorId();
                int devicePID = device.getProductId();
                if(deviceVID != 0x1d6b || (devicePID != 0x0001 || devicePID != 0x0002 || devicePID != 0x0003))
                {
                    // We are supposing here there is only one device connected and it is our serial device
                    connection = usbManager.openDevice(device);
                    // A callback for received data must be defined
                    UsbSerialDevice serialPort = UsbSerialDevice.createUsbSerialDevice(device, connection);
                    if(serialPort != null)
                    {
                        if(serialPort.open())
                        {
                            serialPort.setBaudRate(115200);
                            serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                            serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                            serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                            serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                            String data= pitch+","+roll+","+yaw+","+throttle;
                            Log.v("pitch,roll,yaw,throttle",data);
                            serialPort.write(data.getBytes());
                            Log.v("ex","done :)");
                        }else
                        {
                            // Serial port could not be opened, maybe an I/O error or it CDC driver was chosen it does not really fit
                            Log.v("ex","Serial port could not be opened, maybe an I/O error or it CDC driver was chosen it does not really fit");
                        }
                    }else
                    {
                        // No driver for given device, even generic CDC driver could not be loaded
                        Log.v("ex","No driver for given device, even generic CDC driver could not be loaded");
                    }
                    keep = false;
                }else
                {
                    connection = null;
                    device = null;
                }

                if(!keep)
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
