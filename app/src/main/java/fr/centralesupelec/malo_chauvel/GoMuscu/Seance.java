package fr.centralesupelec.malo_chauvel.GoMuscu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

//import com.spotify.android.appremote.api.ConnectionParams;
//import com.spotify.android.appremote.api.Connector;
//import com.spotify.android.appremote.api.SpotifyAppRemote;
//
//import com.spotify.protocol.client.Subscription;
//import com.spotify.protocol.types.PlayerState;
//import com.spotify.protocol.types.Track;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;


public class Seance extends BaseActivity implements View.OnClickListener {
    private MediaPlayer mediaPlayer;
    private Vector<String> musicList;
    private int musicNum;
    private TextView musicTitle;
    private SeekBar musicBar;
    private Context context;

    private Boolean musicPlaying;
    private ImageButton next, prev, pause;


    //VIEW
    private ListView list;
    private Button ser, rep, kg, plus1, plus2, plus3, old_rep, old_kg, old_ser, minus1, minus2, minus3, start, stop;
    private EditText minutes,seconds;

    String seance;

    //CHRONO :
    private CountDownTimer timer;
    private MediaPlayer alarm;
    private String times, timem;
    private long timeRemainingInMillis;
    private boolean isRunning;


    //LIST OF ARRAY STRINGS and StringAdapter for list d'exos
    private ArrayList<String> listItems;
    private ArrayAdapter<String> adapter;

