package util;

import java.util.Scanner;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class Utils {
	private static Utils instance;
	private Scanner scanner;
	private ValidatorFactory validatorFactory;
	private SessionFactory sessionFactory;

	private Utils(){
		sessionFactory = new Configuration().configure().buildSessionFactory();
        validatorFactory = Validation.byProvider(org.hibernate.validator.HibernateValidator.class)
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
		scanner = new Scanner(System.in);
   	}

	public static Validator getValidator(){
		if (instance == null)
			instance = new Utils();
		return instance.validatorFactory.getValidator();
	}

	public static SessionFactory getSessionFactory(){
		if (instance == null)
			instance = new Utils();
		return instance.sessionFactory;
	}

	public static Scanner getScanner(){
		if (instance == null)
			instance = new Utils();
		return instance.scanner;
	}

	public static void close(){
		if (instance != null){
			instance.validatorFactory.close();
			instance.sessionFactory.close();
			instance.scanner.close();
			instance = null;
		}
	}
}
