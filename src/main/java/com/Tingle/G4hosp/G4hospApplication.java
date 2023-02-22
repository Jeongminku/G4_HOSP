package com.Tingle.G4hosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
public class G4hospApplication {

	public static void main(String[] args) {
		SpringApplication.run(G4hospApplication.class, args);
	}

}
