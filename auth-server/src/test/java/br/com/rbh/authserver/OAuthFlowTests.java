package br.com.rbh.authserver;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, 
	properties = { "server.port=8083" })
public class OAuthFlowTests {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "https://localhost:8083";
		RestAssured.basePath = "/oauth/token";
	}

	@Test
	public void testClientCredentialsGrantTypeFlow() {
		given().
			relaxedHTTPSValidation().
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
			relaxedHTTPSValidation().
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
			relaxedHTTPSValidation().
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
	
	// TODO criar cenario para implicit
//	@Test
	public void testImplicitGrantTypeFlow() throws Exception {
		// get https://localhost:8083/oauth/authorize?response_type=token&client_id=bse&redirect_uri=https://localhost:8083/token-handler
		
		Response response = given().
			relaxedHTTPSValidation().
			param("reponse_type", "token").
			param("client_id", "bse").
			param("redirect_uri", "https://localhost:8083/token-handler").
		when().
			get("https://localhost:8083/oauth/authorize");
		
		Document loginHtml = Jsoup.parse(response.getBody().asString());
		
		Elements elements = loginHtml.select("input#login,input#password");
		
		assertTrue(elements.size() == 2);
		
		String jSessionId = response.getCookie("JSESSIONID");
		
		elements = loginHtml.select("input#csrf");
		String csrf = elements.get(0).attr("value");
		
		response = given().
			relaxedHTTPSValidation().
			param("username", "admin").
			param("password", "admin").
			param("_csrf", csrf).
			header("Content-Type", "application/x-www-form-urlencoded").
			cookie("JSESSIONID", jSessionId).
			redirects().follow(true).
		when().
			post("https://localhost:8083/login");
		
		System.out.println(response.getBody().asString());
	}
	
	// TODO criar o cenario para authorization code
	@Test
	public void testAuthorizationCodeGrantTypeFlow() throws Exception {
		// get https://localhost:8083/oauth/authorize?response_type=code&client_id=bse&redirect_uri=https://localhost:8083/token-handler
		
		Response response = given().
			relaxedHTTPSValidation().
			param("response_type", "code").
			param("client_id", "bse").
			param("redirect_uri", "https://localhost:8083/token-handler").
			redirects().follow(true).
		when().
			get("https://localhost:8083/oauth/authorize");
		
		Document loginHtml = Jsoup.parse(response.getBody().asString());
		
		Elements elements = loginHtml.select("input#login,input#password");
		
		assertTrue(elements.size() == 2);
		
		String jSessionId = response.getCookie("JSESSIONID");
		
		elements = loginHtml.select("input#csrf");
		String csrf = elements.get(0).attr("value");
		
		response = given().
			relaxedHTTPSValidation().
			param("username", "admin").
			param("password", "admin").
			param("_csrf", csrf).
			header("Content-Type", "application/x-www-form-urlencoded").
			cookie("JSESSIONID", jSessionId).
			redirects().follow(true).
		when().
			post("https://localhost:8083/login");
		
		System.out.println(response.getBody().asString());
	}
	
	@Test
	public void testRefreshTokenGrantTypeFlow() throws Exception {
		Response response = given().
			relaxedHTTPSValidation().
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
			relaxedHTTPSValidation().
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
