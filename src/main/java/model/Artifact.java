package model;

public class Artifact {
	private int statModifier;
	private ArtifactType type;

	public Artifact(int statModifier, ArtifactType type){
		this.statModifier = statModifier;
		this.type = type;
	}

	public ArtifactType getType(){
		return this.type;
	}

	public int getStatModifier(){
		return this.statModifier;
	}
}
