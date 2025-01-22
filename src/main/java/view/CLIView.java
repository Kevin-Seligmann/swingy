package view;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import controller.Controller;
import controller.UserInput;
import model.Artifact;
import model.Hero;
import model.HeroType;
import model.Map;
import util.ScannerProvider;

public class CLIView extends View {
	private Controller controller;

	public CLIView(){}

	public CLIView(Controller controller){
		this.controller = controller;
	}

	public void setController(Controller controller){
		this.controller = controller;
	}

	public void closeView() {
		//
	}

	public void welcomeMenu(){
		Set<String> options = Set.of(
			"1", "create",
			"2", "select",
			"3", "peasant",
			"e", "exit",
			"v", "view"
		);
	
		printSeparator();
		System.out.println("1. Create a hero.");
		System.out.println("2. Select a hero.");
		System.out.println("E. Exit.");
		System.out.println("V. Change view.");
		switch (getConsoleInput(options, "Choose an option.")) {
			case "create":
			case "1": controller.onAddHeroSelected(); break;
			case "select":
			case "2": controller.onSelectHeroSelected(); break;
			case "exit":
			case "e": controller.onExitSelected(); break;
			case "view":
			case "v": controller.onSwitchViewSelected(); break;
		}
	}

	public void selectHeroMenu(List<Hero> heroes){
		if (heroes.isEmpty()){
			notifyUser("There are no heroes created.");
			controller.welcomeMenu();
			return ;
		}

		Set<String> options = new HashSet<>(List.of(
			"b", "back",
			"e", "exit",
			"v", "view"
		));
		
		Integer id = 1;
		printSeparator();
		for (Hero hero: heroes){
			System.out.println(id + ". " + hero);
			options.add(id.toString());
			options.add(hero.getName());
			id ++;
		}

		System.out.println("B. Back.");
		System.out.println("E. Exit.");
		System.out.println("V. Change view.");
		String input = getConsoleInput(options, "Select a hero by their id or name.");
		switch (input) {
			case "back":
			case "b": controller.welcomeMenu(); return;
			case "exit":
			case "e": controller.onExitSelected(); return;
			case "view":
			case "v": controller.onSwitchViewSelected(); return;
		}

		Hero selectedHero = null;
		for (Hero hero: heroes){
			if (hero.getName().equals(input)){
				selectedHero = hero;
				break ;
			}
		}
		if (selectedHero == null)
			selectedHero = heroes.get(Integer.parseInt(input) - 1);
		controller.onSelectHero(selectedHero);
	}

	public void addHeroMenu() {
		printSeparator();
		System.out.println("B. Back.");
		System.out.println("E. Exit.");
		System.out.println("V. Change view.");
		String nameInput = getConsoleInput(null, "Hero name: ");
		if (nameInput.equals("e") || nameInput.equals("exit"))
			controller.onExitSelected();
		else if (nameInput.equals("v") || nameInput.equals("view"))
			controller.onSwitchViewSelected();
		else if (nameInput.equals("b") || nameInput.equals("back"))
			controller.welcomeMenu();
		else {
			Set<String> options = Set.of(
				"1", "enchanter",
				"2", "warrior",
				"3", "peasant",
				"c", "cancel",
				"e", "exit",
				"v", "view"
			);

			printSeparator();
			System.out.println("1. Enchanter.");
			System.out.println("2. Warrior.");
			System.out.println("3. Peasant.");
			System.out.println("C. Cancel.");
			System.out.println("E. Exit.");
			System.out.println("V. Change view.");
			switch (getConsoleInput(options, "Select a class")) {
				case "enchanter":
				case "1": controller.onAddHero(nameInput, HeroType.ENCHANTER); break;
				case "warrior":
				case "2": controller.onAddHero(nameInput, HeroType.WARRIOR); break;
				case "peasant":
				case "3": controller.onAddHero(nameInput, HeroType.PEASANT); break;
				case "cancel":
				case "c": controller.welcomeMenu(); break;
				case "exit":
				case "e": controller.onExitSelected(); break;
				case "view":
				case "v": controller.onSwitchViewSelected(); break;
			}
		}
	}

	public void notifyUser(String string) {
		printSeparator();
		System.out.println(string);
		System.out.print("Press enter to continue.");
		ScannerProvider.getScanner().nextLine();
	}

