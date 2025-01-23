package controller;

import view.GUIView;
import view.View;
import model.Artifact;
import model.ArtifactType;
import model.Enemy;
import model.Hero;
import model.HeroType;
import model.Map;
import model.MapCell;
import model.Model;
import util.DatabaseErrorException;
import util.ModelValidationException;
import util.ScannerProvider;
import view.CLIView;

public class Controller {
	private GameState gameState;
	private View currentView;
	private Model model;

	public Controller(View view, Model model){
		this.currentView = view;
		this.model = model;
	}

	public GameState getGameState(){
		return this.gameState;
	}

	public void run(){
		this.gameState = new GameState();
		gameState.setGameStateType(GameStateType.WELCOME_SCREEN);
		showView();
	}

	private void showView(){
		if (currentView == null)
			return ;
		switch(gameState.getGameStateType()){
			case WELCOME_SCREEN: currentView.welcomeMenu(); break;
			case SELECT_HERO: currentView.selectHeroMenu(model.getHeroes()); break;
			case CREATE_HERO: currentView.addHeroMenu(); break;
			case SELECTED_HERO: currentView.selectedHeroMenu(gameState.getCurrentHero()); break;
			case MAP: currentView.showMap(gameState.getCurrentMap(), gameState.getCurrentHero()); break;
			case PRE_FIGHT_MENU: currentView.preFightMenu(gameState.getTargetCell().getEnemy().getLevel()); break ;
			case ARTIFACT_MENU: currentView.showArtifactMenu(gameState.getCurrentHero(), gameState.getTargetCell().getArtifact()); break ;
		}
	}

	public void onSwitchViewSelected(){
		currentView.closeView();
		if (currentView instanceof CLIView)
			currentView = new GUIView(this);
		else if (currentView instanceof GUIView)
			currentView = new CLIView(this);
		showView();
	}

	public void welcomeMenu(){
		gameState.setGameStateType(GameStateType.WELCOME_SCREEN);
		showView();
	}

	public void onAddHeroSelected(){
		gameState.setGameStateType(GameStateType.CREATE_HERO);
		showView();
	}

	public void onSelectHeroSelected(){
		gameState.setGameStateType(GameStateType.SELECT_HERO);
		showView();
	}

	public void onSelectedHero(){
		gameState.setGameStateType(GameStateType.SELECTED_HERO);
		showView();
	}

	public void onPlayHeroSelected(){
		gameState.setCurrentMap(new Map(gameState.getCurrentHero().getLevel()));
		gameState.setGameStateType(GameStateType.MAP);
		showView();
	}

	public void onExitSelected(){
		if (currentView != null)
			currentView.closeView();
		currentView = null;
		model.freeResources();
		ScannerProvider.getScanner().close();
	}

	public void onAddHero(String name, HeroType type){
		try {
			safeAddHero(name, type);
			if (currentView != null)
				currentView.notifyUser("Hero added.");	
		} catch (ModelValidationException e){
			currentView.notifyUser(e.getMessage());
		}
		gameState.setGameStateType(GameStateType.WELCOME_SCREEN);
		showView();
	}

	public void onSelectHero(Hero hero) {
		gameState.setCurrentHero(hero);
		gameState.setGameStateType(GameStateType.SELECTED_HERO);
		showView();
	}

	public void onSelectHeroById(int id) {
		Hero hero = model.getHero(id);
		gameState.setCurrentHero(hero);
		gameState.setGameStateType(GameStateType.SELECTED_HERO);
		showView();
	}

	public void onRemoveHeroSelected() {
		safeRemoveHero();
		if (currentView != null)
			currentView.notifyUser("Hero removed.");
		gameState.setGameStateType(GameStateType.WELCOME_SCREEN);
		showView();
	}

	public void onMove(UserInput input) {
		MapCell targetCell = getTargetCell(input);
		gameState.setTargetCell(targetCell);

		if (targetCell.isEnemy()){{
			gameState.setGameStateType(GameStateType.PRE_FIGHT_MENU);
			showView();
		}
		} else {
			move();	
		}
	}

	public void onRun(){
		if (Math.random() < 0.5){
			currentView.notifyUser("You run from the fight.");
			gameState.setGameStateType(GameStateType.MAP);
			showView();
		}
		else {
			currentView.notifyUser("You fight.");
			onFight();
		}
	}

	public void onFight() {
		FightResult fightResult = fight();
		if (fightResult == FightResult.LOSE){
			currentView.notifyUser("You died.");
			safeRemoveHero();
			gameState.setGameStateType(GameStateType.WELCOME_SCREEN);
			showView();
		} else {
			currentView.notifyUser("You won the fight.");
			handleLevel();
			if (gameState.getTargetCell().hasArtifact())
				handleArtifact();
			else
				move();
		}
	}

