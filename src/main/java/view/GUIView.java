package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import controller.UserInput;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import model.Artifact;
import model.Hero;
import model.HeroType;
import model.Map;
import model.MapCell;

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

	// Create hero view
	private JPanel createHeroViewPanel;
	private JTextField heroNameField;
	private ButtonGroup heroClassGroup;

	// Select hero view
	private JPanel selectHeroViewPanel;
	private DefaultTableModel heroTableModel;

	// Selected hero view
	private JPanel selectedHeroViewpanel;
	private JLabel heroName; 
	private JLabel heroClass; 
	private JLabel heroStats; 
	private JLabel heroLevel; 
	private JLabel heroArtifacts; 
	private JLabel enchanterBonus; 

	// Map panel.
	private JPanel mapViewPanel;
	private JPanel mapContainerPanel;
	private JLabel heroNameMap; 
	private JLabel heroClassMap; 
	private JLabel heroStatsMap; 
	private JLabel heroLevelMap; 
	private JLabel heroArtifactsMap;
	private JLabel enchanterBonusMap;

	// Pre fight menu
	private JPanel preFightViewPanel;
	private JLabel enemyFoundLabel;

	// Artifact found menu
	private JPanel artifactViewPanel;
	private JLabel currentHeroArtifact;
	private JLabel artifactInfoLabel;

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
		String[] heroInfo = hero.toString().split("\n");
		heroName.setText(heroInfo[0]);
		heroClass.setText(heroInfo[1]);
		heroStats.setText(heroInfo[2]);
		heroLevel.setText(heroInfo[3]);
		heroArtifacts.setText(heroInfo[4]);
		if (hero.getType() == HeroType.ENCHANTER)
			enchanterBonus.setText(heroInfo[5]);
		else
			enchanterBonus.setText("");
		setCentralPanel(selectedHeroViewpanel);
	}

	public void showMap(Map currentMap, Hero currentHero){
		String[] heroInfo = currentHero.toString().split("\n");
		heroNameMap.setText(heroInfo[0]);
		heroClassMap.setText(heroInfo[1]);
		heroStatsMap.setText(heroInfo[2]);
		heroLevelMap.setText(heroInfo[3]);
		heroArtifactsMap.setText(heroInfo[4]);
		if (currentHero.getType() == HeroType.ENCHANTER)
			enchanterBonusMap.setText(heroInfo[5]);
		else
			enchanterBonusMap.setText("");
		generateMap(currentMap);
		setCentralPanel(mapViewPanel);
	}

	public void preFightMenu(int enemyLevel){
		enemyFoundLabel.setText("Enemy level " + enemyLevel + " found !!!");
		setCentralPanel(preFightViewPanel);
	}
	
	public void showArtifactMenu(Hero hero, Artifact artifact){
		String[] heroInfo = hero.toString().split("\n");
		artifactInfoLabel.setText("Found. " + artifact.toString());
		currentHeroArtifact.setText("Current artifacts. " + heroInfo[4]);
		setCentralPanel(artifactViewPanel);
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
		configureSouthPanel();
		configureWelcomeView();
		configureCreateHeroView();
		configureSelectHeroView();
		configureSelectedHeroView();
		configureMapView();
		configurePreFightView();
		configureArtifactView();
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

				controller.onAddHero(heroName, heroClass);
		});
		createHeroViewPanel.add(createHeroButton);

		JButton goBackToWelcomeScreenButton = new JButton("GO BACK");
		goBackToWelcomeScreenButton.addActionListener(e->{controller.welcomeMenu();});
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

		JButton goBackToWelcomeScreenButton = new JButton("GO BACK");
		goBackToWelcomeScreenButton.addActionListener(e->{controller.welcomeMenu();});
		selectHeroViewPanel.add(goBackToWelcomeScreenButton);
	}

	public void configureSelectedHeroView(){
		selectedHeroViewpanel = new JPanel();
		GridLayout selectedHeroViewLayout = new GridLayout(9, 1);
		selectedHeroViewpanel.setLayout(selectedHeroViewLayout);

		heroName = new JLabel();
		selectedHeroViewpanel.add(heroName);

		heroClass = new JLabel();
		selectedHeroViewpanel.add(heroClass);

		heroLevel = new JLabel();
		selectedHeroViewpanel.add(heroLevel);

		heroStats = new JLabel();
		selectedHeroViewpanel.add(heroStats);

		heroArtifacts = new JLabel();
		selectedHeroViewpanel.add(heroArtifacts);

		enchanterBonus = new JLabel();
		selectedHeroViewpanel.add(enchanterBonus);

		JButton playButton = new JButton("PLAY");
		playButton.addActionListener(e->{controller.onPlayHeroSelected();});
		selectedHeroViewpanel.add(playButton);

		JButton removeButton = new JButton("REMOVE");
		removeButton.addActionListener(e->{controller.onRemoveHeroSelected();});
		selectedHeroViewpanel.add(removeButton);

		JButton goBackToWelcomeScreenButton = new JButton("GO BACK");
		goBackToWelcomeScreenButton.addActionListener(e->{controller.onSelectHeroSelected();});
		selectedHeroViewpanel.add(goBackToWelcomeScreenButton);
	}

	private void configureMapView(){
		mapViewPanel = new JPanel();
		BorderLayout mapViewLayout = new BorderLayout();
		mapViewPanel.setLayout(mapViewLayout);

		JPanel mapViewSouthPanel = new JPanel();
		FlowLayout mapViewSouthButtonsLayout = new FlowLayout();
		mapViewSouthPanel.setLayout(mapViewSouthButtonsLayout);
		mapViewPanel.add(mapViewSouthPanel, BorderLayout.SOUTH);

		JButton goWestButton = new JButton("WEST");
		goWestButton.addActionListener(e->{controller.onMove(UserInput.WEST);});
		mapViewSouthPanel.add(goWestButton);
	
		JButton goNorthButton = new JButton("NORTH");
		goNorthButton.addActionListener(e->{controller.onMove(UserInput.NORTH);});
		mapViewSouthPanel.add(goNorthButton);

		JButton goSouthButton = new JButton("SOUTH");
		goSouthButton.addActionListener(e->{controller.onMove(UserInput.SOUTH);});
		mapViewSouthPanel.add(goSouthButton);

		JButton goEastButton = new JButton("EAST");
		goEastButton.addActionListener(e->{controller.onMove(UserInput.EAST);});
		mapViewSouthPanel.add(goEastButton);

		JButton quitMapButton = new JButton("QUIT MAP");
		quitMapButton.addActionListener(e->{controller.onSelectedHero();});
		mapViewSouthPanel.add(quitMapButton);

		JPanel eastLayoutMapPanel = new JPanel();
		GridLayout eastLayoutMapPanelLayout = new GridLayout(6, 1);
		eastLayoutMapPanel.setLayout(eastLayoutMapPanelLayout);
		mapViewPanel.add(eastLayoutMapPanel, BorderLayout.EAST);

		heroNameMap = new JLabel();
		eastLayoutMapPanel.add(heroNameMap);

		heroClassMap = new JLabel();
		eastLayoutMapPanel.add(heroClassMap);

		heroLevelMap = new JLabel();
		eastLayoutMapPanel.add(heroLevelMap);

		heroStatsMap = new JLabel();
		eastLayoutMapPanel.add(heroStatsMap);

		heroArtifactsMap = new JLabel();
		eastLayoutMapPanel.add(heroArtifactsMap);

		enchanterBonusMap = new JLabel();
		eastLayoutMapPanel.add(enchanterBonusMap);

		mapContainerPanel = new JPanel();
		mapViewPanel.add(mapContainerPanel, BorderLayout.CENTER);
	}

	// TODO: This is very slow
	private void generateMap(Map map){
		mapContainerPanel.removeAll();
		int mapSize = map.getSize();
		GridLayout layout = new GridLayout(mapSize, mapSize);
		mapContainerPanel.setLayout(layout);

		MapCell[][] mapGrid = map.getMapGrid();
		for (int j = 0; j < mapSize; j++) {
			for (int i = 0; i < mapSize; i++) {
				JLabel cellLabel = new JLabel("", SwingConstants.CENTER);
				cellLabel.setOpaque(true);
				cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				if (mapGrid[i][mapSize - 1 - j].isHero()){
					cellLabel.setText("🦸");
					cellLabel.setBackground(Color.YELLOW);
				}
				else if (mapGrid[i][mapSize - 1 - j].isExplored()){
					cellLabel.setText("⬜");
					cellLabel.setBackground(Color.LIGHT_GRAY);
				}
				else{
					cellLabel.setText("⬛");
					cellLabel.setBackground(Color.DARK_GRAY);
				}

				mapContainerPanel.add(cellLabel);
			}
		}
	}

	private void configurePreFightView(){
		preFightViewPanel = new JPanel();
		GridLayout preFightViewLayout = new GridLayout(3,1);
		preFightViewPanel.setLayout(preFightViewLayout);
		
		enemyFoundLabel = new JLabel();
		preFightViewPanel.add(enemyFoundLabel);

		JButton fightButon = new JButton("FIGHT");
		fightButon.addActionListener(e->{controller.onFight();});
		preFightViewPanel.add(fightButon);

		JButton runButton = new JButton("RUN");
		runButton.addActionListener(e->{controller.onRun();});
		preFightViewPanel.add(runButton);
	}
	
	private void configureArtifactView(){
		artifactViewPanel = new JPanel();
		GridLayout artifactViewLayout = new GridLayout(4, 1);
		artifactViewPanel.setLayout(artifactViewLayout);

		artifactInfoLabel = new JLabel("");
		artifactViewPanel.add(artifactInfoLabel);

		currentHeroArtifact = new JLabel("");
		artifactViewPanel.add(currentHeroArtifact);
	
		JButton keepArtifactButton = new JButton("KEEP ARTIFACT");
		keepArtifactButton.addActionListener(e->{controller.onAcceptArtifact();});
		artifactViewPanel.add(keepArtifactButton);

		JButton rejectArtifactButton = new JButton("REJECT ARTIFACT");
		rejectArtifactButton.addActionListener(e->{controller.onRejectArtifact();});
		artifactViewPanel.add(rejectArtifactButton);
	}
}
