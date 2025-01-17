package model;

public class Map {
	public static final int EMPTY_SQUARE = 0;
	public static final int PLAYER = 1;
	public static final int EXPLORED_SQUARE = 2;
	// Enemies are -1, -2 ... based on level.

	private static final double ENEMY_DENSITY = 0.2;
	private static final int MAX_ENEMY_LEVEL_DIFF = 2;

	private int size;
	private Integer[][] mapGrid;

	public Map(int level){
		size = (level - 1) * 5 + 10 -(level % 2);
		mapGrid = new Integer[size][size];
		for (int i = 0; i < size; i ++){
			for (int j = 0; j < size; j ++){
				if (shouldHaveEnemy())
					mapGrid[i][j] = generateEnemy(level);
				else
					mapGrid[i][j] = EMPTY_SQUARE;
			}
		}
		mapGrid[size / 2][size / 2] = PLAYER;
	}

	private boolean shouldHaveEnemy(){
		return (Math.random() < ENEMY_DENSITY);
	}

	private int generateEnemy(int playerLevel){
		int enemyLevel = playerLevel + (int) (2 * MAX_ENEMY_LEVEL_DIFF * (Math.random() - 0.5));
		if (enemyLevel  <= 0)
			return 1;
		return enemyLevel;
	}

	public String toString(){
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (mapGrid[i][j] == PLAYER)
					stringBuilder.append("P");
				else if (mapGrid[i][j] == EXPLORED_SQUARE)
					stringBuilder.append("O");
				else
					stringBuilder.append("X");
				stringBuilder.append(" ");
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
}
