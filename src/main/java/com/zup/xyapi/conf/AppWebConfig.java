package com.zup.xyapi.conf;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zup.xyapi.controller.handler.ExceptionHandlerController;
import com.zup.xyapi.controller.impl.InterestPointController;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = { InterestPointController.class, ExceptionHandlerController.class })
public class AppWebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		converters.add(new MappingJackson2HttpMessageConverter());
		super.configureMessageConverters(converters);
		
	}
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){
		
		ReloadableResourceBundleMessageSource bundleMessageSource = new ReloadableResourceBundleMessageSource();
		
		bundleMessageSource.setBasename("classpath:/locale/messages");
		bundleMessageSource.setDefaultEncoding("UTF-8");
		
		return bundleMessageSource;
	}
}
