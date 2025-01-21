package model;

public class HeroFactory {
	private static int ENCHANTER_DEFAULT_HP = 2;
	private static int ENCHANTER_DEFAULT_ATK = 2;
	private static int ENCHANTER_DEFAULT_DEF = 2;

	private static int WARRIOR_DEFAULT_HP = 5;
	private static int WARRIOR_DEFAULT_ATK = 5;
	private static int WARRIOR_DEFAULT_DEF = 5;

	private static int PEASANT_DEFAULT_HP = 1;
	private static int PEASANT_DEFAULT_ATK = 1;
	private static int PEASANT_DEFAULT_DEF = 1;

	public static Hero getHero(String name, HeroType type){
		switch (type) {
			case HeroType.ENCHANTER: return new Hero(name, type, ENCHANTER_DEFAULT_ATK, ENCHANTER_DEFAULT_DEF, ENCHANTER_DEFAULT_HP);
			case HeroType.PEASANT: return new Hero(name, type, PEASANT_DEFAULT_ATK, PEASANT_DEFAULT_DEF, PEASANT_DEFAULT_HP);
			case HeroType.WARRIOR: return new Hero(name, type, WARRIOR_DEFAULT_ATK, WARRIOR_DEFAULT_DEF, WARRIOR_DEFAULT_HP);
			default: return null;
		}
	}

	public static void resetStatsOnLevel(Hero hero) {
        switch (hero.getType()) {
            case ENCHANTER:
                hero.setAttack(ENCHANTER_DEFAULT_ATK  - 1 + hero.getLevel());
                hero.setDefense(ENCHANTER_DEFAULT_DEF - 1 + hero.getLevel());
                hero.setHitPoints(ENCHANTER_DEFAULT_HP - 1 + hero.getLevel());
                break;
            case PEASANT:
                hero.setAttack(PEASANT_DEFAULT_ATK - 1 + hero.getLevel());
                hero.setDefense(PEASANT_DEFAULT_DEF - 1 + hero.getLevel());
                hero.setHitPoints(PEASANT_DEFAULT_HP - 1 + hero.getLevel());
                break;
            case WARRIOR:
                hero.setAttack(WARRIOR_DEFAULT_ATK - 1 + hero.getLevel());
                hero.setDefense(WARRIOR_DEFAULT_DEF - 1 + hero.getLevel() );
                hero.setHitPoints(WARRIOR_DEFAULT_HP - 1 + hero.getLevel());
                break;
        }
    }
}
