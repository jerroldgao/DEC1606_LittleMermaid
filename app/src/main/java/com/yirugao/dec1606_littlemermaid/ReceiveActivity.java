package com.yirugao.dec1606_littlemermaid;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class ReceiveActivity extends Activity implements View.OnClickListener{

    /*Fix data string array, this will become the local database later on*/
    String[] foodTitle = { "Pie", "Apple", "Steak", "Bacon", "Banana", "Hot Dog","Ice Cream","Cookies","Candy","Orange"};
    String[] weatherTitle = { "Rain", "Snow", "Cloudy", "Party Cloudy", "Thunder Storms", "Windy","Drizzle","Flurries","Haze"};
    String[] questionTitle = { "What", "Where", "Who", "How", "Why", "When","What's up","How old","What time"};
    String[] greetingTitle = { "Hey", "Hi", "Good morning", "Good afternoon", "Good night", "Bye","See ya","Nice to see you","Nice to meet you"};
    String[] animalTitle = { "Fish", "Dog", "Cat", "Bird", "Pig", "Horse","Lamb","Donkey","Tiger"};
    String[] feelingTitle = { "Boring", "Exciting", "Sad", "Love", "Angry", "Happy","Surprise","Disgusted","Afraid"};
    String[] bodyTitle = { "Head", "Ear", "Neck", "Hand", "Foot", "Arm","Leg","Nose","Teeth"};
    String[] colorTitle = { "Red", "Purple", "Yellow", "Pink", "White", "Black","Blue","Green","Brown"};
    String[] sportTitle = { "Basketball", "Baseball", "Football", "Soccer", "Swimming", "Tennis","Ping Pong","Running","Hiking"};

    /*Declaration Button variable*/
    Button nextWordButton,returnButton;

    /*Declaration text View variable*/
    private TextView selfTextWordView;
    private TextView voiceTextWordView;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    // Declare the voice recognizer
    private Button btnSpeak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

         /*Assign associate navigation ID to the button variable*/
        nextWordButton = (Button) findViewById(R.id.nextWordButton);
        returnButton = (Button) findViewById(R.id.returnButton);

        /*Assign associate ID  to the text view variable*/
        selfTextWordView = (TextView) findViewById(R.id.selfTextWordView);
        voiceTextWordView = (TextView) findViewById(R.id.voiceTextWordView);

        //fetch the single string value from launch activity
        Intent intent = getIntent();
        try {
            String id = intent.getExtras().getString("word");
            //set the new string text on the text view
            selfTextWordView.setText(id);

            //create the 9 X 9 buttons on the table layout with correspond word
            for(int index = 0; index < 9; index++){
                TableLayout table = (TableLayout) findViewById( R.id.buttonLayout );

                int buttonsInRow = 0;
                int numRows = table.getChildCount();
                TableRow row = null;
                if( numRows > 0 ){
                    row = (TableRow) table.getChildAt( numRows - 1 );
                    buttonsInRow = row.getChildCount();
                }

                if( numRows == 0 || buttonsInRow == 3 ){
                    row = new TableRow( this );
                    table.addView( row );
                    buttonsInRow = 0;
                }
                if( buttonsInRow < 3 ){
                    Button bb = new Button( this );
                    row.addView( bb, 350, 220 );
                    //check the relevant title array
                    switch(id){
                        //close return the launch the activity
                        case "food":
                            bb.setText(foodTitle[index]);
                            break;
                        case "weather":
                            bb.setText(weatherTitle[index]);
                            break;
                        case "question":
                            bb.setText(questionTitle[index]);
                            break;
                        case "greeting":
                            bb.setText(greetingTitle[index]);
                            break;
                        case "feeling":
                            bb.setText(feelingTitle[index]);
                            break;
                        case "animal":
                            bb.setText(animalTitle[index]);
                            break;
                        case "body":
                            bb.setText(bodyTitle[index]);
                            break;
                        case "color":
                            bb.setText(colorTitle[index]);
                            break;
                        case "sport":
                            bb.setText(sportTitle[index]);
                            break;
                        default:
                            bb.setText("no data");
                            break;
                    }
                }
            }
        } catch (NullPointerException e){

        }
        btnSpeak = (Button) findViewById(R.id.listenButton);
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        /*setup the listener*/
        nextWordButton.setOnClickListener(this);
        returnButton.setOnClickListener(this);
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    voiceTextWordView.setText(result.get(0));
                }
                break;
            }

        }
    }

    @Override
    public void onClick(View v) {

        //define the button that has been clicked and perform the corresponding user selected
        switch (v.getId()){

            //close return the launch the activity
            case R.id.nextWordButton:
                Toast.makeText(getApplicationContext(), "No more words", Toast.LENGTH_SHORT).show();
                break;
            case R.id.returnButton:
                this.finish();
                break;
            default:
                break;
        }

    }
}
