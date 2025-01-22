package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hibernate.sql.ast.tree.predicate.Junction;

import controller.Controller;
import model.Artifact;
import model.Hero;
import model.Map;

public class GUIView extends View {
	private static final String TITLE = "S.W.I.N.G.Y";
	private static final int X_WINDOW_SIZE = 800;
	private static final int Y_WINDOW_SIZE = 800;

	private Controller controller;

	private JFrame mainFrame;
	private BorderLayout mainLayout;

	// South panel
	private JPanel southButtonsPanel;
	private FlowLayout southButtonsLayout;
	private JButton exitButton;
	private JButton switchViewButton;

	// Welcome view
	private JPanel welcomeViewPanel;
	private BoxLayout welcomeViewLayout;
	private JButton selectHeroButton;
	private JButton createHeroButton;

	public GUIView(Controller controller){
		this.controller = controller;
		initView();
	}

	public GUIView(){
	}

	public void setController(Controller controller){
		this.controller = controller;
		initView();
	}

	public void closeView() {
		mainFrame.dispose();
	}

	public void welcomeMenu(){
		setCentralPanel(welcomeViewPanel);
	}
	
	public void addHeroMenu() {
		// Add hero panel with form, and go back
	}

	public void selectHeroMenu(List<Hero> heroes) {
		// Show heroes with button to select one
	}

	public void selectedHeroMenu(Hero hero) {
		// Play, delete or cancel menu
	}

	public void showMap(Map currentMap, Hero currentHero){
		// Map + Hero data + Keys
	}

	public void preFightMenu(int enemyLevel){
		// Ask to run or figh menu
	}
	
	public void showArtifactMenu(Hero hero, Artifact artifact){
		// Show artifact + ask menu
	}

	public void notifyUser(String string) {
		// Pop up
	}

	private void setCentralPanel(JPanel panel){
		// if (mainFrame.getContentPane().getComponentCount() > 0) {
		// 	mainFrame.getContentPane().remove(mainLayout.getLayoutComponent(BorderLayout.CENTER));
		// }
		mainFrame.add(panel, BorderLayout.CENTER);
		mainFrame.revalidate();
		mainFrame.repaint();
	}

	private void initView(){
		configureMainFrame();
		configureSouthPanel();
		configureWelcomeView();
		mainFrame.setVisible(true);
	}

	private void configureMainFrame(){
		mainFrame = new JFrame();
		mainLayout = new BorderLayout();

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setTitle(TITLE);
		mainFrame.setSize(X_WINDOW_SIZE, Y_WINDOW_SIZE);
		mainFrame.getContentPane().setBackground(Color.GRAY);
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				controller.onExitSelected();
			}
		});

		mainFrame.setLayout(mainLayout);
		mainLayout.setHgap(10);
		mainLayout.setVgap(10);
	}

	private void configureSouthPanel(){
		southButtonsPanel = new JPanel();
		southButtonsLayout = new FlowLayout();
		southButtonsPanel.setLayout(southButtonsLayout);

		exitButton = new JButton("EXIT");
		exitButton.addActionListener(e->controller.onExitSelected());
		southButtonsPanel.add(exitButton);

		switchViewButton = new JButton("SWITCH VIEW");
		switchViewButton.addActionListener(e->controller.onSwitchViewSelected());
		southButtonsPanel.add(switchViewButton);

		mainFrame.add(southButtonsPanel, BorderLayout.SOUTH);
	}

	private void configureWelcomeView(){
		welcomeViewPanel = new JPanel();
		welcomeViewLayout = new BoxLayout(welcomeViewPanel, BoxLayout.Y_AXIS);
		welcomeViewPanel.setAlignmentY(JPanel.CENTER_ALIGNMENT);

		selectHeroButton = new JButton("SELECT HERO");
		selectHeroButton.addActionListener(e->controller.onSelectHeroSelected());
		welcomeViewPanel.add(selectHeroButton);
		selectHeroButton.setAlignmentY(JButton.CENTER_ALIGNMENT);
		selectHeroButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

		createHeroButton = new JButton("CREATE HERO");
		createHeroButton.addActionListener(e->controller.onAddHeroSelected());
		welcomeViewPanel.add(createHeroButton);
		createHeroButton.setAlignmentY(JButton.CENTER_ALIGNMENT);
		createHeroButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

		welcomeViewPanel.setLayout(welcomeViewLayout);
	}
}
