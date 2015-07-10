package com.example.aizaz.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class MainActivity extends Activity implements SensorEventListener {

    Sensor accelerometer;
    Sensor gyroscope;
    Sensor proximity;
    SensorManager sm;
    TextView txt,txt2,txt3;
    float a1,a2,a3,g1,g2,g3,p1,p2,p3,max=0;
    Button btnForOpeningFile,btnForClosingFile;
    FileOutputStream fOut;
    OutputStreamWriter myOutWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnForOpeningFile= (Button) findViewById(R.id.button);
        btnForClosingFile= (Button) findViewById(R.id.button2);




        sm= (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope=sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        proximity=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        txt= (TextView) findViewById(R.id.text);
        txt2= (TextView) findViewById(R.id.textView);
        txt3= (TextView) findViewById(R.id.textView2);
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,gyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,proximity,SensorManager.SENSOR_DELAY_NORMAL);

        File myFile = new File("/sdcard/sdFile.csv");
        try {
            myFile.createNewFile();
            fOut = new FileOutputStream(myFile);
            myOutWriter =new OutputStreamWriter(fOut);
            myOutWriter.append("AX,AY,AZ,PX,PY,PZ,GX,GY,GZ");//headings of 3 sensors X,Y,Z columns
            myOutWriter.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }







       /* btnForOpeningFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    Toast.makeText(getBaseContext(),
                            "Opened and written",
                            Toast.LENGTH_SHORT).show();


            }
        });*/

        btnForClosingFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        //close the file
                try {
                    myOutWriter.close();

                    fOut.close();
                    Toast.makeText(getBaseContext(),
                            "Closed",
                            Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });



    }

    protected void onResume() {
        super.onResume();
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {




        try {
            values(a1,a2,a3,p1,p2,p3,g1,g2,g3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sensor sensor = event.sensor;
        int type = sensor.getType();

        switch (type) {
            case Sensor.TYPE_ACCELEROMETER:
                txt.setText("acc X: "+event.values[0]+" "+"Y: "+event.values[1]+" "+"Z: "+event.values[2]);
                a1=event.values[0];
                a2=event.values[1];
                a3=event.values[2];


                break;
            case Sensor.TYPE_PROXIMITY:
                txt2.setText("pro X: "+event.values[0]+" "+"Y: "+event.values[1]+" "+"Z: "+event.values[2]);
                p1=event.values[0];
                p2=event.values[1];
                p3=event.values[2];

                break;

            case Sensor.TYPE_GYROSCOPE:
                txt3.setText("gyr X: "+event.values[0]+" "+"Y: "+event.values[1]+" "+"Z: "+event.values[2]);
                g1=event.values[0];
                g2=event.values[1];
                g3=event.values[2];

                break;

            default:
                return;
        }





    }

    public void values(float ax, float ay,float az,float gx,float gy,float gz,float px,float py, float pz ) throws IOException {
//This function is writing all values passed by onSensorChanged(SensorEvent event() into .csv
      myOutWriter.append(ax+",");myOutWriter.append(ay+",");myOutWriter.append(az+",");
        myOutWriter.append(px+",");myOutWriter.append(py+",");myOutWriter.append(pz+",");
        myOutWriter.append(gx+",");myOutWriter.append(gy+",");myOutWriter.append(gz+",");
        myOutWriter.write("\n");


    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
