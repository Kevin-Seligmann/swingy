package controller;

import view.GUIView;
import view.View;
import view.CLIView;

public class Controller {
	private GUIView guiView;
	private CLIView cliView;
	private View currentView;

	public Controller(){}

	public void run(String viewType){
		if (viewType.equalsIgnoreCase("console"))
			setCliView();
		else if (viewType.equalsIgnoreCase("gui"))
			setGuiView();
		currentView.welcomeMenu();
	}

	public void addHero(){
		currentView.addHeroMenu();
	}

	public void selectHero(){
		System.out.println("Select hero");
	}

	public void switchView(){
		currentView.closeView();
		if (currentView == cliView)
			setGuiView();
		else
			setCliView();
		currentView.loadView();
	}

	private void setGuiView(){
		if (guiView == null)
			guiView = new GUIView(this);
		currentView = guiView;
	}

	private void setCliView(){
		if (cliView == null)
			cliView = new CLIView(this);
		currentView = cliView;
	}

}
