package com.zup.xyapi.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zup.xyapi.dao.BaseDAO;

@Configuration
@EnableTransactionManagement
@Profile("production")
@PropertySource({ "classpath:environment/production/datasource.properties" })
@ComponentScan(basePackageClasses = { BaseDAO.class })
public class ProductionHibernateConf extends BaseHibernateConf {

}
