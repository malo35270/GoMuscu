package fr.centralesupelec.ianotto.projetCarnetAdresses;

import static android.text.TextUtils.substring;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;

public class Seance extends AppCompatActivity implements View.OnClickListener {

    private CountDownTimer timer;
    private MediaPlayer mediaPlayer;

    //VIEW
    private TextView name, point;
    private ListView list;
    private Button ser, rep, kg, plus1, plus2, plus3, old_rep, old_kg, old_ser, minus1, minus2, minus3, start, stop;
    private EditText minutes,seconds;

    String seance, times, timem;
    private boolean isRunning = false;
    private long timeRemainingInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance);

        // Pour que la flèche s'affiche dans la barre de titre de l'activité
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        seance = getIntent().getExtras().getString("seance", "default_seance");

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
        minutes.setOnClickListener(this);
        seconds = findViewById(R.id.seconds);
        seconds.setCursorVisible(false);
        seconds.setOnClickListener(this);
        point = findViewById(R.id.point);

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        stop.setEnabled(false);

        seconds.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                seconds.setSelection(seconds.getText().length());

                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Touche OK sélectionné.
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(seconds.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        minutes.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                minutes.setSelection(minutes.getText().length());

                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Touche OK sélectionné.
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(minutes.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }


    /*private void handleEnterKeyPress() {
        EditText edit = (EditText) this.getCurrentFocus();
        edit.setText(cleanZeros(edit.toString()));
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }*/

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
        //plus
        if (v == plus1) {ser.setText(String.valueOf(Integer.valueOf(ser.getText().toString()) + 1));}
        if (v == plus2) {rep.setText(String.valueOf(Integer.valueOf(rep.getText().toString()) + 1));}
        if (v == plus3) {kg.setText(String.valueOf(Integer.valueOf(kg.getText().toString()) + 1));}
        //minus
        if (v == minus1) {ser.setText(String.valueOf(Integer.valueOf(ser.getText().toString()) - 1));}
        if (v == minus2) {rep.setText(String.valueOf(Integer.valueOf(rep.getText().toString()) - 1));}
        if (v == minus3) {kg.setText(String.valueOf(Integer.valueOf(kg.getText().toString()) - 1));}

        //timer
        if (v == start) {
            if (timer == null) {
                times = seconds.getText().toString();
                timem = minutes.getText().toString();
                //creates new timer
                timeRemainingInMillis = 60000 * Long.parseLong(minutes.getText().toString()) + 1000 * Long.parseLong(seconds.getText().toString()) + 1000;
                timer = new CountDownTimer(timeRemainingInMillis, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timeRemainingInMillis = millisUntilFinished;
                        String min;
                        String sec;
                        min = String.valueOf((millisUntilFinished/1000)/60);
                        sec = String.valueOf((millisUntilFinished/1000)%60);

                        seconds.setText(sec);
                        minutes.setText(min);
                    }
                    @Override
                    public void onFinish() {
                        mediaPlayer.start();
                        timer.cancel();
                        timer = null;
                        minutes.setText(timem);
                        seconds.setText(times);
                    }
                }.start();
                //manages buttons
                stop.setEnabled(true);
                start.setText("PAUSE");
            }
            else {
                start.setText("START");
                timer.cancel();
                timer = null;
            }
        }

        if (v == stop){
            start.setText("START");
            stop.setEnabled(false);
            if (timer == null){
                timer.cancel();
            }
            timer = null;
            seconds.setText("00");
            minutes.setText("00");
        }
    }
}
