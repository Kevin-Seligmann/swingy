package view;

import java.util.InputMismatchException;
import java.util.Scanner;
import controller.Controller;

public class CLIView extends View {
	private Scanner sc;
	private Controller controller;

	public CLIView(Controller controller){
		this.controller = controller;
		sc = new Scanner(System.in);
	}

	public void welcomeMenu(){
		System.out.println("Welcome!");
		System.out.println("Choose an option.");
		System.out.println("1. Create a hero.");
		System.out.println("2. Select a hero.");
		System.out.println("0. Change view.");
		switch (getNumber(0, 2)) {
			case 0: controller.switchView(); break;
			case 1: controller.addHero(); break;
			case 2: controller.selectHero(); break;	
		}
	}

	private int getNumber(int min, int max){
		int rta = -1;

		while (true){
			try {
				rta = sc.nextInt();
				sc.nextLine();
				if (rta >= min && rta <= max)
					return rta;
			} catch(InputMismatchException e){
				sc.nextLine();
			}
			System.out.println("Choose a valid number.");
		}
	}
}
