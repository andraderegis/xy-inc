package com.zup.xyapi.conf;

import java.util.Properties;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zup.xyapi.dao.BaseDAO;

@Configuration
@EnableTransactionManagement
@Profile("test")
@PropertySource({ "classpath:environment/test/datasource.properties" })
@ComponentScan(basePackageClasses = { BaseDAO.class })
public class TestHibernateConf extends BaseHibernateConf {
	
	@Override
	protected Properties hibernateProperties() {
		
		Properties properties = super.hibernateProperties();
		
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		properties.setProperty("hibernate.hbm2ddl.import_files", env.getProperty("hibernate.hbm2ddl.import_files"));
		
		return properties;
	}

}
