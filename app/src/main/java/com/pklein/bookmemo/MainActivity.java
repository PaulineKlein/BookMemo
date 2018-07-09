package com.pklein.bookmemo;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.library_button) ImageButton library_button;
    @BindView(R.id.add_button) ImageButton add_button;
    @BindView(R.id.search_button) ImageButton search_button;
    @BindView(R.id.stats_button) ImageButton stats_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        library_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent();
                // par défaut on veut tout afficher :
                i.putExtra("annee_search", 0);
                i.putExtra("int_radio_collection", -1);
                i.putExtra("int_radio_possession", -1);
                i.putExtra("int_radio_scan", -1);
                i.putExtra("int_radio_anime", -1);
                i.putExtra("Titre_search", "");
                i.putExtra("auteur_search", "");
                i.putExtra("str_radio_type", "manga");

                i.setClassName("com.klein.bookmemo", "com.klein.bookmemo.SeeAllActivity");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getApplicationContext().startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_import) {
            Log.i(TAG, "action_import ");
            return true;
        }

        if (id == R.id.action_export) {
            Log.i(TAG, "action_import ");
            return true;
        }

        if (id == R.id.action_about) {
            Log.i(TAG, "action_about ");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
