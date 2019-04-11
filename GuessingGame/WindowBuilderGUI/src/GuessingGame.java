import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GuessingGame extends JFrame
{
	//Private data members
	private JTextField textGuess;
	private JLabel labelOut;
	private int guessNumber;
	private JButton buttonPlayAgain;
	private int numGuesses;
	
	//Constructor that sets and handles the GUI
	public GuessingGame() {
		//Private data member that counts number of tries
		//for the correct guess
		numGuesses = 0;
		
		//Initializes JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Matt's Guessing Game");
		getContentPane().setLayout(null);
		
		//Label that gives the game's title
		JLabel lblGuessingGame = new JLabel("Guessing Game");
		lblGuessingGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblGuessingGame.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblGuessingGame.setBounds(218, 11, 117, 17);
		getContentPane().add(lblGuessingGame);
		
		//Label that tells the user to guess a number between 1 and 100
		JLabel lblNewLabel = new JLabel("Guess a number between 1 and 100:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(138, 96, 199, 14);
		getContentPane().add(lblNewLabel);
		
		//Text field created for the user to input their numerical guess
		textGuess = new JTextField();
		textGuess.setHorizontalAlignment(SwingConstants.CENTER);
		textGuess.setBounds(347, 94, 56, 20);
		getContentPane().add(textGuess);
		textGuess.setColumns(10);
		
		//Button that causes the game to check if the current input
		//in the text field is the correct guess
		JButton buttonGuess = new JButton("Guess!");
		buttonGuess.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				checkGuess();
			}
		});
		buttonGuess.setBounds(225, 169, 103, 29);
		getContentPane().add(buttonGuess);
		
		//Creates a text label that changes when a player gives a inputted number
		labelOut = new JLabel("Enter a number above and click \"Guess!\"");
		labelOut.setHorizontalAlignment(SwingConstants.CENTER);
		labelOut.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelOut.setBounds(148, 269, 257, 14);
		getContentPane().add(labelOut);
		
		//Play button restarts the game
		//The button is only visible after the player wins
		buttonPlayAgain = new JButton("Play Again?");
		buttonPlayAgain.setVisible(false);
		buttonPlayAgain.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				numGuesses = 0;
				newGuess();
				labelOut.setText("Enter a number above and click \"Guess!\"");
				buttonPlayAgain.setVisible(false);
			}
		});
		buttonPlayAgain.setBounds(225, 210, 103, 29);
		getContentPane().add(buttonPlayAgain);
		
	}
	
	//Generates the guess required for the game
	public void newGuess()
	{
		guessNumber = (int)(Math.random() * 100 + 1);
	}
	
	//Function checks for the correct guess as well
	//as handles errors caused by bad input
	public void checkGuess()
	{
		String currentGuess = textGuess.getText();
		String message = "";
		try
		{
			int convertedGuess = Integer.parseInt(currentGuess);
			if(convertedGuess > 100 || convertedGuess < 1)
			{
				message = "Enter a whole number between 1 and 100.";
			}
			else if(convertedGuess < guessNumber)
			{
				numGuesses++;
				message = convertedGuess + " is too low. Please try again";
			}
			else if( convertedGuess > guessNumber)
			{
				numGuesses++;
				message = convertedGuess + " is too high. Please try again";
			}
			else
			{
				numGuesses++;
				message = convertedGuess + " is correct. You win after " + numGuesses + " tries!";
				buttonPlayAgain.setVisible(true);
			}
		} catch(Exception e)
		{
			message = "Enter a whole number between 1 and 100.";
		} finally
		{
			labelOut.setText(message);
			textGuess.requestFocus();
			textGuess.selectAll();
		}
	}
	
	//Opens window for the game and starts it
	public static void main(String[] args) 
	{
		GuessingGame nGame = new GuessingGame();
		nGame.newGuess();
		nGame.setSize(new Dimension(600,400));
		nGame.setVisible(true);
	}
}
