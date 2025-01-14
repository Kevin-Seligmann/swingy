package model;

public class Weapon extends Artifact {
	public Weapon(int modifier){
		this.attackDelta = modifier;
	}
}
