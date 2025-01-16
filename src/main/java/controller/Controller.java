package controller;

import view.GUIView;
import view.View;
import view.CLIView;

public class Controller {
	private GameState gameState;
	private View currentView;

	public Controller(){}

	public void run(String viewType){
		if (viewType.equalsIgnoreCase("console"))
			currentView = new CLIView(this);
		else if (viewType.equalsIgnoreCase("gui"))
			currentView = new GUIView(this);
		welcomeMenu();
	}

	public void welcomeMenu(){
		gameState = GameState.WELCOME_SCREEN;
		currentView.welcomeMenu();
	}

	public void addHero(){
		gameState = GameState.WELCOME_SCREEN; // Until hero creation is implemented.
		System.out.println("Creating hero");
		currentView.welcomeMenu();  // Until hero creation is implemented.
		// currentView.addHeroMenu();
	}

	public void selectHero(){
		gameState = GameState.WELCOME_SCREEN; // Until hero selection is implemented.
		System.out.println("Hero selected");
		currentView.welcomeMenu();  // Until hero selection is implemented.
		// currentView.selectHero();
	}

	public void switchView(){
		currentView.closeView();
		if (currentView instanceof CLIView)
			currentView = new GUIView(this);
		else if (currentView instanceof GUIView)
			currentView = new CLIView(this);
		switch(gameState){
			case GameState.WELCOME_SCREEN: welcomeMenu(); break;
			case GameState.SELECT_HERO: selectHero(); break;
			case GameState.CREATE_HERO: addHero(); break;
			case GameState.MAP: break;
		}
	}

	public void exit(){
		currentView.closeView();
	}
}
