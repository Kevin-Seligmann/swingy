package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import util.DatabaseErrorException;
import util.ModelValidationException;
import view.View;

public class Model {
	private List<View> views;
	SessionFactory sessionFactory;
	ValidatorFactory validatorFactory;

	public Model(SessionFactory sessionFactory, ValidatorFactory validatorFactory){
		this.sessionFactory = sessionFactory;
		this.validatorFactory = validatorFactory;
		views = new ArrayList<>();
	}

	public void registerView(View view){
		views.add(view);
	}

	public void unregisterView(View view){
		views.remove(view);
	}

	// CRUD 
	public void addHero(String name, HeroType type){
		Hero hero = HeroFactory.getHero(name, type);
		validateHero(hero);
		try {
			sessionFactory.inTransaction(session -> {
				session.persist(hero);
			});
		} catch (Exception e){
			// e.printStackTrace();
			throw new DatabaseErrorException("Adding Hero.");
		}
	}

	public void removeHero(Hero hero){
        try {
            sessionFactory.inTransaction(session -> {
                session.remove(hero);
            });
        } catch (Exception e){
            throw new DatabaseErrorException("Removing Hero.");
        }
	}

	public void updateHero(Hero hero){
        try {
			sessionFactory.inTransaction(session -> {
             	session.merge(hero);
        });
        } catch (Exception e){
            throw new DatabaseErrorException("Updating Hero.");
        }
	}

	public List<Hero> getHeroes(){
        try {
            return sessionFactory.fromTransaction(session -> {
                var builder = sessionFactory.getCriteriaBuilder();
                CriteriaQuery<Hero> query = builder.createQuery(Hero.class);
                Root<Hero> seguro = query.from(Hero.class);
                query.select(seguro);
                return session.createSelectionQuery(query).getResultList();
            });
        } catch (Exception e) {
            throw new DatabaseErrorException("Retrieving Heroes.");
        }
	}

	private void validateHero(Hero hero){
        StringBuilder error = new StringBuilder();

        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Hero>> constraintViolations = validator.validate(hero);
        if (!constraintViolations.isEmpty()){
            for (ConstraintViolation<Hero> violation : constraintViolations) {
                error.append("\n").append(violation.getMessage());
            }
            throw new ModelValidationException(error.toString());
        }
    }
}
