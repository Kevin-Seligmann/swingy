package controller;

import view.GUIView;
import view.View;
import model.HeroType;
import model.Model;
import view.CLIView;

public class Controller {
	private GameState gameState;
	private View currentView;
	private Model model;

	public Controller(){
		model = new Model();
	}

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
		gameState = GameState.CREATE_HERO;
		currentView.addHeroMenu();
	}

	public void addHeroInput(String name, HeroType type){
		// currentView.notifyUser("Hero added");
		model.addHero(name, type);
		gameState = GameState.WELCOME_SCREEN;
		currentView.welcomeMenu();
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
