package br.com.rbh.authserver;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.apache.sling.commons.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
public class OAuthFlowTests {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8083";
		RestAssured.basePath = "/oauth/token";
	}

	@Test
	public void testClientCredentialsGrantTypeFlow() {
		given().
			auth().preemptive().basic("bse", "123").
			param("grant_type", "client_credentials").
		when().
			post().
		then().
			body("access_token", is(notNullValue())).
			body("token_type", is(notNullValue())).
			body("expires_in", is(notNullValue())).
			body("scope", is(notNullValue()));
	}

	@Test
	public void testPasswordGrantTypeFlow() {
		given().
			auth().preemptive().basic("bse", "123").
			param("grant_type", "password").
			param("username", "john").
			param("password", "123").
			param("client_id", "bse").
		when().
			post().
		then().
			body("access_token", is(notNullValue())).
			body("refresh_token", is(notNullValue())).
			body("token_type", is(notNullValue())).
			body("expires_in", is(notNullValue())).
			body("scope", is(notNullValue()));
	}
	
	@Test
	public void testPasswordGrantTypeFlowWithInvalidCredentials() {
		given().
			auth().preemptive().basic("bse", "123").
			param("grant_type", "password").
			param("username", "john").
			param("password", "1234").
			param("client_id", "bse").
		when().
			post().
		then().
			body("error", is("invalid_grant")).
			body("error_description", is("Bad credentials"));
	}
	
	@Test
	public void testImplicitGrantTypeFlow() {
		given().
			auth().preemptive().basic("bse", "123").
			param("grant_type", "implicit").
			param("client_id", "bse").
		when().
			post().
		then().
			body("access_token", is(notNullValue())).
			body("token_type", is(notNullValue())).
			body("expires_in", is(notNullValue())).
			body("scope", is(notNullValue()));
	}
	
	@Test
	public void testAuthorizationCodeGrantTypeFlow() throws Exception {
		Response response = given().
			param("response_type", "code").
			param("client_id", "bse").
			param("redirect_uri", "http://localhost:8080").
		when().
			post("http://localhost:8080/oauth/authorize");
		
		System.out.println("\n\n\t Resposta do access token:\n" + new JSONObject(response.getBody().asString()).toString(4) + "\n\n");
		
		response.
			then().
			body("access_token", is(notNullValue())).
			body("token_type", is(notNullValue())).
			body("expires_in", is(notNullValue())).
			body("scope", is(notNullValue()));
	}
	
	@Test
	public void testRefreshTokenGrantTypeFlow() throws Exception {
		Response response = given().
			auth().preemptive().basic("bse", "123").
			param("grant_type", "password").
			param("username", "john").
			param("password", "123").
			param("client_id", "bse").
		when().
			post();
		
		System.out.println("\n\n\t Resposta do access token:\n" + new JSONObject(response.getBody().asString()).toString(4) + "\n\n");
		
		response.
			then().
				body("access_token", is(notNullValue())).
				body("refresh_token", is(notNullValue())).
				body("token_type", is(notNullValue())).
				body("expires_in", is(notNullValue())).
				body("scope", is(notNullValue()));
		
		String refreshToken = response.path("refresh_token");
		
		response = given().
			auth().preemptive().basic("bse", "123").
			param("grant_type", "refresh_token").
			param("refresh_token", refreshToken).
		when().
			post();
		
		System.out.println("\n\n\t Resposta do refresh token:\n" + new JSONObject(response.getBody().asString()).toString(4) + "\n\n");
		
		response.
			then().
				body("access_token", is(notNullValue())).
				body("token_type", is(notNullValue())).
				body("expires_in", is(notNullValue())).
				body("scope", is(notNullValue()));
	}
}
