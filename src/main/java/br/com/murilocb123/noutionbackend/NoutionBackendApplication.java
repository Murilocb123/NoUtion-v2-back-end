package br.com.murilocb123.noutionbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NoutionBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoutionBackendApplication.class, args);
	}

}
