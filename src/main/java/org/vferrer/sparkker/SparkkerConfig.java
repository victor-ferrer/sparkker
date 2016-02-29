package org.vferrer.sparkker;

import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringCloudApplication
@EnableFeignClients
public class SparkkerConfig {

}
