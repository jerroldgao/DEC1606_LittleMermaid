package com.yirugao.dec1606_littlemermaid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LaunchActivity extends Activity implements View.OnClickListener {

    /*Declaration Button and Label View variable*/
    Button nextButton,startButton,exitButton;
    Button foodLabel,weatherLabel,questionLabel,greetingLabel,feelingLabel,
            askingLabel,bodyLabel,locationLabel,sportLabel;

    /*Testing remove later*/
    String toastMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

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
        nextButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {

        //define the button that has been clicked and perform the corresponding user selected
        switch (v.getId()){

            case R.id.foodButton:
                toastMessage = "food ";
                break;
            case R.id.weatherButton:
                toastMessage = "weather ";
                break;
            case R.id.questionButton:
                toastMessage = "question ";
                break;
            case R.id.greetingButton:
                toastMessage = "greeting ";
                break;
            case R.id.feelingButton:
                toastMessage = "feeling ";
                break;
            case R.id.askButton:
                toastMessage = "ask ";
                break;
            case R.id.bodyButton:
                toastMessage = "body ";
                break;
            case R.id.locationButton:
                toastMessage = "location ";
                break;
            case R.id.sportButton:
                toastMessage = "sport ";
                break;
            case R.id.nextButton:
                Toast.makeText(getApplicationContext(), "This is no current next category", Toast.LENGTH_SHORT).show();
                break;
            case R.id.startButton:
                launchCategory();
                break;
            case R.id.exitButton:

                break;
            default:
                break;
        }
    }

    /*Launch Button method:
        * Return error message if user has no selected any id label
        * Return selected id message
        * */
    public void launchCategory(){
        if (toastMessage.equals("")){
            Toast.makeText(getApplicationContext(), "Please selected the category", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LaunchActivity.this, ReceiveActivity.class);
            i.putExtra("word", toastMessage);
            startActivity(i);
        }
    }

}
