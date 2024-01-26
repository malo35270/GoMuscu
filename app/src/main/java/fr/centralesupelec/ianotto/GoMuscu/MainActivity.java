package fr.centralesupelec.ianotto.GoMuscu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import fr.centralesupelec.ianotto.GoMuscu.R;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button boutonNouvelleSeance;
    private Button boutonReprendreSeance;
    private Button boutonGestionnaireMesocycle;
    private Button boutonParametreCompte;

    public static int num_meso=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boutonNouvelleSeance = findViewById(R.id.boutonDemarrerUneNouvelleSeance);
        boutonNouvelleSeance.setOnClickListener(this);

        boutonReprendreSeance = findViewById(R.id.MainAct_Btn_ReprendreSeance);
        boutonReprendreSeance.setOnClickListener(this);

        boutonGestionnaireMesocycle = findViewById(R.id.boutonGestionnaireMesocycle);
        boutonGestionnaireMesocycle.setOnClickListener(this);

        boutonParametreCompte = findViewById(R.id.boutonParametreCompte);
        boutonParametreCompte.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent();
        if (v == boutonNouvelleSeance) {
            i.setClass(getApplicationContext(), NouvelleSeance.class);
            startActivity(i);
        }
        if (v == boutonReprendreSeance) {
            i.setClass(getApplicationContext(), AfficherContactsActivity.class);
            startActivity(i);
        }
        if (v == boutonGestionnaireMesocycle) {
            i.setClass(getApplicationContext(), GestionMesocycle.class);
            startActivity(i);
        }
        if (v == boutonParametreCompte) {
            i.setClass(getApplicationContext(), parametreCompte.class);
            startActivity(i);
        }
    }


}