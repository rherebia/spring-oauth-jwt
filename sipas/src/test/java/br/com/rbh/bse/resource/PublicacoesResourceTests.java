package br.com.rbh.bse.resource;

import static io.restassured.RestAssured.given;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PublicacoesResourceTests {
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "http://sistemas5.ufms.br";
		RestAssured.basePath = "/sgp-ws/api";
	}

	@Test
	public void testGetPublicacoes() {
		given().
			relaxedHTTPSValidation().
		when().
			get("/servidores_inativos").
		then().
			statusCode(200);
			
		
	}

}
