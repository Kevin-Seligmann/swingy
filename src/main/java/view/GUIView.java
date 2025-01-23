package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import model.Artifact;
import model.Hero;
import model.HeroType;
import model.Map;

public class GUIView extends View {
	private static final String TITLE = "S.W.I.N.G.Y";
	private static final int X_WINDOW_SIZE = 800;
	private static final int Y_WINDOW_SIZE = 800;

	private Controller controller;

	private JFrame mainFrame;
	private BorderLayout mainLayout;

	// South panel and components for multples vieews
	private JPanel southButtonsPanel;
	private FlowLayout southButtonsLayout;
	private JButton exitButton;
	private JButton switchViewButton;
	private JButton goBackToWelcomeScreenButton;

	// Welcome view
	private JPanel welcomeViewPanel;

	// Create hero view
	private JPanel createHeroViewPanel;
	private JTextField heroNameField;
	private ButtonGroup heroClassGroup;

	// Select hero view
	private JPanel selectHeroViewPanel;
	private DefaultTableModel heroTableModel; 

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
		heroNameField.setText("");
		heroClassGroup.clearSelection();	
		setCentralPanel(createHeroViewPanel);
	}

	public void selectHeroMenu(List<Hero> heroes) {
		if (heroes.isEmpty()){
			notifyUser("There are no heroes created.");
			controller.welcomeMenu();
		} else {
			heroTableModel.setRowCount(0);
			for (Hero hero: heroes){
				heroTableModel.addRow(new Object[]{
					hero.getId(),
					hero.getName(), 
					hero.getType().toString(), 
					hero.getLevel(),
					hero.getAttack(), 
					hero.getHitPoints(), 
					hero.getDefense(),
					hero.getWeaponStatWithBonus(),
					hero.getHelmStatWithBonus(),
					hero.getArmorStatWithBonus()}
				);
			}
			setCentralPanel(selectHeroViewPanel);
		}
	}

	public void selectedHeroMenu(Hero hero) {
		System.out.println(hero);
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
		JOptionPane.showMessageDialog(mainFrame, string, null, JOptionPane.INFORMATION_MESSAGE);
	}

	private void setCentralPanel(JPanel panel){
		mainFrame.getContentPane().removeAll();
		mainFrame.add(panel, BorderLayout.CENTER);
		mainFrame.add(southButtonsPanel, BorderLayout.SOUTH);
		mainFrame.revalidate();
		mainFrame.repaint();
	}

	private void initView(){
		configureMainFrame();
		configureMultipleViewsElements();
		configureWelcomeView();
		configureCreateHeroView();
		configureSelectHeroView();
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

	private void configureMultipleViewsElements(){
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

		goBackToWelcomeScreenButton = new JButton("GO BACK");
		goBackToWelcomeScreenButton.addActionListener(e->{controller.welcomeMenu();});
	}

	private void configureWelcomeView(){
		GridLayout welcomeViewLayout = new GridLayout(2, 1);
		welcomeViewPanel = new JPanel();
		welcomeViewPanel.setLayout(welcomeViewLayout);

		JButton selectHeroButton = new JButton("SELECT HERO");
		selectHeroButton.addActionListener(e->controller.onSelectHeroSelected());
		welcomeViewPanel.add(selectHeroButton);

		JButton createHeroButton = new JButton("CREATE HERO");
		createHeroButton.addActionListener(e->controller.onAddHeroSelected());
		welcomeViewPanel.add(createHeroButton);

	}

	private void configureCreateHeroView(){
		GridLayout createHeroViewLayout = new GridLayout(8, 1);
		heroClassGroup = new ButtonGroup();
		createHeroViewPanel = new JPanel();
		createHeroViewPanel.setLayout(createHeroViewLayout);

		JLabel heroNameLabel = new JLabel("Hero Name");
		createHeroViewPanel.add(heroNameLabel);

		heroNameField = new JTextField();
		createHeroViewPanel.add(heroNameField);

		JLabel heroClassLabel = new JLabel("Hero Class");
		createHeroViewPanel.add(heroClassLabel);

		JRadioButton heroWarriorRadioButton = new JRadioButton("WARRIOR");
		createHeroViewPanel.add(heroWarriorRadioButton);
		heroClassGroup.add(heroWarriorRadioButton);

		JRadioButton heroEnchanterRadioButton = new JRadioButton("ENCHANTER");
		createHeroViewPanel.add(heroEnchanterRadioButton);
		heroClassGroup.add(heroEnchanterRadioButton);

		JRadioButton heroPeasantRadioButton = new JRadioButton("PEASANT");
		createHeroViewPanel.add(heroPeasantRadioButton);
		heroClassGroup.add(heroPeasantRadioButton);

		JButton createHeroButton = new JButton("CREATE HERO");
			createHeroButton.addActionListener(e->{
				String heroName = heroNameField.getText().trim().toLowerCase();
				HeroType heroClass = null;

				if (heroWarriorRadioButton.isSelected()) {
					heroClass = HeroType.WARRIOR;
				} else if (heroEnchanterRadioButton.isSelected()) {
					heroClass = HeroType.ENCHANTER;
				} else if (heroPeasantRadioButton.isSelected()) {
					heroClass = HeroType.PEASANT;
				}

				// TODO: Notation based handling of empty name and empty class.
				controller.onAddHero(heroName, heroClass);
		});
		createHeroViewPanel.add(createHeroButton);

		createHeroViewPanel.add(goBackToWelcomeScreenButton);
	}

	private void configureSelectHeroView(){
		selectHeroViewPanel = new JPanel();
		GridLayout selectHeroViewLayout = new GridLayout(4, 1);
		selectHeroViewPanel.setLayout(selectHeroViewLayout);

		JLabel selectHeroLabel = new JLabel("Select a hero by clicking a row.");
		selectHeroViewPanel.add(selectHeroLabel);

		String[] columns = {"ID", "NAME", "CLASS", "LEVEL", "ATK", "HP", "DEF", "WEAPON", "HELM", "ARMOR"};
		heroTableModel = new DefaultTableModel(columns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable heroTable = new JTable(heroTableModel);
		heroTable.getColumnModel().getColumn(0).setMinWidth(0);
		heroTable.getColumnModel().getColumn(0).setMaxWidth(0);
		heroTable.getColumnModel().getColumn(0).setWidth(0);
		heroTable.getTableHeader().setResizingAllowed(false);
		heroTable.getTableHeader().setReorderingAllowed(false);
		heroTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane selectHeroScrollPane = new JScrollPane(heroTable);
		selectHeroViewPanel.add(selectHeroScrollPane);

		JButton selectHeroButton = new JButton("CHOOSE");
		selectHeroButton.addActionListener(e->{
			if (heroTable.getSelectedRow() != -1){
				controller.onSelectHeroById((int) heroTable.getValueAt(heroTable.getSelectedRow(), 0));
			} else {
                JOptionPane.showMessageDialog(mainFrame, "Please select a hero", null, JOptionPane.ERROR_MESSAGE);
            }
		});
		selectHeroViewPanel.add(selectHeroButton);

		selectHeroViewPanel.add(goBackToWelcomeScreenButton);
	}
}
