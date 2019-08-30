package com.example.ejemplousoacelerometro;


import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import static android.hardware.SensorManager.SENSOR_DELAY_NORMAL;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    int contador = 0;

    private ImageView imagen1;
    private ImageView imagen2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagen1 = findViewById(R.id.foto1);
        imagen2 = findViewById(R.id.foto2);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (sensor == null){
            finish();
        }

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float inclinacion = sensorEvent.values[0];

                if(inclinacion<-5 && contador == 0){
                    contador++;
                    getWindow().getDecorView().setBackgroundColor(Color.rgb(15, 10, 255));
                    imagen1.setVisibility(View.INVISIBLE);
                    imagen2.setVisibility(View.VISIBLE);
                }else if(inclinacion > 5 && contador ==1){
                    contador++;
                    getWindow().getDecorView().setBackgroundColor(Color.rgb(15, 255, 250));
                    imagen1.setVisibility(View.VISIBLE);
                    imagen2.setVisibility(View.INVISIBLE);
                }else if(inclinacion == 0){
                    getWindow().getDecorView().setBackgroundColor(Color.rgb(0, 0, 0));
                    imagen1.setVisibility(View.VISIBLE);
                    imagen2.setVisibility(View.VISIBLE);
                }

                if( contador == 2 ){
                    contador = 0;
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
//                No se requiere utilizar
            }
        };
        start();
    }

    private void start(){
        sensorManager.registerListener(sensorEventListener, sensor, SENSOR_DELAY_NORMAL);
    }

    private void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }
}
