package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GestionMesocycle extends AppCompatActivity implements View.OnClickListener {
    private Button boutonCurrentMeso;
    private Button boutonNewMeso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionnaire_meso);

        boutonCurrentMeso = findViewById(R.id.boutonCurrentMeso);
        boutonCurrentMeso.setOnClickListener(this);

        boutonNewMeso = findViewById(R.id.boutonNewMeso);
        boutonNewMeso.setOnClickListener(this);

        // Pour que la flèche s'affiche dans la barre de titre de l'activité
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }
    @Override
    public void onClick(View v) {
        Intent i = new Intent();
        if (v == boutonCurrentMeso) {
            i.setClass(getApplicationContext(), CurrentMesocycle.class);
            startActivity(i);
        }
        if (v == boutonNewMeso) {
            i.setClass(getApplicationContext(), NewMesocycle.class);
            startActivity(i);
        }

    }


    // Méthode qui permet permet de revenir à l'activité précédente
    // lorsqu'on clique sur la flèche qui se trouve dans la barre de titre
    // de l'activité
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}