package br.com.rbh.bse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BseApplication {

	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.trustStore", BseApplication.class.getResource("/truststore.jks").getPath());
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		
		SpringApplication.run(BseApplication.class, args);
	}
	
}
