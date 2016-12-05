package com.yirugao.dec1606_littlemermaid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class LaunchActivity extends Activity implements View.OnClickListener {

    /*Declaration Button and Label View variable*/
    Button nextButton,startButton,exitButton;

    /*Display button topic*/
    ArrayList<String> btnDisplay;

    /*Button Selection */
    HashSet<String> setSelected;
    //ArrayList<String> setSelected;

    HashMap<String, Set<String>> topic;

    Boolean isClickedDummy; // global after the declaration of your class


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

         /*Assign associate navigation ID to the button variable*/
        nextButton = (Button) findViewById(R.id.nextButton);
        startButton = (Button) findViewById(R.id.startButton);
        exitButton = (Button) findViewById(R.id.exitButton);

        /*setup the listener*/
        nextButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

        //Hash map for topic
        topic = new HashMap<String, Set<String>>();

        //topic display on btn
        btnDisplay = new ArrayList<String>();

        //topic get selected
        setSelected = new HashSet<>();
        //setSelected = new ArrayList<>();

        //read file from csv
        String line;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("Dec1606Lexicons.csv")));
            while((line = br.readLine())!= null){

                String[] words = line.split(",");

                HashSet<String> set = new HashSet<>();
                for(int i = 1 ; i < words.length ; i++){
                    set.add(words[i]);
                }
                topic.put(words[0].trim(),set);

                //save all the topic
                //topic.put(words[0],set_name);
                System.out.println(topic);
                //System.out.println(words.length);
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* go through each  */
        String key = "";
        //Get the set of key from hashmap
        Set setOfKeys = topic.keySet();
        //get the iterator instance from set
        Iterator iterator = setOfKeys.iterator();
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            btnDisplay.add(key);
            System.out.println("Key: "+ btnDisplay);
        }

        //Topic Btn display
        categoryDisplay();

    }

    @Override
    public void onClick(View v) {

        //define the button that has been clicked and perform the corresponding user selected
        switch (v.getId()){

            case R.id.nextButton:
                Toast.makeText(getApplicationContext(), "No current next category", Toast.LENGTH_SHORT).show();
                break;
            case R.id.startButton:
                if(setSelected.size() == 0){
                    Toast.makeText(getApplicationContext(), "Please selected category", Toast.LENGTH_SHORT).show();
                }else {
                    //send the value to the receive
                    Intent intent = new Intent(LaunchActivity.this, ReceiveActivity.class);
                    intent.putExtra("Topic",setSelected);
                    //start send
                    startActivity(intent);

                    //reset the string
                    setSelected.clear();
                }
                break;
            case R.id.exitButton:
                this.finish();
                break;
            default:
                break;
        }
    }

    //Topic display
    private void categoryDisplay() {

        try{
            //create the 9 X 9 buttons on the table layout with correspond word
            for(int index = 0; index < 9; index++){

                TableLayout table = (TableLayout) findViewById( R.id.topicLayout);

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
                    bb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Button button = (Button) view;
                            String selectedText = button.getText().toString();
                            //user selected the button add to the array list
                            if (topic.containsKey(selectedText)){
                                setSelected.addAll(topic.get(selectedText));
                                //System.out.println(topic.get(selectedText));
                                System.out.println(setSelected.toString());
                            }
                            //Toast.makeText(getApplicationContext(), selectedText, Toast.LENGTH_SHORT).show();
                        }
                    });
                    row.addView( bb, 450, 350 );
                    bb.setText(btnDisplay.get(index));
                }
            }

        }catch (NullPointerException e){

        }
    }


}
