package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import util.DatabaseErrorException;
import util.Utils;
import util.ModelValidationException;
import view.View;

public class Model {
	private List<View> views;
	private Hero currentHero;
	private Map currentMap;

	public Map getCurrentMap(){
		return currentMap;
	}

	public void setCurrentHero(Hero hero){
		this.currentHero = hero;
	}
	
	public Hero getCurrentHero(){
		return currentHero;
	}
	public Model(){
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
			Utils.getSessionFactory().inTransaction(session -> {
				session.persist(hero);
			});
		} catch (Exception e){
			e.printStackTrace();
			throw new DatabaseErrorException("Error interacting with the database.");
		}
	}

	public void removeHero(Hero hero){
        try {
            Utils.getSessionFactory().inTransaction(session -> {
                session.remove(hero);
            });
        } catch (Exception e){
            throw new DatabaseErrorException("Error interacting with the database.");
        }
	}

	public void updateHero(Hero hero){
        validateHero(hero);
        try {
             Utils.getSessionFactory().inTransaction(session -> {
             	session.merge(hero);
        });
        } catch (Exception e){
            throw new DatabaseErrorException("Error interacting with the database.");
        }
	}

	public List<Hero> getHeroes(){
		SessionFactory sessionFactory = Utils.getSessionFactory();
        try {
            return sessionFactory.fromTransaction(session -> {
                var builder = sessionFactory.getCriteriaBuilder();
                CriteriaQuery<Hero> query = builder.createQuery(Hero.class);
                Root<Hero> seguro = query.from(Hero.class);
                query.select(seguro);
                return session.createSelectionQuery(query).getResultList();
            });
        } catch (Exception e) {
            throw new DatabaseErrorException("Error interacting with the database.");
        }
	}

	private void validateHero(Hero hero){
        StringBuilder error = new StringBuilder();

        Validator validator = Utils.getValidator();
        Set<ConstraintViolation<Hero>> constraintViolations = validator.validate(hero);
        if (!constraintViolations.isEmpty()){
            for (ConstraintViolation<Hero> violation : constraintViolations) {
				if ("name".equals(violation.getPropertyPath().toString()))
					error.append("\n").append("This name is already in use.");
                error.append("\n").append(violation.getMessage());
            }
            throw new ModelValidationException(error.toString());
        }
    }

	public void generateMap() {
		currentMap = new Map(currentHero.getLevel());
	}
}
