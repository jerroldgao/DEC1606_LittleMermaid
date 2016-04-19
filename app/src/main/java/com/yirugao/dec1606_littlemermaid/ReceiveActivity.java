package com.yirugao.dec1606_littlemermaid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiveActivity extends Activity implements View.OnClickListener{

    /*Declaration Button variable*/
    Button nextWordButton,returnButton;
    /*Declaration text View variable*/
    TextView textWordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

         /*Assign associate navigation ID to the button variable*/
        nextWordButton = (Button) findViewById(R.id.nextWordButton);
        returnButton = (Button) findViewById(R.id.returnButton);

        /*Assign associate ID  to the text view variable*/
        textWordView = (TextView) findViewById(R.id.textWordView);

        //fetch the single string value from launch activity
        Intent intent = getIntent();
        String id = intent.getExtras().getString("word");
        //set the new string text on the text view
        textWordView.setText(id);

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
                bb.setText(id + index);
            }
        }

            /*setup the listener*/
            returnButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //define the button that has been clicked and perform the corresponding user selected
        switch (v.getId()){

            //close current activity and return the launch the activity
            case R.id.returnButton:
                this.finish();
                break;
            default:
                break;
        }

    }
}
