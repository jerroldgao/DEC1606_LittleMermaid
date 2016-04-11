package com.yirugao.dec1606_littlemermaid;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ConversationActivity extends Activity implements View.OnClickListener {

    /*Declaration Button and Label View variable*/
    Button nextButton,startButton,exitButton;
    Button foodLabel,weatherLabel,questionLabel,greetingLabel,feelingLabel,
             askingLabel,bodyLabel,locationLabel,sportLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        /*Assign associate navigation ID to the button variable*/
        nextButton = (Button) findViewById(R.id.nextButton);
        startButton = (Button) findViewById(R.id.startButton);
        exitButton = (Button) findViewById(R.id.exitButton);

        /*Assign associate ID category to the button variable*/
        foodLabel = (Button) findViewById(R.id.foodButton);
        weatherLabel = (Button) findViewById(R.id.weatherButton);
        questionLabel = (Button) findViewById(R.id.questionButton);
        greetingLabel = (Button) findViewById(R.id.greetingButton);
        feelingLabel = (Button) findViewById(R.id.feelingButton);
        askingLabel = (Button) findViewById(R.id.askButton);
        bodyLabel = (Button) findViewById(R.id.bodyButton);
        locationLabel = (Button) findViewById(R.id.locationButton);
        sportLabel = (Button) findViewById(R.id.sportButton);

        /*setup the listener*/
        foodLabel.setOnClickListener(this);
        weatherLabel.setOnClickListener(this);
        questionLabel.setOnClickListener(this);
        greetingLabel.setOnClickListener(this);
        feelingLabel.setOnClickListener(this);
        askingLabel.setOnClickListener(this);
        bodyLabel.setOnClickListener(this);
        locationLabel.setOnClickListener(this);
        sportLabel.setOnClickListener(this);

    }

    /*Launch Button method:
        * Return error message if user has no selected any id label
        * Return selected id message
        * */
    @Override
    public void onClick(View v) {

        //define the button that has been clicked and perform the corresponding user selected
        switch (v.getId()){

            case R.id.foodButton:
                Toast.makeText(getApplicationContext(), "This is food button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.weatherButton:
                Toast.makeText(getApplicationContext(), "This is weather button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.questionButton:
                Toast.makeText(getApplicationContext(), "This is question button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.greetingButton:
                Toast.makeText(getApplicationContext(), "This is greeting button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.feelingButton:
                Toast.makeText(getApplicationContext(), "This is feeling button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.askButton:
                Toast.makeText(getApplicationContext(), "This is ask button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bodyButton:
                Toast.makeText(getApplicationContext(), "This is body button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.locationButton:
                Toast.makeText(getApplicationContext(), "This is location button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sportButton:
                Toast.makeText(getApplicationContext(), "This is sport button", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }


    }

}
