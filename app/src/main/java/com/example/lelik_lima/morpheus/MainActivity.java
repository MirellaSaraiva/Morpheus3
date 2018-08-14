package com.example.lelik_lima.morpheus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button start, info, info2;
    CheckBox despertadorcheck;
    EditText hora;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        start = (Button) findViewById(R.id.startbutton);
        info = (Button) findViewById(R.id.graphbutton);
        info2= (Button) findViewById(R.id.appbutton);

        despertadorcheck = (CheckBox) findViewById(R.id.despertadorcheck);
        hora = (EditText) findViewById(R.id.TimeeditText);


        info2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, InformaActivity.class);
                startActivity(intent);
            }
        });

        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, MonitorActivity.class);
                startActivity(intent);
            }
        });



    }
}
