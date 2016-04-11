package com.yirugao.dec1606_littlemermaid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class ConversationActivity extends AppCompatActivity {

    /*Declaration Button variable*/
    Button nextButton,launchButton,exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        nextButton = (Button) findViewById(R.id.nextButton);
        launchButton = (Button) findViewById(R.id.launchButton);
        exitButton = (Button) findViewById(R.id.exitButton);

    }
}
