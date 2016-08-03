package com.submarine.config.swagger;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.wordnik.swagger.config.FilterFactory;

@Configuration
@PropertySources({ @PropertySource("classpath:default.properties"),
		@PropertySource(value = "file:${external.config}", ignoreResourceNotFound = true) })
public class SwaggerConfig implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		FilterFactory.setFilter(new SwaggerFilter());
	}

}