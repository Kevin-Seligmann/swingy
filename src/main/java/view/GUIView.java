package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Controller;

public class GUIView extends View {
	private static final String TITLE = "S.W.I.N.G.Y";
	private static final int X_WINDOW_SIZE = 800;
	private static final int Y_WINDOW_SIZE = 800;
	private Controller controller;
	private JFrame frame;
	private JPanel welcomePanel;
	private JPanel mapPanel;
	private JPanel fightPanel;
	private JPanel selectHeroScreen;
	private JTextField windowTitle;

	public GUIView(Controller controller){
		this.controller = controller;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(TITLE);
		frame.setSize(X_WINDOW_SIZE, Y_WINDOW_SIZE);
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setVisible(true);
	}
	
	public void closeView() {
		frame.dispose();
	}

	public void welcomeMenu(){
		if (welcomePanel == null)
			createWelcomePanel();
		setPanel(welcomePanel);
	}

	private void setPanel(JPanel panel){
		frame.setContentPane(panel);
		frame.revalidate();
		frame.repaint();
	}

	private void createWelcomePanel(){
		JPanel welcomePanel = new JPanel();
		JButton addHeroButton = new JButton("ADD HERO");
		JButton selectHeroButton = new JButton("SELECT HERO");
		JButton changeViewButton = new JButton("CHANGE VIEW");
		JButton exitButton = new JButton("EXIT");
		GridLayout layout = new GridLayout(2, 1, 0, 10);
		
		addHeroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				controller.addHero();
			}
		});

		selectHeroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				controller.selectHero();
			}
		});

		changeViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				controller.switchView();
			}
		});

		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				controller.exit();
			}
		});

		welcomePanel.setLayout(layout);
		welcomePanel.add(addHeroButton);
		welcomePanel.add(selectHeroButton);
		welcomePanel.add(changeViewButton);
		welcomePanel.add(exitButton);
		this.welcomePanel = welcomePanel;
	}

	public void addHeroMenu() {
	}
}
