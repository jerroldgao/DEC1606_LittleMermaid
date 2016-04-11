package com.yirugao.dec1606_littlemermaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button launchButton = (Button) findViewById(R.id.launchButton);
        Button receiveButton = (Button) findViewById(R.id.receiveButton);
        MenuItem profileButton = (MenuItem) findViewById(R.id.action_profile);
        MenuItem settingButton = (MenuItem) findViewById(R.id.action_settings);
        MenuItem recordingButton = (MenuItem) findViewById(R.id.action_record);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_activity_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                return true;

            case R.id.action_record:
                // User chose the "Favorite" action, mark the current item
                // as a favorite..
                startActivity(new Intent(MainActivity.this,RecordingActivity.class));
                return true;

            case R.id.action_profile:
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


}
