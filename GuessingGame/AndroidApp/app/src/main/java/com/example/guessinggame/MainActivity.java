package com.example.guessinggame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText textGuess;
    private Button buttonGuess;
    private Button buttonPlayAgain;
    private TextView labelOut;
    private int numGuesses;
    private int gameNumber;

    //Generates the guess required for the game
    public void newGame()
    {
        gameNumber = (int)(Math.random() * 100 + 1);

    }

    //Function checks for the correct guess as well
    //as handles errors caused by bad input
    public void checkGuess()
    {
        String currentGuess = textGuess.getText().toString();
        String message = "";
        try
        {
            int convertedGuess = Integer.parseInt(currentGuess);
            if(convertedGuess > 100 || convertedGuess < 1)
            {
                message = "Enter a number between 1 and 100.";
            }
            else if(convertedGuess < gameNumber)
            {
                numGuesses++;
                message = convertedGuess + " is too low. Please try again";
            }
            else if( convertedGuess > gameNumber)
            {
                numGuesses++;
                message = convertedGuess + " is too high. Please try again";
            }
            else
            {
                numGuesses++;
                message = convertedGuess + " is correct. You win after " + numGuesses + " tries!";
                buttonPlayAgain.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        } catch(Exception e)
        {
            message = "Enter a number between 1 and 100.";
        } finally
        {
            labelOut.setText(message);
            textGuess.requestFocus();
            textGuess.selectAll();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connects UI elements to private variables for code
        //manipulation
        textGuess = (EditText) findViewById(R.id.textGuess);
        buttonGuess = (Button) findViewById(R.id.buttonGuess);
        labelOut = (TextView) findViewById(R.id.labelOut);
        buttonPlayAgain = (Button) findViewById(R.id.buttonPlayAgain);
        newGame();

        //Makes hitting the "Guess!" button cause the program to
        //check for the correct guess
        buttonGuess.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkGuess();
            }
        });

        buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
                labelOut.setText("Once entered, then click \"Guess!\"");
                buttonPlayAgain.setVisibility(View.INVISIBLE);
            }
        });

        //Allows the user to hit "enter" button on their keyboard to submit a guess
        textGuess.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                checkGuess();
                return true;
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}