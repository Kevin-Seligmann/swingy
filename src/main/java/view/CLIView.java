package view;

import java.util.InputMismatchException;
import java.util.List;
import controller.Controller;
import model.Hero;
import model.HeroType;
import model.Map;
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
	
		printSeparator();
		System.out.println("Choose an option.");
		System.out.println("1. Create a hero.");
		System.out.println("2. Select a hero.");
		System.out.println("9. Exit.");
		System.out.println("0. Change view.");
		while (rta < 0 || (rta > 2 && rta != 9))
			rta = getNumber();
		switch (rta) {
			case 0: controller.onSwitchViewSelected(); break;
			case 1: controller.onAddHeroSelected(); break;
			case 2: controller.onSelectHeroSelected(); break;
			case 9: controller.onExitSelected();
		}
	}

	public void selectHeroMenu(List<Hero> heroes){
		int id = 1;
		int rta = -1;
	
		printSeparator();
		for (Hero hero: heroes){
			System.out.println(id + ". " + hero);
			id ++;
		}
		while (rta < 1 || rta > id)
			rta = getNumber();
		controller.onSelectHero(heroes.get(rta));
	}

	public void addHeroMenu() {
		int rta = -1;

		printSeparator();
		System.out.println("9. Exit.");
		System.out.println("0. Change view.");
		System.out.println("Hero name: ");
		String name = Utils.getScanner().nextLine();
		if (name.equals("9"))
			controller.onExitSelected();
		else if (name.equals("0"))
			controller.onSwitchViewSelected();
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
				case 0: controller.onSwitchViewSelected(); break;
				case 1: controller.onAddHero(name, HeroType.ENCHANTER); break;
				case 2: controller.onAddHero(name, HeroType.WARRIOR); break;
				case 3: controller.onAddHero(name, HeroType.PEASANT); break;
				case 9: controller.onExitSelected();
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

	public void notifyUser(String string) {
		printSeparator();
		System.out.println(string);
		System.out.println("Press space bar to continue");
		Utils.getScanner().nextLine();
	}

	private void printSeparator(){
		System.out.println("\n-------------------- S.W.I.N.G.Y --------------------\n");
	}

	public void selectedHeroMenu(Hero hero) {
		int rta = -1;
		
		printSeparator();
		System.out.println("HERO \n");
		System.out.println(hero);
		System.out.println("Choose an option.");
		System.out.println("1. Play.");
		System.out.println("2. Remove.");
		System.out.println("9. Exit.");
		System.out.println("0. Change view.");
		while (rta < 0 || (rta > 2 && rta != 9))
			rta = getNumber();
		switch (rta) {
			case 0: controller.onExitSelected();
			case 1: controller.onPlayHeroSelected();
			case 2: controller.onRemoveHeroSelected();
			case 9: controller.onExitSelected();
		}
	}

	public void showMap(Map map){

	}

}
