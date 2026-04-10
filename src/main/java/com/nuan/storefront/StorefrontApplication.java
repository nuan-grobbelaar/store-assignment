package com.nuan.storefront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application entry point for the Storefront Backend.
 *
 * Architecture: Hexagonal (Ports & Adapters)
 *
 * This application is structured in layers:
 * - Domain: Core business logic (entities, ports)
 * - Application: Use cases and orchestration
 * - Adapters: HTTP controllers, persistence, notifications
 * - Infrastructure: Configuration and wiring
 */
@SpringBootApplication
public class StorefrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorefrontApplication.class, args);
	}

}
