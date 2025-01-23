package view;

import java.util.List;

import controller.Controller;
import model.Artifact;
import model.Hero;
import model.Map;

public abstract class View {
	public abstract void setController(Controller controller);
	public abstract void welcomeMenu ();
	public abstract void addHeroMenu();
	public abstract void closeView();
	public abstract void selectHeroMenu(List<Hero> heroes);
	public abstract void notifyUser(String string);
	public abstract void selectedHeroMenu(Hero hero);
	public abstract void showMap(Map currentMap, Hero currentHero);
	public abstract void preFightMenu(int enemyLevel);
	public abstract void showArtifactMenu(Hero hero, Artifact artifact);
}
