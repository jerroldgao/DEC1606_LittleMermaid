package com.yirugao.dec1606_littlemermaid;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class ReceiveActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener{

    //Declaration Text to speech object  variable
    private TextToSpeech textToSpeech;
    //status check code
    private int MY_DATA_CHECK_CODE = 0;

    //String value
    private String aStringValue;

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
    private Button nextWordButton,returnButton,speakButton,clearButton;

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

        //check for Text to speech data
        Intent checkTextToSpeechIntent = new Intent();
        checkTextToSpeechIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTextToSpeechIntent, MY_DATA_CHECK_CODE);

        //String value for add more word
        aStringValue = "";

         /*Assign associate navigation ID to the button variable*/
        nextWordButton = (Button) findViewById(R.id.nextWordButton);
        returnButton = (Button) findViewById(R.id.returnButton);
        speakButton = (Button) findViewById(R.id.speakButton);
        clearButton = (Button) findViewById(R.id.clearButton);

        /*Assign associate ID  to the text view variable*/
        selfTextWordView = (TextView) findViewById(R.id.selfTextWordView);
        voiceTextWordView = (TextView) findViewById(R.id.voiceTextWordView);

        //fetch the single string value from launch activity
        Intent intent = getIntent();
        try {
            String id = intent.getExtras().getString("word");
            //set the new string text on the text view
            //selfTextWordView.setText(id);

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
                    final Button bb = new Button( this );
                    row.addView( bb, 250, 200 );
                    //check the relevant title array
                    switch(id){
                        //close return the launch the activity
                        case "food":
                            bb.setText(foodTitle[index]);
                            bb.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                   // selfTextWordView.setText(bb.getText());
                                    setTextToView(bb);
                                }
                            });
                            break;
                        case "weather":
                            bb.setText(weatherTitle[index]);
                            bb.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    setTextToView(bb);
                                }
                            });
                            break;
                        case "question":
                            bb.setText(questionTitle[index]);
                            bb.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    setTextToView(bb);
                                }
                            });
                            break;
                        case "greeting":
                            bb.setText(greetingTitle[index]);
                            bb.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    setTextToView(bb);
                                }
                            });
                            break;
                        case "feeling":
                            bb.setText(feelingTitle[index]);
                            bb.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    setTextToView(bb);
                                }
                            });
                            break;
                        case "animal":
                            bb.setText(animalTitle[index]);
                            bb.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    setTextToView(bb);
                                }
                            });
                            break;
                        case "body":
                            bb.setText(bodyTitle[index]);
                            bb.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    setTextToView(bb);
                                }
                            });
                            break;
                        case "color":
                            bb.setText(colorTitle[index]);
                            bb.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    setTextToView(bb);
                                }
                            });
                            break;
                        case "sport":
                            bb.setText(sportTitle[index]);
                            bb.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    setTextToView(bb);
                                }
                            });
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
        speakButton.setOnClickListener(this);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selfTextWordView.setText("");
                aStringValue = "";
            }
        });
    }

    private void setTextToView(Button bb) {
        aStringValue += bb.getText() + " ";
        selfTextWordView.setText(aStringValue);
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
        //Result of Text to speech data check
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the Text to speech
                textToSpeech= new TextToSpeech(this, this);
            }else{
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
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
            case R.id.speakButton:
                String words = selfTextWordView.getText().toString();
                speakWords(words);
                break;
            case R.id.returnButton:
                this.finish();
                break;
            default:
                break;
        }
    }

    //speak the user text
    private void speakWords(String speech) {
        //speak straight away
        textToSpeech.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }

    //setup Text to speech
    public void onInit(int initStatus) {
        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if(textToSpeech.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
                textToSpeech.setLanguage(Locale.US);

        }else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Speech failed...", Toast.LENGTH_LONG).show();
        }
    }




}
