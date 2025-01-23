package model;

public class Map {
	private static final double ENEMY_DENSITY = 0.2;
	private static final double ARTIFACT_DENSITY = 0.7;
	private static final int MAX_ENEMY_LEVEL_DIFF = 2;
	private static final int MAX_ARTIFACT_LEVEL_STAT_DIFF = 4;

	private int size;
	private MapCell heroCell;
	private MapCell[][] mapGrid;
	private int level;

	public Map(int level){
		this.level = level;
		this.size = (level - 1) * 5 + 10 -(level % 2);
		this.mapGrid = new MapCell[size][size];
		generateMap();
	}

	public MapCell getHeroCell(){
		return heroCell;
	}

	public int getSize(){
		return size;
	}

	public MapCell getCell(int x, int y){
		return mapGrid[x][y];
	}

	public MapCell[][] getMapGrid(){
		return mapGrid;
	}

	public void setHeroCell(MapCell cell){
		heroCell.putEmpty();
		cell.putHero();
		heroCell = cell;
	}

	public String toString(){
		StringBuilder stringBuilder = new StringBuilder();
		for (int j = 0; j < size; j++) {
			for (int i = 0; i < size; i++) {
				if (mapGrid[i][size - 1 - j].isHero())
					stringBuilder.append("P");
				else if (mapGrid[i][size - 1 - j].isExplored())
					stringBuilder.append("O");
				else
					stringBuilder.append("X");
				stringBuilder.append(" ");
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	private void generateMap(){
		for (int i = 0; i < size; i ++){
			for (int j = 0; j < size; j ++){
					mapGrid[i][j] = new MapCell(i, j);
					if (shouldHaveEnemy()){
						int enemyLevel = generateEnemyLevel();
						mapGrid[i][j].putEnemy(new Enemy(enemyLevel));
						if (shouldHaveArtifact()){
							Artifact artifact = generateArtifact();
							mapGrid[i][j].setArtifact(artifact);
						}
					}
			}
		}
		mapGrid[size / 2][size / 2].putHero();
		heroCell = mapGrid[size / 2][size / 2];
	}

	private Artifact generateArtifact(){
		ArtifactType type;
		int aux;
		int artifactStat;
	
		aux = ((int) (Math.random() * 100) % 3);
		switch (aux) {
			case 0: type = ArtifactType.ARMOR; break;
			case 1: type = ArtifactType.HELM; break;
			case 2: type = ArtifactType.WEAPON; break;
			default: type = ArtifactType.WEAPON;
		}
		artifactStat = generateArtifactStat();
		return new Artifact(artifactStat, type);
	}

	private int generateArtifactStat(){
		int artifactStat;

		artifactStat = level + (int) (MAX_ARTIFACT_LEVEL_STAT_DIFF * 2 * (Math.random() - 0.5));
		if (artifactStat < 1)
			return 1;
		return artifactStat;
	}

	private int generateEnemyLevel(){
		int enemyLevel;

		enemyLevel = level + (int) (MAX_ENEMY_LEVEL_DIFF * 2 * (Math.random() - 0.5));
		if (enemyLevel < 1)
			return 1;
		return enemyLevel;
	}

	private boolean shouldHaveEnemy(){
		return (Math.random() < ENEMY_DENSITY);
	}

	private boolean shouldHaveArtifact(){
		return (Math.random() < ARTIFACT_DENSITY);
	}

	public boolean isCellOnEdge(MapCell targetCell) {
		return
			targetCell.getX() == size - 1 || 
			targetCell.getY() == size - 1 ||
			targetCell.getY() == 0 ||
			targetCell.getX() == 0;
	}
}
