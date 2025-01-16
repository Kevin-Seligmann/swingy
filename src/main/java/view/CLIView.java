package view;

import java.util.InputMismatchException;
import java.util.Scanner;
import controller.Controller;
import util.Utils;

public class CLIView extends View {
	private Controller controller;

	public CLIView(Controller controller){
		this.controller = controller;
	}

	public void closeView() {
		//
	}

	public void welcomeMenu(){
		System.out.println("Welcome!");
		System.out.println("Choose an option.");
		System.out.println("1. Create a hero.");
		System.out.println("2. Select a hero.");
		System.out.println("3. Exit.");
		System.out.println("0. Change view.");
		switch (getNumber(0, 3)) {
			case 0: controller.switchView(); break;
			case 1: controller.addHero(); break;
			case 2: controller.selectHero(); break;
			case 3: controller.exit();
		}
	}

	public void addHeroMenu() {
	}

	private int getNumber(int min, int max){
		int rta = -1;

		while (true){
			try {
				rta = Utils.getScanner().nextInt();
				Utils.getScanner().nextLine();
				if (rta >= min && rta <= max)
					return rta;
			} catch(InputMismatchException e){
				Utils.getScanner().nextLine();
			}
			System.out.println("Choose a valid number.");
		}
	}

}
