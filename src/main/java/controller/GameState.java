package controller;

import model.Artifact;
import model.Hero;
import model.Map;
import model.MapCell;

public class GameState {
	private GameStateType gameStateType;
	private Map currentMap;
	private Hero currentHero;
	private MapCell targetCell;

	public GameState(){}

	public GameStateType getGameStateType() {
		return gameStateType;
	}

	public void setGameStateType(GameStateType gameStateType) {
		this.gameStateType = gameStateType;
	}

	public Map getCurrentMap() {
		return currentMap;
	}

	public void setCurrentMap(Map currentMap) {
		this.currentMap = currentMap;
	}

	public Hero getCurrentHero() {
		return currentHero;
	}

	public void setCurrentHero(Hero currentHero) {
		this.currentHero = currentHero;
	}

	public MapCell getTargetCell() {
		return targetCell;
	}

	public void setTargetCell(MapCell targetCell) {
		this.targetCell = targetCell;
	}
}
