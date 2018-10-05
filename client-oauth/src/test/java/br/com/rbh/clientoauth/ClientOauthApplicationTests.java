package br.com.rbh.clientoauth;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ClientOauthApplicationTests {

	@Test
	public void comparePerformance() {
		long inicioRestTemplate = System.currentTimeMillis(); 
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity("https://sistemas5.ufms.br:8443/api/grh-ws/estados?pais=33", String.class);
		
		long fimRestTemplate = System.currentTimeMillis();
		
		System.out.println(responseEntity.getBody());
		System.out.println("Tempo do rest template: " + (fimRestTemplate - inicioRestTemplate));
		
		long inicioJersey = System.currentTimeMillis();
		String response = ClientBuilder.newClient()
			.target("https://sistemas5.ufms.br:8443/api/grh-ws/estados?pais=33")
			.request(MediaType.APPLICATION_JSON)
			.get(String.class);
		long fimJersey = System.currentTimeMillis();
		
		System.out.println(response);
		System.out.println("Tempo do rest template: " + (fimJersey - inicioJersey));
	}

}
