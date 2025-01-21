package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Controller;
import controller.UserInput;
import model.Artifact;
import model.Enemy;
import model.Hero;
import model.Map;
import model.MapCell;

public class GUIView extends View {
	private static final String TITLE = "S.W.I.N.G.Y";
	private static final int X_WINDOW_SIZE = 800;
	private static final int Y_WINDOW_SIZE = 800;
	private Controller controller;
	private JFrame frame;
	private JButton exitButton;
	private JButton switchViewButton;
	private JPanel welcomePanel;
	private JPanel mapPanel;
	private JPanel fightPanel;
	private JPanel selectHeroScreen;
	private JTextField windowTitle;

	public GUIView(Controller controller){
		this.controller = controller;
		initView();
	}

	public GUIView(){
		initView();
	}

	public void setController(Controller controller){
		this.controller = controller;
	}

	public void closeView() {
		frame.dispose();
	}

	public void welcomeMenu(){
		setPanel(welcomePanel);
	}
	
	public void selectHeroMenu(){}


	public void addHeroMenu() {
	}

	public void notifyUser(String string) {
	}

	public void selectHeroMenu(List<Hero> heroes) {
	}

	public void selectedHeroMenu(Hero hero) {
	}

	public void showMap(Map currentMap, Hero currentHero){
		
	}

	public void preFightMenu(int enemyLevel){
	}
	
	public void showArtifactMenu(Hero hero, Artifact artifact){

	}

	private void initView(){
		createFrame();
		createComponents();
		createWelcomePanel();
	}

	private void setPanel(JPanel panel){
		frame.setContentPane(panel);
		frame.revalidate();
		frame.repaint();
	}

	private void createFrame(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(TITLE);
		frame.setSize(X_WINDOW_SIZE, Y_WINDOW_SIZE);
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setVisible(true);
	}

	private void createComponents(){
		switchViewButton = new JButton("SWITCH VIEW");
		switchViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				controller.onSwitchViewSelected();
			}
		});

		exitButton = new JButton("EXIT");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				controller.onExitSelected();
			}
		});
	}

	private void createWelcomePanel(){
		welcomePanel = new JPanel();
		JButton addHeroButton = new JButton("ADD HERO");
		JButton selectHeroButton = new JButton("SELECT HERO");
		GridLayout layout = new GridLayout(5, 1, 0, 10);
		
		addHeroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				controller.onAddHeroSelected();
			}
		});

		selectHeroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				controller.onSelectHeroSelected();
			}
		});

		welcomePanel.setLayout(layout);
		welcomePanel.add(addHeroButton);
		welcomePanel.add(selectHeroButton);
		welcomePanel.add(switchViewButton);
		welcomePanel.add(exitButton);
	}
}