	public void selectedHeroMenu(Hero hero) {
		Set<String> options = Set.of(
			"p", "play",
			"r", "remove",
			"b", "back",
			"e", "exit",
			"v", "view"
		);

		printSeparator();
		System.out.println(hero);
		System.out.println("P. Play.");
		System.out.println("R. Remove.");
		System.out.println("B. Back");
		System.out.println("E. Exit.");
		System.out.println("V. Change view.");
		switch (getConsoleInput(options, "Select an option.")) {
			case "play":
			case "p": controller.onPlayHeroSelected(); break;
			case "remove":
			case "r":controller.onRemoveHeroSelected(); break;
			case "back":
			case "b": controller.welcomeMenu(); break;
			case "exit":
			case "e": controller.onExitSelected(); break;
			case "view":
			case "v": controller.onSwitchViewSelected(); break;
		}
	}

	public void showMap(Map currentMap, Hero currentHero){
		printSeparator();
		System.out.println(currentMap);
		System.out.println("Map size: " + currentMap.getSize());
		System.out.println("Hero position: " + (currentMap.getHeroCell().getX() + 1) + ", " + (currentMap.getHeroCell().getY() + 1));
		System.out.println(currentHero);
		movementMenu();
	}

	private void movementMenu(){
		Set<String> options = Set.of(
			"a", "west",
			"d", "east",
			"w", "north",
			"s", "south",
			"q", "quit",
			"e", "exit",
			"v", "view"
		);
	
		System.out.println("A. Move west.");
		System.out.println("D. Move east.");
		System.out.println("W. Move north.");
		System.out.println("S. Move south.");
		System.out.println("Q. Quit map.");
		System.out.println("E. Exit.");
		System.out.println("V. Change view.");
		switch (getConsoleInput(options, "Choose a direction.")) {
			case "west":
			case "a": controller.onMove(UserInput.WEST); break;
			case "east":
			case "d": controller.onMove(UserInput.EAST); break;
			case "north":
			case "w": controller.onMove(UserInput.NORTH); break;
			case "south":
			case "s": controller.onMove(UserInput.SOUTH); break;
			case "quit":
			case "q": controller.onSelectedHero();
			case "exit":
			case "e": controller.onExitSelected(); break;
			case "view":
			case "v": controller.onSwitchViewSelected(); break;
		}
	}

	public void preFightMenu(int enemyLevel) {
		Set<String> options = Set.of(
			"1", "fight",
			"2", "run",
			"e", "exit",
			"v", "view"
		);
	
		printSeparator();
		System.out.println("Enemy level " + enemyLevel + " found!!\n");
		System.out.println("1. Fight.");
		System.out.println("2. Run. ");
		System.out.println("E. Exit.");
		System.out.println("V. Change view.");
		switch (getConsoleInput(options, "Fight or flee.")) {
			case "fight":
			case "1": controller.onFight(); break;
			case "run":
			case "2": controller.onRun(); break;
			case "exit":
			case "e": controller.onExitSelected(); break;
			case "view":
			case "v": controller.onSwitchViewSelected(); break;
		}
	}

	public void showArtifactMenu(Hero hero, Artifact artifact){
		Set<String> options = Set.of(
			"1", "accept", "y", "yes",
			"2",  "reject", "n", "no",
			"e", "exit",
			"v", "view"
		);

		System.out.println(hero);
		System.out.println(artifact);
		System.out.println("1. Accept artifact.");
		System.out.println("2. Reject artifact. ");
		System.out.println("E. Exit.");
		System.out.println("V. Change view.");
		switch (getConsoleInput(options, "Decide if you want to keep the artifact.")) {
			case "y":
			case "yes":
			case "accept":
			case "1": controller.onAcceptArtifact(); break;
			case "n":
			case "no":
			case "reject":
			case "2": controller.onRejectArtifact(); break;
			case "exit":
			case "e": controller.onExitSelected(); break;
			case "view":
			case "v": controller.onSwitchViewSelected(); break;
		}
	}

	private String getConsoleInput(Set<String> validInputs, String prompt){
		String input;

		if (prompt != null)
			System.out.println(prompt);
		while (true){
			input = ScannerProvider.getScanner().nextLine().trim().toLowerCase();
			if (!input.isEmpty() && (validInputs == null || validInputs.contains(input)))
				break;
			System.out.println("Invalid option, try again.");
		}
		return input;
	}

	private void printSeparator(){
		System.out.println("\n-------------------- S.W.I.N.G.Y --------------------\n");
	}
}