    private JSONHandler handler = new JSONHandler();

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    int nb;
    boolean num_cycle_modifier = false;
    int derniere_seance;
    private String exo_actuelle;
    Vector<String> exo = new Vector<>();
    private Cursor cursor_derniere_seance;
    private int NumCycle = 0;
    private int NumCycle_actuelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance);

        musicPlaying = false;
        // Pour que la flèche s'affiche dans la barre de titre de l'activité
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nb = getIntent().getExtras().getInt("seance", 0);
        derniere_seance = getIntent().getExtras().getInt("derniere_seance", -1);
        Log.i("reprendre_seance", String.valueOf(derniere_seance));
        if (derniere_seance != -1){
            dbHelper = new DatabaseHelper(getApplicationContext());
            database = dbHelper.getWritableDatabase();
            dbHelper.open();
            Pair<Cursor, Integer> pair = dbHelper.getSeancesById(derniere_seance);
            cursor_derniere_seance = pair.first;
            nb = pair.second;
            if (cursor_derniere_seance.moveToFirst()) {
                do {
                    // Boucle à travers toutes les lignes du curseur
                    for (int i = 0; i < cursor_derniere_seance.getColumnCount(); i++) {
                        // Affichez chaque colonne pour la ligne actuelle du curseur
                        Log.d("probleme_cursor", cursor_derniere_seance.getColumnName(i) + ": " + cursor_derniere_seance.getString(i));
                    }
                } while (cursor_derniere_seance.moveToNext()); // Passez à la ligne suivante
            }



        }
        try {
            String file = "jsonfileCurrent.json";
            JSONObject obj = new JSONObject(handler.LectureJSON(this, file));
            JSONArray arr = obj.getJSONArray("seance" + nb);
            Log.i("json", String.valueOf(obj.getInt("nb_de_seance")));
            Toast.makeText(this, "Loading Séance " + nb, Toast.LENGTH_LONG).show();
            seance = handler.getJsonSeance(obj, nb);
            Log.i("json", arr.toString());
            for (int i = 1; i < arr.length(); i++) {
                exo.add(handler.getJsonExo(arr, nb, i));
                Log.i("json", exo.toString());
            }
        } catch (JSONException e) {
            Toast.makeText(this, "error loading JSON", Toast.LENGTH_LONG).show();
        }


        getSupportActionBar().setTitle(seance);

        //TAKES CARE OF THE LISTVIEW FOR EXERCICES
        listItems = new ArrayList<String>(exo);
        list = findViewById(R.id.listExo);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Sélectionnez l'élément cliqué
                list.setSelection(position);
                Log.i("seance_malo_click", String.valueOf(position));
                // Obtenez l'élément sélectionné si nécessaire
                Object selectedItem = parent.getItemAtPosition(position);
                // Démarrez une nouvelle activité en réponse à la sélection de l'élément
                dbHelper = new DatabaseHelper(getApplicationContext());
                database = dbHelper.getWritableDatabase();
                dbHelper.open();
                exo_actuelle = exo.get(position);
                Cursor cursor;
                if (derniere_seance == -1){
                    cursor = dbHelper.getLastData(exo.get(position));
                }else{
                    cursor = cursor_derniere_seance;
                    Log.i("reprendre_derniere", cursor.toString());
                }
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        int NbReps = cursor.getInt(cursor.getColumnIndex("NbReps"));
                        int NbSerie = cursor.getInt(cursor.getColumnIndex("NbSerie"));
                        int NbPoids = cursor.getInt(cursor.getColumnIndex("NbPoids"));
                        NumCycle = cursor.getInt(cursor.getColumnIndex("NumCycle"));
                        Log.i("seance_malo_db_result","Nbserie "+NbSerie+"reps "+NbReps+"poids "+NbPoids + "NumCycle :"+NumCycle);
                        old_kg.setText(String.valueOf(NbPoids));
                        old_rep.setText(String.valueOf(NbReps));
                        old_ser.setText(String.valueOf(NbSerie));
                        if (derniere_seance != -1){
                            break;
                        }
                    } while (cursor.moveToNext());
                }

                // Fermer la base de données après utilisation
                dbHelper.close();
            }
        });



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

        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        pause = findViewById(R.id.pause);

        musicTitle = findViewById(R.id.musicName);
        musicBar = findViewById(R.id.musicBar);


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

        prev.setOnClickListener(this);
        pause.setOnClickListener(this);
        next.setOnClickListener(this);

        minutes = findViewById(R.id.minutes);
        minutes.setCursorVisible(false);
        minutes.setOnClickListener(this);
        seconds = findViewById(R.id.seconds);
        seconds.setCursorVisible(false);
        seconds.setOnClickListener(this);

        //initiates audio
        context = this;

        alarm = MediaPlayer.create(this, R.raw.alarm);
        musicList = listRaw();
        musicNum = 1;
        musicTitle.setText(musicList.get(musicNum));
        int sound_id = context.getResources().getIdentifier(musicList.get(musicNum), "raw",
                context.getPackageName());
        if(sound_id != 0) {
            mediaPlayer = MediaPlayer.create(context, sound_id);
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //Progress bar
        musicBar.setMax(mediaPlayer.getDuration());
        musicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            public void onCompletion(MediaPlayer mp) {
//                mp.reset();
//                musicNum = (musicNum + 1)%4;
//                musicTitle.setText(musicList.get(musicNum));
//                try {
//                    mp.setDataSource(context, Uri.parse(musicList.get(musicNum)));
//                } catch (IOException e) {
//                    Toast.makeText(context, "sorry error", Toast.LENGTH_LONG).show();
//                }
//                mp.start();
//            }
//        });



        //button
        stop.setEnabled(false);

        seconds.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                seconds.setSelection(seconds.getText().length());
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Touche OK sélectionné.
                    seconds.clearFocus();
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
                    minutes.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(minutes.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    // Méthode qui permet permet de revenir à l'activité précédente
    // lorsqu'on clique sur la flèche qui se trouve dans la barre de titre
    // de l'activité
    @Override
    public boolean onSupportNavigateUp() {
        // Si derniere_seance est différent de -1, déclencher une autre activité
        if (derniere_seance != -1) {
            // Démarrez votre autre activité ici
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true; // Indique que l'action a été traitée
        } else {
            // Sinon, effectuez le comportement par défaut (retour arrière)
            onBackPressed();
            return true;
        }
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

         //ajoutData(String name, int series,int reps, double poids, int numeroseance, int numerocycle

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        dbHelper.open();

        if (derniere_seance == -1 && !num_cycle_modifier){
            NumCycle_actuelle = NumCycle+1;
            num_cycle_modifier = true;
        }
        boolean isCycleEqual = dbHelper.isLastCycleNumberEqual(exo_actuelle, NumCycle_actuelle);
        if (derniere_seance == -1 && !isCycleEqual) {
            Log.i("view_ajouter_data","Nouvelle valeur; NumSeance :"+nb + " NumCycle : "+ NumCycle_actuelle );
            Log.i("view_ajouter_data","ser " + ser.getText().toString()+ " rep "+ rep.getText().toString()+ " kg "+kg.getText().toString());
            dbHelper.ajoutData(exo_actuelle, Integer.valueOf(ser.getText().toString()), Integer.valueOf(rep.getText().toString()), Double.valueOf(kg.getText().toString()), nb, NumCycle_actuelle);
            dbHelper.close();
            NumCycle = NumCycle_actuelle;
        } else {
            Log.i("view_ajouter_data","modification valeur; NumSeance :"+nb + " NumCycle : "+ NumCycle);
            Log.i("view_ajouter_data","ser " + ser.getText().toString()+ " rep "+ rep.getText().toString()+ " kg "+kg.getText().toString());
            dbHelper.modifierData(exo_actuelle, Integer.valueOf(ser.getText().toString()), Integer.valueOf(rep.getText().toString()), Double.valueOf(kg.getText().toString()), nb, NumCycle_actuelle);
            dbHelper.close();
        }

        if (v == prev) {
            musicNum = ((musicNum + 1)%5);
            if (musicNum==0){musicNum=1;}
            String filename = "android.resource://" + this.getPackageName() + "/raw/" + musicList.get(musicNum);
            try {
                musicTitle.setText(musicList.get(musicNum));
                musicBar.setProgress(0);
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(this, Uri.parse(filename));
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (v == next) {
            musicNum = ((musicNum - 1)%5);
            if (musicNum==0){musicNum=4;}
            String filename = "android.resource://" + this.getPackageName() + "/raw/" + musicList.get(musicNum);
            try {
                musicTitle.setText(musicList.get(musicNum));
                musicBar.setProgress(0);
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(this, Uri.parse(filename));
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (v == pause) {
            if (musicPlaying) {
                mediaPlayer.pause();
                musicPlaying=false;
                ((ImageButton) v).setImageResource(R.drawable.play);
            }
            else {
                mediaPlayer.start();
                musicPlaying=true;
                ((ImageButton) v).setImageResource(R.drawable.pause);
            }
        }

        //timer
        if (v == start) {
            if (timer == null) {
                if (!isRunning){
                    times = seconds.getText().toString();
                    timem = minutes.getText().toString();
                    isRunning=true;
                }
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
                        alarm.start();
                        alarmDialogBox();
                        timer.cancel();
                        timer = null;
                        isRunning=false;
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
            if (timer != null){
                timer.cancel();
                timer = null;
            }
            minutes.setText(timem);
            seconds.setText(times);
        }
    }

    // Spotify

    @Override
    protected void onStart() {
        super.onStart();
//        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
//        SpotifyAppRemote.connect(this, mConnectionParams, mConnectionListener);
    }

    private void connected() {
        // Then we will write some more code here.
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }



    public void alarmDialogBox() {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set Alert Title
        builder.setTitle("ALARM");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the button
        builder.setNeutralButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click ok then dialog box is canceled.
            dialog.cancel();
            alarm.stop();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }


    public void play() {
        int duration = mediaPlayer.getDuration();
        int progress = 0;
        musicBar.setProgress(progress);
        musicTitle.setText(musicList.get(musicNum));
    }

    public Vector<String> listRaw(){
        Vector<String> musicList = new Vector<>();
        Field[] fields=R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){
            musicList.add(fields[count].getName());
            Log.i("Raw Asset: ", fields[count].getName());
        }
        return musicList;
    }
}
