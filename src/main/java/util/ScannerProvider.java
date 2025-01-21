package util;

import java.util.Scanner;

public class ScannerProvider {
	private static ScannerProvider instance;
	private Scanner scanner;

	private ScannerProvider(){
		scanner = new Scanner(System.in);
   	}

	public static Scanner getScanner(){
		if (instance == null)
			instance = new ScannerProvider();
		return instance.scanner;
	}

	public static void close(){
		if (instance != null){
			instance.scanner.close();
			instance = null;
		}
	}
}
