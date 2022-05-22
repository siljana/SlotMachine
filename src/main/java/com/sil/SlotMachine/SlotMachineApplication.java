package com.sil.SlotMachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SlotMachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlotMachineApplication.class, args);
	}

}
