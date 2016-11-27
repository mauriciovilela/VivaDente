package com.odonto.util.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.odonto.dto.ConfigOUT;
import com.odonto.util.AppConfigProperties;

@ApplicationScoped
public class EntityManagerProducer {

	private EntityManagerFactory factory;
	
	public EntityManagerProducer() {
		Map<String, String> dbProps = new HashMap<String, String>();            
        
		AppConfigProperties prop = new AppConfigProperties();
		ConfigOUT config = prop.init();

		dbProps.put("hibernate.connection.url", config.getDbConexao());
		dbProps.put("hibernate.connection.username", config.getDbUsuario());
		dbProps.put("hibernate.connection.password", config.getDbSenha());
        
		factory = Persistence.createEntityManagerFactory("VivaDente", dbProps);	
	}
	
	@Produces @RequestScoped
	public EntityManager createEntityManager() {
		return factory.createEntityManager();
	}
	
	public void closeEntityManager(@Disposes EntityManager manager) {
		manager.close();
	}
	
}