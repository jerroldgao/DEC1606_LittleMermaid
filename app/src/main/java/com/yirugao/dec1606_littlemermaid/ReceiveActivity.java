package com.yirugao.dec1606_littlemermaid;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;

import static android.R.attr.data;

public class ReceiveActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener{

    //Declaration Text to speech object  variable
    private TextToSpeech textToSpeech;
    //status check code
    private int MY_DATA_CHECK_CODE = 0;

    //String value
    private String aStringValue;

    /*Declaration Button variable*/
    private Button nextWordButton,returnButton,speakButton,clearButton;

    /*Declaration text View variable*/
    private TextView selfTextWordView;
    private TextView voiceTextWordView;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    // Declare the voice recognizer
    private Button btnSpeak;

    /* Firebase*/
    private Firebase aRef;

    private HashSet<String> setSelected;

    private HashMap<String,HashMap<String,Integer>> lemmaStored;
    private HashMap<String,Integer> cateSumMap;

    private PriorityQueue<String> words;

    private ArrayList<String> dataRetrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        Firebase.setAndroidContext(this);
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


        dataRetrieve = new ArrayList<>();

        /* Get the user selected value from category page*/
        Intent intent = getIntent();
        setSelected = (HashSet<String>) intent.getSerializableExtra("Topic");
        System.out.println("-From other activity---" + setSelected);

        //
        cateSumMap = new HashMap<String, Integer>();

        //
        lemmaStored = new HashMap<String,HashMap<String, Integer>>();

        //topic get selected
        setSelected = new HashSet<>();

        aRef = new Firebase("https://littlemermaid.firebaseio.com/dictionary/");

        aRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //test if connect to the database
                //Log.e("Count---- " ,""+dataSnapshot.getChildrenCount());
                for (DataSnapshot ds : dataSnapshot.getChildren() ){

                    dataRetrieve.add(ds.getValue().toString());
                    //System.out.println(ds.getValue().toString());
                }

                System.out.println(dataRetrieve.get(0));


                /*
                //word
                for (String setVal : setSelected) {
                    //                    if (setVal.equals("Food")) {
                    //                        System.out.println(setVal);
                    //                    }else {
                    //                        System.out.println(setVal);
                    //                    }
                    setVal = setVal.trim();
                    //System.out.println(dataSnapshot.child("lemma").getValue()+"----"+dataSnapshot.child(setVal.trim()).getValue());
                    if(!lemmaStored.containsKey(setVal)){
                        lemmaStored.put(setVal, new HashMap<String,Integer>());
                    }
                    if (!lemmaStored.get(setVal).containsKey(dataSnapshot.child("lemma").getValue())){
                        lemmaStored.get(setVal).put(dataSnapshot.child("lemma").getValue().toString(),Integer.parseInt(dataSnapshot.child(setVal).getValue().toString()));
                    }

                }
                System.out.println(lemmaStored.get("Food").size());

                Iterator<String> iterator = setSelected.iterator();
                String firstCate = iterator.next().trim();
//                System.out.println(firstCate);
                for(HashMap.Entry<String,Integer> lemmaValue: lemmaStored.get(firstCate).entrySet()){
                    cateSumMap.put(lemmaValue.getKey(),lemmaValue.getValue());
                    System.out.println(lemmaValue.getKey()+" : " +lemmaValue.getValue());
                }
                while (iterator.hasNext()){
                    String cate = iterator.next().trim();
                    for(HashMap.Entry<String,Integer> lemmaValue: lemmaStored.get(cate).entrySet()){
                        cateSumMap.put(lemmaValue.getKey(),cateSumMap.get(lemmaValue.getKey())+lemmaValue.getValue());
                    }
                }
//                System.out.println(cateSumMap.size());
//                words = new PriorityQueue<String>(cateSumMap.size(),new Comparator<String>() {
//                    @Override
//                    public int compare(String s, String t1) {
//                        return cateSumMap.get(t1)-cateSumMap.get(s);
//                    }
//                });
//                words.addAll(cateSumMap.keySet());

//                while (words.size() > 0) {
//                    System.out.println(words.poll());
//                }
                //Comparator comp = words.comparator();
                */

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



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
    }  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// OnCreated  method end ////////////////////////////////////////////////////////////

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
