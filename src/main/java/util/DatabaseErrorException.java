package util;

public class DatabaseErrorException extends RuntimeException {
	public DatabaseErrorException(String msg){
		super("Database error: " + msg);
	}
}
