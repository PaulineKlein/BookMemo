package com.pklein.bookmemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.pklein.bookmemo.tools.FileEditor;

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

        // to be able to import /export data from files :
        if (shouldAskPermissions()) {
            askPermissions();
        }

        library_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SeeAllActivity.class );
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getApplicationContext().startActivity(i);
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddActivity.class );
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getApplicationContext().startActivity(i);
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SearchActivity.class );
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getApplicationContext().startActivity(i);
            }
        });

        stats_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),StatsActivity.class );
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

            FileEditor importFile = new FileEditor();
            try{
                importFile.importData(this.getContentResolver());
                Toast.makeText(getApplicationContext(), R.string.file_OK_import, Toast.LENGTH_LONG).show();
            }catch (Exception e)
            {
                Log.e(TAG, e.getMessage());
                if(e.getMessage().equals("Absent"))
                    Toast.makeText(getApplicationContext(), R.string.file_absent, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), R.string.file_error, Toast.LENGTH_LONG).show();
            }
            return true;
        }

        if (id == R.id.action_export) {
            Log.i(TAG, "action_export ");

            FileEditor exportFile = new FileEditor();
            try{
                exportFile.exportData(exportFile.getFile(), this.getContentResolver());
                Toast.makeText(getApplicationContext(), R.string.file_OK_export, Toast.LENGTH_LONG).show();
            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(), R.string.file_error, Toast.LENGTH_LONG).show();
            }
            return true;
        }

        if (id == R.id.action_about) {
            Log.i(TAG, "action_about ");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* with the Help of https://stackoverflow.com/questions/8854359/exception-open-failed-eacces-permission-denied-on-android */
    protected boolean shouldAskPermissions() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))){

            return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
        }
        return false;
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }
}
