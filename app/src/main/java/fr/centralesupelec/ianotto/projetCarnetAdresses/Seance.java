package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;

public class Seance extends AppCompatActivity implements View.OnClickListener {

    CountDownTimer timer;
    MediaPlayer mediaPlayer;

    boolean isRunning = false;

    private TextView name;
    private Button old_ser;
    private Button old_rep;
    private Button old_kg;

    private Button ser;
    private Button rep;
    private Button kg;

    private Button plus1;
    private Button plus2;
    private Button plus3;

    private Button minus1;
    private Button minus2;
    private Button minus3;

    private ListView list;

    private EditText minutes;
    private TextView point;
    private EditText seconds;
    String seance;

    private Button start;
    private Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance);


        list = findViewById(R.id.list);
        seance = getIntent().getExtras().getString("seance","default_seance");

        name = findViewById(R.id.textView3);
        name.setText(seance);

        //assess id to object
        old_kg = findViewById(R.id.old_kg);
        old_ser = findViewById(R.id.old_ser);
        old_rep = findViewById(R.id.old_rep);

        kg = findViewById(R.id.kg);
        ser = findViewById(R.id.ser);
        rep = findViewById(R.id.rep);

        plus1 = findViewById(R.id.plus1);
        plus2 = findViewById(R.id.plus2);
        plus3 = findViewById(R.id.plus3);

        minus1 = findViewById(R.id.minus1);
        minus2 = findViewById(R.id.minus2);
        minus3 = findViewById(R.id.minus3);

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);

        //set onclick view listener
        old_kg.setOnClickListener(this);
        old_rep.setOnClickListener(this);
        old_ser.setOnClickListener(this);

        kg.setOnClickListener(this);
        rep.setOnClickListener(this);
        ser.setOnClickListener(this);

        plus1.setOnClickListener(this);
        plus2.setOnClickListener(this);
        plus3.setOnClickListener(this);

        minus1.setOnClickListener(this);
        minus2.setOnClickListener(this);
        minus3.setOnClickListener(this);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);

        minutes = findViewById(R.id.minutes);
        minutes.setCursorVisible(false);
        seconds = findViewById(R.id.seconds);
        seconds.setCursorVisible(false);
        point = findViewById(R.id.point);

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);

    }


    // Méthode qui permet permet de revenir à l'activité précédente
    // lorsqu'on clique sur la flèche qui se trouve dans la barre de titre
    // de l'activité
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == plus1) {
            ser.setText(String.valueOf(Integer.valueOf(ser.getText().toString()) + 1));
        }
        if (v == plus2) {
            rep.setText(String.valueOf(Integer.valueOf(rep.getText().toString()) + 1));
        }
        if (v == plus3) {
            kg.setText(String.valueOf(Integer.valueOf(kg.getText().toString()) + 1));
        }
        if(v == start){
            //kg.setText("hello");
            // create and setup timer
            long millis = 60000 * Integer.valueOf(minutes.getText().toString()) + 1000 * Integer.valueOf(seconds.getText().toString()) + 1000;
            timer = new CountDownTimer(millis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String min;
                    String sec;
                    min = String.valueOf((millisUntilFinished/1000)/60);
                    sec = String.valueOf((millisUntilFinished/1000)%60);

                    seconds.setText(sec);
                    minutes.setText(min);
                }

                @Override
                public void onFinish() {
                    mediaPlayer.start(); // no need to call prepare(); create() does that for you
                }
            };
            isRunning = true;
            timer.start();
        }
        if (v == stop){
            if(isRunning){
                timer.cancel();
                isRunning = false;
            }
            minutes.setText("01");
            seconds.setText("00");
        }
    }
}
