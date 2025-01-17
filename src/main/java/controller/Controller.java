package controller;

import view.GUIView;
import view.View;
import model.Hero;
import model.HeroType;
import model.Model;
import view.CLIView;

public class Controller {
	private GameState gameState;
	private View currentView;
	private Model model;
	private boolean running;

	public Controller(String viewType){
		if (viewType.equalsIgnoreCase("console"))
			currentView = new CLIView(this);
		else if (viewType.equalsIgnoreCase("gui"))
			currentView = new GUIView(this);
		model = new Model();
		gameState = GameState.WELCOME_SCREEN;
	}

	public void run(){
		running = true;
		while (running){
			switch(gameState){
				case WELCOME_SCREEN: currentView.welcomeMenu();; break;
				case SELECT_HERO: currentView.selectHeroMenu(model.getHeroes());; break;
				case CREATE_HERO: currentView.addHeroMenu();; break;
				case SELECTED_HERO: currentView.selectedHeroMenu(model.getCurrentHero()); break;
				case MAP: currentView.showMap(model.getCurrentMap()); break;
				default: break;
			}
		}
		currentView.closeView();
	}

	public void welcomeMenu(){
		gameState = GameState.WELCOME_SCREEN;
	}

	public void onAddHeroSelected(){
		gameState = GameState.CREATE_HERO;
	}

	public void onSelectHeroSelected(){
		gameState = GameState.SELECT_HERO;
	}

	public void onSelectedHero(){
		gameState = GameState.SELECTED_HERO;
	}

	public void onPlayHeroSelected(){
		model.generateMap();
		gameState = GameState.MAP;
	}

	public void onExitSelected(){
		running = false;
	}

	public void onSwitchViewSelected(){
		currentView.closeView();
		if (currentView instanceof CLIView)
			currentView = new GUIView(this);
		else if (currentView instanceof GUIView)
			currentView = new CLIView(this);
	}

	public void onAddHero(String name, HeroType type){
		model.addHero(name, type);
		currentView.notifyUser("Hero added.");
		gameState = GameState.WELCOME_SCREEN;
	}


	public void onSelectHero(Hero hero) {
		model.setCurrentHero(hero);
		gameState = GameState.SELECTED_HERO;
	}

	public void onRemoveHeroSelected() {
		model.removeHero(model.getCurrentHero());
		currentView.notifyUser("Hero removed.");
		gameState = GameState.WELCOME_SCREEN;
	}
}
