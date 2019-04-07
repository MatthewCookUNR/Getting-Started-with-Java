//Extend keyword allows inheritance
public class Cat extends Animal
{
	public Cat()
	{
		
	}
	
	public String makeSound()
	{
		return "Meow";
	}
	
	public static void main(String[] args)
	{
		//Example of polymorphism uses below
		Animal alfredo = new Dog();
		Animal willis = new Cat();
		
		Animal[] theAnimals = new Animal[10];
		theAnimals[0] = alfredo;
		theAnimals[1] = willis;
		
		System.out.println("Alfredo says " + theAnimals[0].makeSound());
		System.out.println("Willis says " + theAnimals[1].makeSound());
		
		speakAnimal(alfredo);
		speakAnimal(willis);
		
	}
}
