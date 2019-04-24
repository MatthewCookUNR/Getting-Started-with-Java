package com.example.secretmessages;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText inputText;
    private EditText outputText;
    private EditText keyVal;
    private SeekBar keySlider;
    private Button cipherButton;
    private Button moveButton;

    //Takes in the desired message and key and encode or decodes
    //the message (symmetrical)
    public String encodeDecode (String message, int keyVal)
    {
        String output = "";
        String rvsedMessage = "";
        char key = (char) keyVal;
        keySlider.setProgress(keyVal + 13);

        //Reverses the message to make it harder to guess
        for(int i = message.length()-1; i >= 0; i--)
        {
            rvsedMessage += message.charAt(i);
        }

        //Cipher "shifts" each character of the message alphabetically by the key
        //Numbers are shifted by the key modulo 10
        //Shift wraps around if it exceeds the first/last letter or digit
        for(int x = 0; x < rvsedMessage.length(); x++)
        {
            char input = rvsedMessage.charAt(x);

            //Shifts uppercase letters
            if( input >= 'A' && input <= 'Z')
            {
                input += key;
                if(input > 'Z')
                {
                    input -= 26;
                }
                if(input < 'A')
                {
                    input += 26;
                }
            }
            //Shifts lowercase letters
            else if( input >= 'a' && input <= 'z')
            {
                input += key;
                if(input > 'z')
                {
                    input -= 26;
                }
                if(input < 'a')
                {
                    input += 26;
                }
            }
            //Shifts numbers
            else if( input >= '0' && input <= '9')
            {
                input += (keyVal % 10);
                if(input > '9')
                {
                    input -= 10;
                }
                if(input < '0')
                {
                    input += 10;
                }
            }
            output += input;
        }
        return output;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inputText = findViewById(R.id.inputText);
        outputText = findViewById(R.id.outputText);
        keyVal = findViewById(R.id.keyVal);
        keySlider = findViewById(R.id.keySlider);
        cipherButton = findViewById(R.id.cipherButton);
        moveButton = findViewById(R.id.moveButton);

        //Handles sharing of text into the app
        Intent receivedIntent = getIntent();
        String receivedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
        if(receivedText != null)
        {
            inputText.setText(receivedText);
        }

        //Button encodes/decodes text using inputted text and key value
        cipherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    int key = Integer.parseInt(keyVal.getText().toString());
                    if(key < -13 || key > 13)
                    {
                        throw new ArithmeticException("Outside range");
                    }
                    String textIn = inputText.getText().toString();
                    String output = encodeDecode(textIn, key);
                    outputText.setText(output);
                }
                catch (Exception e)
                {
                    String defaultKey = "13";
                    keyVal.setText(defaultKey);
                    keySlider.setProgress(26);
                    Toast.makeText(MainActivity.this, "Please enter a key between -13 and 13.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Button sets the input text to the output text
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    String textOut = outputText.getText().toString();
                    if(textOut.isEmpty())
                    {
                        throw new Exception();
                    }
                    int key = Integer.parseInt(keyVal.getText().toString());
                    inputText.setText(textOut);
                    keySlider.setProgress(key * -1 + 13);
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, "Please enter text to be encoded or decoded.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //SeekBar is from -13 to 13 and encodes/decodes text whenever progress changes
        keySlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                int key = keySlider.getProgress() - 13;
                String message = inputText.getText().toString();
                String output = encodeDecode(message, key);
                String newKey = "" + key;
                outputText.setText(output);
                keyVal.setText(newKey);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        //Fab handles sharing text to other applications on android device such as email
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Secret Message " + DateFormat.getDateInstance().format(new Date()) + " " + DateFormat.getTimeInstance().format(new Date()));
                shareIntent.putExtra(Intent.EXTRA_TEXT, outputText.getText().toString());
                try
                {
                    startActivity(Intent.createChooser(shareIntent, "Share message..."));
                    finish();
                }
                catch (android.content.ActivityNotFoundException ex)
                {
                    Toast.makeText(MainActivity.this, "Error: Sharing failed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
            aboutDialog.setTitle("About Secret Messages");
            aboutDialog.setMessage("Application was created by Matthew Cook for his own educational purposes");
            aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });
            aboutDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
