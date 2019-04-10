import java.util.Scanner;

//Program implements a classic Caesar cipher to encode and decode messages
//Also reverses the message to make the message more confusing
public class SecretMessages 
{
	public static void main(String[] args) 
	{
		Scanner myScan = new Scanner(System.in);
		System.out.println("Enter a message to encode or decode or press ENTER to quit:");
		String message = myScan.nextLine();
		
		//User can encode and decode until a message is not entered
		while(message.length() > 0)
		{
			//Asks user to enter a key used for the cipher
			int keyVal = 0;
			while(keyVal == 0)
			{
				try
				{
					System.out.println("Enter a secret key between -25 to 25");
					keyVal = Integer.parseInt(myScan.nextLine());
					if(keyVal < -25 || keyVal > 25)
					{
						throw new ArithmeticException("swag");
					}
				}
				catch(Exception e)
				{
					keyVal = 0;
				}
			}
			char key = (char) keyVal;
			String output = "";
			String rvsedMessage = "";
			
			//Reverses the message
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
			System.out.println(output);
			System.out.println("Enter a message to encode or decode or press ENTER to quit:");
			message = myScan.nextLine();
		}
		myScan.close();
	}
}
