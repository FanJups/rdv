package biz.advanceitgroup.rdvserver;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;



import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import biz.advanceitgroup.rdvserver.configurations.AppProperties;


@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties(AppProperties.class)
public class RendezvousNowApplication {
	
	
	private static final Logger logger = LoggerFactory.getLogger(RendezvousNowApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(RendezvousNowApplication.class, args);
		logger.debug("--Application Started--");
	}
	
}

