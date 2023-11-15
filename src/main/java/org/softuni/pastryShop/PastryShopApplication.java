package org.softuni.pastryShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class PastryShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(PastryShopApplication.class, args);
	}

}
