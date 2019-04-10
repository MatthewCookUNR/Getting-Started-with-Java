package com.example.guessinggame;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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

public class MainActivity extends AppCompatActivity
{
    private EditText textGuess;
    private Button buttonGuess;
    private Button buttonPlayAgain;
    private TextView labelOut;
    private int numGuesses;
    private int gameNumber;
    private int guessRange = 100;
    private TextView labelRange;
    private boolean gameOver;


    //Essentially starts the game with a new guess number
    //Gives a default guess of half of the number range
    public void newGame()
    {
        numGuesses = 0;
        gameOver = false;
        gameNumber = (int)(Math.random() * guessRange + 1);
        labelRange.setText("Enter a number between 1 and " + guessRange + ".");
        textGuess.setText("" + guessRange/2);
        textGuess.requestFocus();
        textGuess.selectAll();
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
            if(convertedGuess > guessRange || convertedGuess < 1)
            {
                message = "Enter a number between 1 and " + guessRange + ".";
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
                gameOver = true;

                //Increments number of games won in Game Stats
                SharedPreferences myPreferences =
                        PreferenceManager.getDefaultSharedPreferences(this);
                int gamesWon = myPreferences.getInt("gamesWon", 0) + 1;
                SharedPreferences.Editor myEditor = myPreferences.edit();
                myEditor.putInt("gamesWon", gamesWon);
                myEditor.apply();

                //Creates a alert saying you won and makes play again button visible
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                buttonPlayAgain.setVisibility(View.VISIBLE);
            }
        } catch(Exception e)
        {
            message = "Enter a number between 1 and " + guessRange + ".";
        } finally
        {
            labelOut.setText(message);
            textGuess.requestFocus();
            textGuess.selectAll();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connects UI elements to private variables for code
        //manipulation
        textGuess = (EditText) findViewById(R.id.textGuess);
        buttonGuess = (Button) findViewById(R.id.buttonGuess);
        labelOut = (TextView) findViewById(R.id.labelOut);
        buttonPlayAgain = (Button) findViewById(R.id.buttonPlayAgain);
        labelRange = (TextView) findViewById(R.id.gameInstruct);
        SharedPreferences myPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        guessRange = myPreferences.getInt("range", 100);
        newGame();

        //Makes hitting the "Guess!" button cause the program check
        //for the correct guess if game is currently not over
        buttonGuess.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!gameOver)
                {
                    checkGuess();
                }
            }
        });

        //Play again button starts new game and then disappears
        buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
                String message = "Once entered, then click \"Guess!\"";
                labelOut.setText(message);
                buttonPlayAgain.setVisibility(View.INVISIBLE);
            }
        });

        //Allows the user to hit "enter" button on their keyboard to submit a guess
        //if game is currently not over
        textGuess.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(!gameOver)
                {
                    checkGuess();
                }
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Handles selection of items from the games menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            //Allows user to change the settings (range of numbers)
            case R.id.action_settings:
                final CharSequence[] choices = {"Easy (1 to 10)", "Normal (1 to 100)",
                        "Hard (1 to 1000)"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select the desired range:");
                builder.setItems(choices, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choice) {
                        switch(choice)
                        {
                            case 0:
                                guessRange = 10;
                                storeRange(guessRange);
                                newGame();
                                break;
                            case 1:
                                guessRange = 100;
                                storeRange(guessRange);
                                newGame();
                                break;
                            case 2:
                                guessRange = 1000;
                                storeRange(guessRange);
                                newGame();
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;

            //Starts a new game
            case R.id.action_newgame:
                newGame();
                return true;

            //Shows user the number of games they have won
            case R.id.action_gamestats:
                SharedPreferences myPreferences =
                        PreferenceManager.getDefaultSharedPreferences(this);
                int gamesWon = myPreferences.getInt("gamesWon", 0);
                AlertDialog statDialog = new AlertDialog.Builder(MainActivity.this).create();
                statDialog.setTitle("Guessing Game Stats");
                statDialog.setMessage("You have won " + gamesWon + " games in total. Awesome!");
                statDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                            }
                        });
                statDialog.show();
                return true;

            //Displays a bit of information about the game
            case R.id.action_about:
                AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
                aboutDialog.setTitle("About Guessing Game");
                aboutDialog.setMessage(
                        "The game was created by Matthew Cook for his own educational purposes.");
                aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                aboutDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Allows game to keep track of the last selected range of numbers
    //after being closed
    public void storeRange(int newRange)
    {
        SharedPreferences myPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor myEditor = myPreferences.edit();
        myEditor.putInt("range", newRange);
        myEditor.apply();
    }

}