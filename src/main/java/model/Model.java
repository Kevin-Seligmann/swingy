package model;

import java.util.ArrayList;
import java.util.List;

import view.View;

public class Model {
	private List<View> views;
	
	public Model(){
		views = new ArrayList<>();
	}

	public void addHero(){

	}

	public void removeHero(){

	}

	public void setArmor(){

	}

	public void setWeapon(){

	}

	public void setHelm(){

	}

	public void registerView(View view){
		views.add(view);
	}

	public void unregisterView(View view){
		views.remove(view);
	}
}
