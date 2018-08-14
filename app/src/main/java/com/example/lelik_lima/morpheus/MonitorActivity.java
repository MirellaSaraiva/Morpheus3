package com.example.lelik_lima.morpheus;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.util.Calendar;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.Toast;

import java.util.Date;

public class MonitorActivity extends AppCompatActivity implements SensorEventListener {

    Sensor aceler;
    SensorManager sensormanager;
    TextView txtac , txttime, txtac2;
    Button wakeB;
    float gx, gy, gz, Mag;
    float x=0;
    float y=0;
    float z=0;
    int dia, i, hours, minutes, seconds;
    long lastUpdate1 = 0;
    long lastUpdate2 = 0;
    private static final float NS2S = 1.0f / 1000000000.0f;
    float aux = 0;
    float auy = 0;
    float auz = 0;
    float count =0;
    static final float alpha = 0.05f;
    BancoController bancoController;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        i=0;
        wakeB = (Button) findViewById(R.id.wakebutton);

        sensormanager = (SensorManager) getSystemService(SENSOR_SERVICE);
        aceler = sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensormanager.registerListener(this, aceler, sensormanager.SENSOR_DELAY_NORMAL);


        bancoController = new BancoController(getBaseContext());
        cursor=bancoController.carregaDados1();


            cursor.moveToLast();
            String dia_string;
            String[] nomeCampos = new String[]{sqlitee.DIA, sqlitee.HORA, sqlitee.MINUTOS, sqlitee.SEGUNDOS, sqlitee.MAGNITUDE};

            dia_string = cursor.getString(0);
            dia = Integer.parseInt(dia_string);
            dia= dia+1;



        wakeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
       txttime = (TextView) findViewById(R.id.time_txt);
        txtac = (TextView) findViewById(R.id.ac_txt);
        txtac2 = (TextView) findViewById(R.id.actxt2);




                gx = alpha * gx + (1 - alpha) * event.values[0];  //filtro passa alta para elimnar gravidade
                gy = alpha * gy + (1 - alpha) * event.values[1];
                gz = alpha * gz + (1 - alpha) * event.values[2];



                x = event.values[0] - gx;
                y = event.values[1] - gy;
                z = event.values[2] - gz;                           //fim do filtro

                txtac2.setText("dia:"+dia+" Correção X"+ x+" "+y+" "+z);






        long curTime = System.currentTimeMillis();


        if ((curTime - lastUpdate1) > 1000 ) {    //se dentro do intervalo de 1s

            count++;
            aux = aux + x;
            auy = auy + y;
            auz = auz + z;
            long fad = 0;
            fad = curTime - lastUpdate1;
            //txtac.setText("exxxxxe"+auy+"     dd   "+curTime+"    "+fad);

            lastUpdate1 = curTime;
        }

        if ((curTime - lastUpdate2) > 60000) {    //se dentro do intervalo de 1min



            x = aux / count;
            y = auy / count;
            z = auz / count;
            Mag = (float) Math.sqrt(x * x + y * y + z * z);


            seconds = (int) (curTime / 1000) % 60 ;   //pegando horario
            minutes = (int) ((curTime / (1000*60)) % 60);
            hours   = (int) (((curTime / (1000*60*60)) % 24));

                if (hours < 3){ //diferença de fuzo
                    hours = hours - 3 + 24;
                } else { hours = hours -3;}




            txtac.setText(x+" "+y+" "+z+ " mag"+ Mag + "    Time "+ curTime + " horas " + hours +"    Minutos:" + minutes+" Segs"+seconds);


            i = i + 1;



            String Sdia, Shora,Sminuto,Ssegundo,Smag;

            Sdia = Integer.toString(dia);
            Shora= Integer.toString(hours);
            Sminuto= Integer.toString(minutes);
            Ssegundo= Integer.toString(seconds);
            Smag= Float.toString(Mag);

            String resultado;
            resultado = bancoController.insereDado(Sdia, Shora, Sminuto, Ssegundo, Smag);

            Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();


            txttime.setText("Salvou" + i);



            aux = 0;
            auy = 0;
            auz = 0;
            count=0;

            lastUpdate2 = curTime;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }





}