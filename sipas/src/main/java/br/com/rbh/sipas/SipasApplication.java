package br.com.rbh.sipas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SipasApplication {

	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.trustStore", SipasApplication.class.getResource("/truststore.jks").getPath());
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		
		SpringApplication.run(SipasApplication.class, args);
	}
	
}
