package fr.centralesupelec.ianotto.GoMuscu;
import android.content.res.Configuration;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyDayNightTheme();
    }

    // Définissez la fonction que vous souhaitez appliquer à toutes les activités
    private void applyDayNightTheme() {
        Configuration configuration = getResources().getConfiguration();
        int currentNightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                setTheme(R.style.AppTheme);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                setTheme(R.style.AppTheme_Dark);
                break;
        }
    }
}
