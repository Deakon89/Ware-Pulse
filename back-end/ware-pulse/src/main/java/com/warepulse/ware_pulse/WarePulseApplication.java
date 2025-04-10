package com.warepulse.ware_pulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.warepulse"})
@EntityScan(basePackages = {"com.warepulse.model"})
@EnableJpaRepositories(basePackages = {"com.warepulse.repository"})
public class WarePulseApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarePulseApplication.class, args);
	}

}
