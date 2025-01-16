// TODO: Main package

import java.util.logging.Level;
import controller.Controller;


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

		if (!args[0].equalsIgnoreCase("console") && !args[0].equalsIgnoreCase("gui")){
			System.out.println("Error: Bad usage.");
			usage();
			return ;
		}

		try {
			Controller controller = new Controller();
			controller.run(args[0]);
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Unknown error.");
		}
	}

	private static void usage(){
		System.out.println("usage: java -jar swingy [view]");
		advise();
	}

	private static void advise(){
		System.out.println("Try 'java -jar swingy --help' for more information.");
	}

	private static void help(){
		System.out.println("View must be 'console' or 'gui'.");
	}
}
