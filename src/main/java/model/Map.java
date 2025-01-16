package model;

public class Map {
	private int size;
	private MapCell[][] mapGrid;

	public Map(int level){
		size = (level-1) * 5 + 10 -(level % 2);
		mapGrid = new MapCell[size][size];
	}
}
