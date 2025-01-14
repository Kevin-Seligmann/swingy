package controller;

import view.GUIView;
import view.CLIView;

public class Controller {
	private final GUIView guiView;
	private final CLIView cliView;

	public Controller(){
		guiView = new GUIView();
		cliView = new CLIView();
	}

	public void run(){

	}
}
