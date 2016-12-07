package com.yirugao.dec1606_littlemermaid;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.DynamicLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;

import static android.R.attr.data;
import static android.R.interpolator.linear;

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
    private HashMap<String,ArrayList<Pair<String,Integer>>> nGramValue;
    private PriorityQueue<String> words;

    private ArrayList<String> w ;

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

        w = new ArrayList<>();

        /* Get the user selected value from category page*/
        Intent intent = getIntent();
        setSelected = (HashSet<String>) intent.getSerializableExtra("Topic");
        System.out.println("-From other activity---" + setSelected);

        //
        cateSumMap = new HashMap<String, Integer>();

        //


        aRef = new Firebase("https://littlemermaid.firebaseio.com/dictionary/");

        aRef.keepSynced(true);

        aRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String,HashMap<String,Integer>> lemmaStored = new HashMap<String,HashMap<String, Integer>>();
                  for (DataSnapshot childSnapshot : dataSnapshot.getChildren() ){
                      String currentLemma =childSnapshot.child("lemma").getValue().toString();
                      for(String setVal : setSelected){

                          //System.out.println(childSnapshot.child("lemma").getValue()+"---"+childSnapshot.child(setVal.trim()).getValue(String.class));
                          setVal = setVal.trim();

                          if(!lemmaStored.containsKey(setVal)){
                            lemmaStored.put(setVal, new HashMap<String,Integer>());
                          }
                          if (!lemmaStored.get(setVal).containsKey(childSnapshot.child("lemma").getValue())){
                            lemmaStored.get(setVal).put(childSnapshot.child("lemma").getValue().toString(),childSnapshot.child(setVal).getValue(Integer.class));
                          }

                      }
                      if (!nGramValue.containsKey(currentLemma)){
                          nGramValue.put(currentLemma,new ArrayList<Pair<String, Integer>>());
                      }
                      String line = childSnapshot.child("Ngram").getValue().toString();
                      line.replaceAll("\\{\\}","");
                      String[] terms = line.toLowerCase().split(",");
                      for (String term: terms){
                          String[] ngram = term.split(":");
                          nGramValue.get(currentLemma).add(new Pair<String, Integer>(ngram[0],Integer.parseInt(ngram[1])));
                      }
                   }

                    //System.out.println(lemmaStored.get("Food").toString());
                    Iterator<String> iterator = setSelected.iterator();
                    String firstCate = iterator.next().trim();
                    for(HashMap.Entry<String,Integer> lemmaValue: lemmaStored.get(firstCate).entrySet()){
                        cateSumMap.put(lemmaValue.getKey(),lemmaValue.getValue());
                        //System.out.println(lemmaValue.getKey()+" : " +lemmaValue.getValue());
                    }
                    while (iterator.hasNext()){
                        String cate = iterator.next().trim();
                        for(HashMap.Entry<String,Integer> lemmaValue: lemmaStored.get(cate).entrySet()){
                            cateSumMap.put(lemmaValue.getKey(),cateSumMap.get(lemmaValue.getKey())+lemmaValue.getValue());
                        }
                    }
                    //System.out.println(cateSumMap.size());
                    words = new PriorityQueue<String>(cateSumMap.size(),new Comparator<String>() {
                        @Override
                        public int compare(String s, String t1) {
                            return cateSumMap.get(t1)-cateSumMap.get(s);
                        }
                    });
                    words.addAll(cateSumMap.keySet());

                            //System.out.println(words.size());
                            /*
                                                        while (words.size() > 0) {
                                                            System.out.println(words.poll());
                                                        }
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

    private void wordSuggestion(int index, String prevWord, String prevPrevWord){
        words = new PriorityQueue<String>((Collection<? extends String>) new HeuristicFunction(index,prevWord,prevPrevWord,nGramValue,cateSumMap));
        words.addAll(cateSumMap.keySet());
        /**Here need to load pq to UI and refresh the UI*/
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


    /*  Button Display */
    public void wordButtonDisplay(ArrayList<String> ar) {


        try{
            //create the 9 X 9 buttons on the table layout with correspond word
            for(int index = 0; index < ar.size(); index++){

                TableLayout table = (TableLayout) findViewById( R.id.btnWord);

                int buttonsInRow = 0;
                int numRows = table.getChildCount();
                TableRow row = null;
                if( numRows > 0 ){
                    row = (TableRow) table.getChildAt( numRows - 1 );
                    buttonsInRow = row.getChildCount();
                }

                if( numRows == 0 || buttonsInRow == 4 ){
                    row = new TableRow( this );
                    table.addView( row );
                    buttonsInRow = 0;
                }
                if( buttonsInRow < 4 ){
                    Button bb = new Button( this );
                    row.addView( bb, 400, 250 );
                    bb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            System.out.println();
                        }
                    });
                    bb.setText(ar.get(index));
                }
            }

        }catch (NullPointerException e){

        }

    }



}
