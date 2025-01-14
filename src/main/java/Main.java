// TODO: Main package

import controller.Controller;

public class Main {
	public static void main(String[] args){

		try {
			Controller controller = new Controller();
			controller.run();
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Unknown error.");
		}

	}
}
