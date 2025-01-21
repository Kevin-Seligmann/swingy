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

			ValidatorFactory validatorFactory = Validation.byProvider(org.hibernate.validator.HibernateValidator.class)
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Model model = new Model(sessionFactory, validatorFactory);
			Controller controller = new Controller(view, model);
			view.setController(controller);
			
			controller.run();

			sessionFactory.close();
			validatorFactory.close();
			ScannerProvider.close();
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Unknown error.");
		}

		ScannerProvider.close();
	}

	private static void usage(){
		System.out.println("usage: java -jar swingy <console|gui>");
		advise();
	}

	private static void advise(){
		System.out.println("Try 'java -jar swingy --help' for more information.");
	}

	private static void help(){
		System.out.println("View must be 'console' or 'gui'.");
	}
}
