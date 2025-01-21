package model;

public class Enemy {
	private int level;
	private int healthPoints;
	private int attack;
	private int defense;

	public Enemy(int level){
		this.level = level;
		healthPoints = level;
		attack = level;
		defense = level;
	}

	public int getLevel(){
		return level;
	}

	public int getHealthPoints(){
		return healthPoints;
	}

	public int getAttack(){
		return attack;
	}

	public int getDefense(){
		return defense;
	}

	public void setHealthPoints(int healthPoints){
		if (healthPoints > 0)
			this.healthPoints = healthPoints;
		else
			this.healthPoints = 0;
	}
}