	public void move(){
		MapCell targetCell = gameState.getTargetCell();
		Map map = gameState.getCurrentMap();

		if (map.isCellOnEdge(targetCell)) {
			currentView.notifyUser("Map edge reached, you won!");
			gameState.setGameStateType(GameStateType.SELECTED_HERO);
			showView();
		} else {
			map.setHeroCell(targetCell);
			gameState.setGameStateType(GameStateType.MAP);
			showView();
		}
	}

	public FightResult fight(){
		int heroAttack;
		int heroDefense;
		int heroHealth;

		Hero hero = gameState.getCurrentHero();
		Enemy enemy = gameState.getTargetCell().getEnemy();

		if (hero.getType() == HeroType.ENCHANTER){
			heroAttack = hero.getAttack() + hero.getWeaponStat() * hero.enchanterArtifactMultiplier();
			heroDefense = hero.getDefense() + hero.getArmorStat() * hero.enchanterArtifactMultiplier();
			heroHealth = hero.getHitPoints() + hero.getHelmStat() * hero.enchanterArtifactMultiplier();
		} else {
			heroAttack = hero.getAttack() + hero.getWeaponStat();
			heroDefense = hero.getDefense() + hero.getArmorStat();
			heroHealth = hero.getHitPoints() + hero.getHelmStat();
		}

		int enemyAttackPerHit = enemy.getAttack() - heroDefense;
		if (enemyAttackPerHit <= 0)
			enemyAttackPerHit = Math.max(1, enemy.getAttack() / 10);

		int heroAttackPerHit = heroAttack - enemy.getDefense();
		if (heroAttackPerHit <= 0)
			heroAttackPerHit = Math.max(1, heroAttack / 10);

		double neededHitsByEnemy = (double) heroHealth / enemyAttackPerHit;
		double neededHitByHero = (double) enemy.getHealthPoints() / heroAttackPerHit;
		double maxHitDelta = 1;
		double hitDelta = 2 * (Math.random() - 0.5) * maxHitDelta;
		if (neededHitByHero + hitDelta > neededHitsByEnemy)
			return FightResult.LOSE;
		else
			return FightResult.WIN;
	}

	public void handleArtifact(){
		gameState.setGameStateType(GameStateType.ARTIFACT_MENU);
		showView();
	}

	public void onAcceptArtifact(){
		Hero hero = gameState.getCurrentHero();
		Artifact artifact = gameState.getTargetCell().getArtifact();
		
		switch (artifact.getType()) {
			case ArtifactType.ARMOR: hero.setArmorStat(artifact.getStatModifier()); break;
			case ArtifactType.WEAPON: hero.setWeaponStat(artifact.getStatModifier()); break;
			case ArtifactType.HELM: hero.setHelmStat(artifact.getStatModifier()); break;
		}
		safeUpdateHero();
		move();
	}

	public void onRejectArtifact() {
		move();
	}

	private void handleLevel(){
		Hero hero = gameState.getCurrentHero();
		Enemy enemy = gameState.getTargetCell().getEnemy();
		int enemyLevel = enemy.getLevel();
		int expGained = (enemyLevel * 1000 + (enemyLevel - 1) * (enemyLevel - 1) * 450) / 5;
		
		if (hero.getLevel() == Hero.MAX_LEVEL){
			return ;
		}
		hero.setExperience(hero.getExperience() + expGained);
		if (hero.getExperience() >= hero.getNextLevelExperience()){
			hero.setExperience(0);
			hero.setLevel(hero.getLevel() + 1);
			hero.resetStats();
			currentView.notifyUser("You leveled up!");
		}
		safeUpdateHero();
	}

	private MapCell getTargetCell(UserInput input){
		Map map = gameState.getCurrentMap();
		MapCell heroCell = map.getHeroCell();
	
		switch (input) {
			case SOUTH: return map.getCell(heroCell.getX(), heroCell.getY() - 1);
			case WEST: return map.getCell(heroCell.getX() - 1, heroCell.getY());
			case NORTH: return map.getCell(heroCell.getX(), heroCell.getY() + 1);
			case EAST: return map.getCell(heroCell.getX() + 1, heroCell.getY());
			default: return null;
		}
	}

	private void safeUpdateHero(){
		try {
			model.updateHero(gameState.getCurrentHero());
		} catch  (DatabaseErrorException e){
			System.out.println(e.getMessage());
			onExitSelected();
		} 
	}

	private void safeAddHero(String name, HeroType type){
		try {
			model.addHero(name, type);
		} catch  (DatabaseErrorException e){
			System.out.println(e.getMessage());
			onExitSelected();
		} 
	}

	private void safeRemoveHero(){
		try {
			model.removeHero(gameState.getCurrentHero());
		} catch  (DatabaseErrorException e){
			System.out.println(e.getMessage());
			onExitSelected();
		} 
	}
}
