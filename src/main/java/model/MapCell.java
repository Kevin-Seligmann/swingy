package model;

public class MapCell {
	private final int x;
	private final int y;
	private MapCellContentType content;
	private Enemy enemy;
	private boolean explored;
	private Artifact artifact;

	protected MapCell(int x, int y){
		this.x = x;
		this.y = y;
		this.explored = false;
		this.content = MapCellContentType.EMPTY;
	}

	public int getX(){return x;}

	public int getY(){return y;}

	public Enemy getEnemy(){return enemy;}

	public Artifact getArtifact(){return artifact;}

	public void putHero(){
		this.explored = true;
		this.content = MapCellContentType.HERO;
	}

	public void putEnemy(Enemy enemy){
		this.enemy = enemy;
		this.content = MapCellContentType.ENEMY;
	}

	public void putEmpty(){
		this.content = MapCellContentType.EMPTY;
	}

	public void setArtifact(Artifact artifact){
		this.artifact = artifact;
	}

	public boolean isHero(){
		return content == MapCellContentType.HERO;
	}

	public boolean isExplored(){
		return explored;
	}

	public boolean isEnemy(){
		return content == MapCellContentType.ENEMY;
	}

	public boolean hasArtifact(){
		return artifact != null;
	}
}
