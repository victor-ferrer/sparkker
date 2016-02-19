package org.vferrer.sparkker.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RulesConfig {

	@Value("${rules.file.path}")
	private String rulesFilePath;
	
    @Bean
    public KieContainer kieContainer() throws Exception {
    	
	    KieServices kieServices = KieServices.Factory.get();
	    KieFileSystem kfs = kieServices.newKieFileSystem();

	    // FIXME Can´t figure out how to take this file from the resources folder
	    FileInputStream fis = new FileInputStream(rulesFilePath);
	    kfs.write("src/main/resources/rules.drl", kieServices.getResources().newInputStreamResource(fis));

	    KieBuilder kieBuilder = kieServices.newKieBuilder( kfs ).buildAll();
	    Results results = kieBuilder.getResults();
	    if( results.hasMessages( Message.Level.ERROR ) ){
	        System.out.println( results.getMessages() );
	        throw new IllegalStateException( "### errors ###: "+ results.getMessages());
	    }

	    KieContainer kieContainer =  kieServices.newKieContainer( kieServices.getRepository().getDefaultReleaseId() );

        return kieContainer;
    }
}
