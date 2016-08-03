package com.submarine.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jersey.JerseyApiReader;
import com.wordnik.swagger.reader.ClassReaders;

@Configuration
public class ContainerListener implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    @Autowired
    private Environment env;

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        // Swagger
        final SwaggerConfig config = ConfigFactory.config();
        config.setBasePath("/api");
        config.setApiVersion(env.getProperty("info.build.version"));
        ScannerFactory.setScanner(new DefaultJaxrsScanner());
        ClassReaders.setReader(new JerseyApiReader());
    }
}
