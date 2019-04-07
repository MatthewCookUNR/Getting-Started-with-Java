import java.util.*;

//Example object
public class Animal 
{
	//example data members
	//Static means it is shared by all instances of object
	//Final means the value cannot be changed
	public static final String livingType = "Animal";
	private String name;
	private int weight;
	private boolean hasOwner = false;
	private byte age;
	private int speed;
	
	//Scanner used to get input from console
	static Scanner userInput = new Scanner(System.in);
	
	public Animal()
	{
		int bigNumber = 2131321;
		
		System.out.println(bigNumber);
		
		System.out.print("Enter name: \n");
		
		//hasNextLine, hasNextInt, hasNextFloat, hasNextDouble, 
		//hasNextByte (check for correct data type)
		
		//NextLine, NextInt, NextFloat, NextDouble, 
		//NextByte (retrieve)
		
		if(userInput.hasNextLine()) //Get a string from console input
		{
			this.setName(userInput.nextLine());
			System.out.println(name);
		}
		this.setAge((byte)5);
		this.setSpeed();
		switch( (int) age )
		{
			case 5:
				System.out.println("Byte is 5");
				break;
			
			default: 
				System.out.println("Byte is not 5");
				break;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public boolean isHasOwner() {
		return hasOwner;
	}

	public void setHasOwner(boolean hasOwner) {
		this.hasOwner = hasOwner;
	}

	public byte getAge() {
		return age;
	}

	public void setAge(byte age) {
		this.age = age;
	}

	public int getSpeed() {
		return speed;
	}

	//Basic set function that takes a integer input
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	//Overload of set function to set a random speed if 
	//nothing is inputed
	public void setSpeed() {
		int minNumber = 1;
		int maxNumber = 1000;
		this.speed = minNumber + (int) (Math.random() * ((maxNumber - minNumber) + 1));
	}

	//Example function to test a loop and continue keyword
	protected static void countTo(int startingNumber)
	{
		for(int i = startingNumber; i <= 100; i++)
		{
			//Continue is used to essentially skip a increment
			//of the loop if a condition is true (break 1 iteration)
			if(i == 55) continue;
			System.out.println(i);
		}
	}
	
	public String makeSound()
	{
		return "Roar";
	}
	
	public static void speakAnimal(Animal randAnimal)
	{
		System.out.println("Animal says " + randAnimal.makeSound());
	}
	
	public static void main(String[] args)
	{
		Animal myAnimal = new Animal();
		
		//Examples of using arrays below
		int[] favNumbers = new int[20];
		favNumbers[0] = 100;
		
		String[] stringArray = {"Here", "Random", "Words"};
		for(String word : stringArray)
		{
			System.out.println(word);
		}
		
		String[] cloneOfArray = Arrays.copyOf(stringArray, 3);
		
		System.out.println(Arrays.toString(cloneOfArray));
		
		System.out.println(Arrays.binarySearch(cloneOfArray, "Here"));
		
		//Function is accessed statically below
		Animal.countTo(50);
		
		userInput.close();
	}
}