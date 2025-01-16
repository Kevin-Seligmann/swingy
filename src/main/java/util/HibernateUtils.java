package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class HibernateUtils {
	private static HibernateUtils instance;
	private ValidatorFactory validatorFactory;
	private SessionFactory sessionFactory;

	private HibernateUtils(){
		sessionFactory = new Configuration().configure().buildSessionFactory();
        validatorFactory = Validation.byProvider(org.hibernate.validator.HibernateValidator.class)
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
   	}

	public static Validator getValidator(){
		if (instance == null)
			instance = new HibernateUtils();
		return instance.validatorFactory.getValidator();
	}

	public static SessionFactory getSessionFactory(){
		if (instance == null)
			instance = new HibernateUtils();
		return instance.sessionFactory;
	}

	public static void close(){
		if (instance != null){
			instance.validatorFactory.close();
			instance.sessionFactory.close();
			instance = null;
		}
	}
}
