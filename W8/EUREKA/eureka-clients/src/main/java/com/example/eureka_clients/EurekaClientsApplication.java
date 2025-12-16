package com.example.eureka_clients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class EurekaClientsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientsApplication.class, args);
	}

}

@RestController
class MyController {
	@GetMapping("/Hello")
	public String Hello() {
		return "Hello From the eureka client";
	}
}
