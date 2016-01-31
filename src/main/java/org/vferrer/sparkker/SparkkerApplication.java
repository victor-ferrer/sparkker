package org.vferrer.sparkker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SparkkerConfig.class)
public class SparkkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparkkerApplication.class, args);
		
	}
}
