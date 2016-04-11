package com.yirugao.dec1606_littlemermaid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConversationActivity extends AppCompatActivity {

    /*Declaration Button and Label View variable*/
    Button nextButton,launchButton,exitButton;
    Button foodLabel,weatherLabel,questionLabel,greetingLabel,feelingLabel,
             askingLabel,bodyLabel,locationLabel,sportLabel;

    /*Remove later*/
    String textInformation;
    TextView testResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        /*Assign associate navigation ID to the button variable*/
        nextButton = (Button) findViewById(R.id.nextButton);
        launchButton = (Button) findViewById(R.id.launchButton);
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

        /*Launch Button method:
                * Return error message if user has no selected any id label
                * Return selected id message
                * */
        launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
}
