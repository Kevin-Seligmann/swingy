// TODO: Main package

import java.util.logging.Level;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.function.ModeStatsModeEmulation;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import controller.Controller;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import model.Model;
import util.DatabaseErrorException;
import util.ScannerProvider;
import view.CLIView;
import view.GUIView;
import view.View;


public class Main {
	public static void main(String[] args){
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		
		if (args.length != 1){
			usage();
			return ;
		}

		if (args[0].equals("--help")){
			help();
			return ;
		}

		SessionFactory sessionFactory = null;
		ValidatorFactory validatorFactory = null;
		Controller controller = null;
		try {
			View view;
		
			if (args[0].equalsIgnoreCase("console")){
				view = new CLIView();
			}
			else if (args[0].equalsIgnoreCase("gui")){
				view = new GUIView();
			}
			else {
				System.out.println("Error: Bad usage.");
				usage();
				return ;
			}

			validatorFactory = Validation.byProvider(org.hibernate.validator.HibernateValidator.class)
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
			sessionFactory = new Configuration().configure().buildSessionFactory();
			Model model = new Model(sessionFactory, validatorFactory);
			controller = new Controller(view, model);
			view.setController(controller);
			
			controller.run();
		} catch  (DatabaseErrorException e){
			System.out.println(e.getMessage());
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Unknown error.");
		}
	}

	private static void usage(){
		System.out.println("usage: java -jar swingy <console|gui>");
		advise();
	}

	private static void advise(){
		System.out.println("Try 'java -jar swingy --help' for more information.");
	}

	private static void help() {
		System.out.println("Welcome to Swingy!\n" +
			"GUI Controls are intuitive, just click the right button!\n\n" +
	
			"CLI Controls:\n" +
			"The controls are straightforward, but here are some additional rules:\n" +
			"- CLI inputs do not distinguish between uppercase and lowercase.\n" +
			"- You can switch views at any time by entering 'v' or 'view'.\n" +
			"- You can exit the program at any time by entering 'e' or 'exit'.\n" +
			"- Most controls follow this rule. For example, you can type 'play' or 'p', 'remove' or 'r', move 'west' or 'w', etc.\n" +
			"- To pick a class, it is recommended to use '1', '2', or '3', but you can also type the name of the class.\n" +
			"- To accept or reject an artifact, you can use inputs like 'yes', 'no', and other variants.\n\n" +
	
			"Gameplay:\n" +
			"- You create and pick a hero.\n" +
			"- You move across the map, which is a rectangular grid.\n" +
			"- When you encounter an enemy, you can choose to run (50% chance) or fight.\n" +
			"- If you win the fight, there's a chance you obtain an artifact to increase your stats. If you lose, your hero is deleted.\n" +
			"- When you reach the edge of the map, you win.\n\n" +
	
			"Classes:\n" +
			"- Warrior: Very strong, designed to defeat almost all enemies.\n" +
			"- Enchanter: Gains double stats from artifacts.\n" +
			"- Peasant: Weak, 'real' gameplay.\n\n" +
	
			"Hero Level:\n" +
			"- A hero levels up upon reaching the experience threshold: 'level * 1000 + (level - 1)^2 * 450'.\n" +
			"- When you level up, your stats increase by one point.\n\n" +
	
			"Stats:\n" +
			"- There are three main stats: attack, hit points, and defense.\n\n" +
	
			"Fight Outcomes:\n" +
			"- Fight outcomes are determined by the number of hits both combatants need to eliminate each other.\n" +
			"  (HP / (ATK - opponent's DEF))\n" +
			"- The combatant with the fewest required hits, plus a bit of luck, wins.\n\n" +
	
			"Artifact Stats:\n" +
			"- Artifact stats are based on the enemy level, plus or minus a luck factor.\n\n" +
	
			"Enemies:\n" +
			"- Enemy levels are determined based on hero level, with a luck factor added or subtracted.\n" +
			"- Enemy stats are solely based on their level.\n\n" +
	
			"Map:\n" +
			"- Map size is determined by hero level using the formula: (level - 1) * 5 + 10 - (level % 2).\n" +
			"- The density of enemies and artifacts remains constant across levels.\n" +
			"- Enemies can spawn at the edge of the map.\n\n" +
	
			"Objective:\n" +
			"The objectives of this program are:\n" +
			"- To implement the MVC pattern.\n" +
			"- To utilize Swing for GUI development.\n" +
			"- To apply annotation-based validation.\n" +
			"- To implement a persistent database.\n");
	}
}
