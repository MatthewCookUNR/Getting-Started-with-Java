import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//Program creates a GUI that uses classic Caesar cipher to encode and decode messages
public class SecretMessagesGUI extends JFrame 
{
	private JTextField textKey;
	private JTextArea textIn;
	private JTextArea textOut;
	private JSlider slider;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	
	//Constructs GUI
	public SecretMessagesGUI() 
	{
		//Initializes JFrame
		getContentPane().setBackground(new Color(135, 206, 250));
		setTitle("Matt's Secret Message App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		//Allows textIn area to scroll for long messages
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 568, 140);
		getContentPane().add(scrollPane);
		
		//Text area for user to write message that will be encoded/decoded
		textIn = new JTextArea();
		scrollPane.setViewportView(textIn);
		textIn.setWrapStyleWord(true);
		textIn.setLineWrap(true);
		textIn.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		//Allows textOut area to scroll for long messages
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 214, 568, 140);
		getContentPane().add(scrollPane_1);
		
		//Text area that presents the encoded/decoded message
		textOut = new JTextArea();
		scrollPane_1.setViewportView(textOut);
		textOut.setWrapStyleWord(true);
		textOut.setLineWrap(true);
		textOut.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		//Text field that allows user to input the key for the cipher
		textKey = new JTextField();
		textKey.addKeyListener(new KeyAdapter() //Code updates slider
		{
			@Override
			public void keyReleased(KeyEvent e) 
			{
				try
				{
					int intKey = Integer.parseInt(textKey.getText());
					if(intKey < -25 || intKey > 25)
					{
						throw new ArithmeticException("Out of desired bounds");
					}
					slider.setValue(intKey);
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(getContentPane(), 
							"Please enter a whole number between -25 and 25 for the encryption key.");
					textKey.requestFocus();
					textKey.selectAll();
				}
			}
		});
		textKey.setText("0");
		textKey.setHorizontalAlignment(SwingConstants.CENTER);
		textKey.setBounds(259, 172, 49, 20);
		getContentPane().add(textKey);
		textKey.setColumns(10);
		
		//Label for the field that the user will enter the key into
		JLabel lblKey = new JLabel("Key:");
		lblKey.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKey.setBounds(220, 175, 31, 14);
		getContentPane().add(lblKey);
		
		//Button that causes the program to encode/decode a entered message
		JButton btnEncodedecode = new JButton("Encode/Decode");
		btnEncodedecode.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					String myMessage = textIn.getText();
					int myKey = Integer.parseInt(textKey.getText());
					if(myKey < -25 || myKey > 25)
					{
						throw new ArithmeticException("Out of desired bounds");
					}
					String output = encodeDecode(myMessage, myKey);
					textOut.setText(output);
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(getContentPane(), 
							"Please enter a whole number between -25 and 25 for the encryption key.");
					textKey.requestFocus();
					textKey.selectAll();
				}
			}
			
		});
		btnEncodedecode.setBounds(318, 171, 130, 23);
		getContentPane().add(btnEncodedecode);
		
		//Button moves output text to input text
		JButton btnMoveUp = new JButton("Move Up ^");
		btnMoveUp.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e)
			{
				String outText = textOut.getText();
				textIn.setText(outText);
				int slidValue = slider.getValue() * -1;
				slider.setValue(slidValue);
			}
		});
		btnMoveUp.setBounds(460, 171, 89, 23);
		getContentPane().add(btnMoveUp);
		
		
		//Adds slider to provide ease of use and key range
		//Encodes/decodes as number changes
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				textKey.setText("" + slider.getValue());
				String myMessage = textIn.getText();
				int myKey = slider.getValue();
				String myOutput = encodeDecode(myMessage, myKey);
				textOut.setText(myOutput);
			}
		});
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(13);
		slider.setMinorTickSpacing(1);
		slider.setValue(0);
		slider.setPaintTicks(true);
		slider.setMaximum(26);
		slider.setMinimum(-26);
		slider.setBackground(new Color(135, 206, 250));
		slider.setBounds(20, 162, 200, 41);
		getContentPane().add(slider);
		
	}

	//Takes in the desired message and key and encode or decodes
	//the message (symmetrical)
	public String encodeDecode (String message, int keyVal)
	{
		String output = "";
		String rvsedMessage = "";
		char key = (char) keyVal;
		
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
	
	//Starts program and opens up a window for the GUI
	public static void main(String[] args) 
	{
		SecretMessagesGUI myApp = new SecretMessagesGUI();
		myApp.setSize(new java.awt.Dimension(600, 400));
		myApp.setVisible(true);
	}
}
