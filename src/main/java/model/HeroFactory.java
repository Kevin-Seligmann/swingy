package model;

public class HeroFactory {
	private static int ENCHANTER_DEFAULT_HP = 5;
	private static int ENCHANTER_DEFAULT_ATK = 5;
	private static int ENCHANTER_DEFAULT_DEF = 5;

	private static int WARRIOR_DEFAULT_HP = 10;
	private static int WARRIOR_DEFAULT_ATK = 10;
	private static int WARRIOR_DEFAULT_DEF = 10;

	private static int PEASANT_DEFAULT_HP = 3;
	private static int PEASANT_DEFAULT_ATK = 3;
	private static int PEASANT_DEFAULT_DEF = 3;

	public static Hero getHero(String name, HeroType type){
		switch (type) {
			case HeroType.ENCHANTER: return new Hero(name, type, ENCHANTER_DEFAULT_ATK, ENCHANTER_DEFAULT_DEF, ENCHANTER_DEFAULT_HP);
			case HeroType.PEASANT: new Hero(name, type, PEASANT_DEFAULT_ATK, PEASANT_DEFAULT_DEF, PEASANT_DEFAULT_HP);
			case HeroType.WARRIOR: new Hero(name, type, WARRIOR_DEFAULT_ATK, WARRIOR_DEFAULT_DEF, WARRIOR_DEFAULT_HP);
			default: return null;
		}
	}
}
