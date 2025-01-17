package view;

import java.util.InputMismatchException;
import java.util.Scanner;
import controller.Controller;
import model.HeroType;
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
		int rta = -1;
	
		System.out.println("Choose an option.");
		System.out.println("1. Create a hero.");
		System.out.println("2. Select a hero.");
		System.out.println("9. Exit.");
		System.out.println("0. Change view.");
		while (rta < 0 || (rta > 2 && rta != 9))
			rta = getNumber();
		switch (rta) {
			case 0: controller.switchView(); break;
			case 1: controller.addHero(); break;
			case 2: controller.selectHero(); break;
			case 9: controller.exit();
		}
	}

	public void addHeroMenu() {
		int rta = -1;

		System.out.println("9. Exit.");
		System.out.println("0. Change view.");
		System.out.println("Hero name: ");
		String name = Utils.getScanner().nextLine();
		if (name.equals("9"))
			controller.exit();
		else if (name.equals("0"))
			controller.switchView();
		else {
			System.out.println("Select a hero type.");
			System.out.println("1. Enchanter.");
			System.out.println("2. Warrior.");
			System.out.println("3. Peasant.");
			System.out.println("9. Exit.");
			System.out.println("0. Change view.");
			while (rta < 0 || (rta > 3 && rta != 9))
				rta = getNumber();
			switch (rta) {
				case 0: controller.switchView(); break;
				case 1: controller.addHeroInput(name, HeroType.ENCHANTER); break;
				case 2: controller.addHeroInput(name, HeroType.WARRIOR); break;
				case 3: controller.addHeroInput(name, HeroType.PEASANT); break;
				case 9: controller.exit();
			}
		}

	}

	private int getNumber(){
		int rta = -1;

		while (true){
			try {
				rta = Utils.getScanner().nextInt();
				Utils.getScanner().nextLine();
				return rta;
			} catch(InputMismatchException e){
				Utils.getScanner().nextLine();
			}
			System.out.println("Choose a valid number.");
		}
	}

}
