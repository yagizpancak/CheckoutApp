package com.trendyol.bootcamp.checkout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TrendyolCheckoutApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrendyolCheckoutApplication.class, args);
	}

}
