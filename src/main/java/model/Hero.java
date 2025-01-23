package model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Hero {
	public static final int MAX_LEVEL = 10;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull(message = "Hero name cant be null")
	@Size(min = 2, max = 10, message = "Name must have a length between {min} and {max}")
	private String name;
	@NotNull(message = "Hero class cant be null")
	@Enumerated(EnumType.ORDINAL)
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
		setName(name);
		setType(type);
		setAttack(defaultAttack);
		setDefense(defaultDefense);
		setHitPoints(defaultHitPoints);
		setLevel(1);
	}

	public int getNextLevelExperience(){
		return level * 1000 + (level - 1) * (level - 1) * 450;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();

		sb.append("Hero '").append(name).append("'\n").
		append("Class: ").append(type.toString()).append("\n").
		append("Level: ").append(level).append(". ");
		if (level == MAX_LEVEL)
			sb.append(" (MAX). ");
		sb.append(" XP (").append(experience).append("/").append(getNextLevelExperience()).append(")\n").
		append("Attack ").append(attack).
		append(", HitPoints ").append(hitPoints).
		append(", Defense ").append(defense).append("\n").
		append("Artifacts:").
		append(" Weapon ").append(weaponStat).
		append(", Helm ").append(helmStat).
		append(", Armor ").append(armorStat).append("\n");
		if (type == HeroType.ENCHANTER){
			sb.append("Enchanter artifact bonus: ").
			append("Weapon ").append(weaponStat * enchanterArtifactMultiplier()).
			append(", Helm ").append(helmStat *  enchanterArtifactMultiplier()).
			append(", Armor ").append(armorStat * enchanterArtifactMultiplier()).append("\n");
		}
		return sb.toString();
	}

	public int enchanterArtifactMultiplier(){
		return level;
	}

	// Getters, setters, default constructor. (Hibernate needs).
	public Hero(){}

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
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

	public int getArmorStatWithBonus(){
		if (type == HeroType.ENCHANTER){
			return getArmorStat() * enchanterArtifactMultiplier();
		} else {
			return getArmorStat();
		}
	}

	public int getWeaponStatWithBonus(){
		if (type == HeroType.ENCHANTER){
			return getWeaponStat() * enchanterArtifactMultiplier();
		} else {
			return getWeaponStat();
		}
	}

	public int getHelmStatWithBonus(){
		if (type == HeroType.ENCHANTER){
			return getHelmStat() * enchanterArtifactMultiplier();
		} else {
			return getHelmStat();
		}
	}

	public void resetStats() {
		HeroFactory.resetStatsOnLevel(this);
	}
}
