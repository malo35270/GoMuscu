package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button boutonAjouterContact;
    private Button boutonAfficherContact;
    private Button boutonSupprimerContact;
    private Button boutonModifierContact;
    public static ContactOperations contactOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}