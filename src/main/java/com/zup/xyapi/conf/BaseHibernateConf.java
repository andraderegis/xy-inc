package com.zup.xyapi.conf;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public abstract class BaseHibernateConf {

	@Autowired
	protected Environment env;

	@Bean
	public LocalSessionFactoryBean sessionFactory() {

		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.zup.xyapi.model" });
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}

	protected Properties hibernateProperties() {

		Properties properties = new Properties();

		properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		properties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));

		return properties;
	}

	@Bean
	public DataSource dataSource() {

		try {

			ComboPooledDataSource dts = new ComboPooledDataSource();

			dts.setDriverClass(env.getRequiredProperty("jdbc.driverclass"));
			dts.setUser(env.getRequiredProperty("jdbc.user"));
			dts.setPassword(env.getRequiredProperty("jdbc.password"));
			dts.setJdbcUrl(env.getRequiredProperty("jdbc.url"));

			dts.setMaxPoolSize(env.getRequiredProperty("pool.maxPoolSize", Integer.class));
			dts.setAcquireIncrement(env.getRequiredProperty("pool.acquireIncrement", Integer.class));
			dts.setMaxIdleTime(env.getRequiredProperty("pool.maxIdleTime", Integer.class));
			dts.setAcquireRetryAttempts(env.getRequiredProperty("pool.acquireRetryAttempts", Integer.class));
			dts.setInitialPoolSize(env.getRequiredProperty("pool.initialPoolSize", Integer.class));

			return dts;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Bean
	public HibernateTransactionManager transationManager(SessionFactory sessionFactory) {

		HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
		hibernateTransactionManager.setSessionFactory(sessionFactory);

		return hibernateTransactionManager;
	}
}
