package view;

import model.Hero;

public abstract class View {
	private Hero currentHero;
	public abstract void welcomeMenu ();
	public abstract void addHeroMenu();
	public abstract void closeView();
	public abstract void loadView();
}
