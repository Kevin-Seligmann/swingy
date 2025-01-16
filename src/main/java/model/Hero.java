package model;

import jakarta.persistence.Entity;

@Entity
public class Hero  {
	private String name;
	private HeroType type;
	private int level;
	private int experience;
	private int attack;
	private int defense;
	private int hitPoints;
	private int helmStat;
	private int weaponStat;
	private int armorStat;

	public Hero(String name, HeroType type, int defaultAttack, int defaultDefense, int defaultHitPoints){
		this.name = name;
		this.type = type;
		this.attack = defaultAttack;
		this.defense = defaultDefense;
		this.hitPoints = defaultHitPoints;
		this.level = 1;
	}

	// Getters, setters, default constructor. (Hibernate needs).
	public Hero(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HeroType getType() {
		return type;
	}

	public void setType(HeroType type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	public int getHelmStat() {
		return helmStat;
	}

	public void setHelmStat(int helmStat) {
		this.helmStat = helmStat;
	}

	public int getWeaponStat() {
		return weaponStat;
	}

	public void setWeaponStat(int weaponStat) {
		this.weaponStat = weaponStat;
	}

	public int getArmorStat() {
		return armorStat;
	}

	public void setArmorStat(int armorStat) {
		this.armorStat = armorStat;
	}

	
}
