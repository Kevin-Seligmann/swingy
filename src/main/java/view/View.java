package view;

import java.util.List;

import model.Hero;
import model.Map;

public abstract class View {
	public abstract void welcomeMenu ();
	public abstract void addHeroMenu();
	public abstract void closeView();
	public abstract void selectHeroMenu(List<Hero> heroes);
	public abstract void notifyUser(String string);
	public abstract void selectedHeroMenu(Hero hero);
	public abstract void showMap(Map map);
}
