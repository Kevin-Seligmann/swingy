package model;

import model.Armor;
import model.Artifact;
import model.Helmet;

public class ArtifactFactory {
	private static ArtifactFactory instance;

	private ArtifactFactory(){}

	public static ArtifactFactory getInstance(){
		if (instance == null)
			instance = new ArtifactFactory();
		return instance;
	}

	public Artifact createArtifact(String type, int modifier){
		if (type.equalsIgnoreCase("armor")){
			return new Armor(modifier);
		}
		else if (type.equalsIgnoreCase("weapon")){
			return new Weapon(modifier);
		}
		else if (type.equalsIgnoreCase("helmet")){
			return new Helmet(modifier);
		}
		else {
			return null;
		}
	}
}
