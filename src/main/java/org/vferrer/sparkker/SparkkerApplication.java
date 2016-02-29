package org.vferrer.sparkker;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sparkker")
@Import(SparkkerConfig.class)
public class SparkkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparkkerApplication.class, args);
		
	}
}
