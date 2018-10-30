package br.com.rbh.sipas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class SipasApplication {

	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.trustStore", SipasApplication.class.getResource("/truststore.jks").getPath());
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		
		SpringApplication.run(SipasApplication.class, args);
	}
	
}
