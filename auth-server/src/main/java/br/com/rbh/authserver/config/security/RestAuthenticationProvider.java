package br.com.rbh.authserver.config.security;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.web.client.RestTemplate;

public class RestAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String login = authentication.getName();
        String senha = authentication.getCredentials().toString();
        
    	RestTemplate template = new RestTemplate();
    	
    	try {
    		HttpHeaders headers = new HttpHeaders();
    		headers.add("X-AUTH-TOKEN", "c05e2183af5701686607c28a6839573f");
    		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    		
    		HttpEntity<String> request = new HttpEntity<String>("{\"login\":\"" + login + "\", \"senha\":\"" + senha + "\"}", headers);
    		
			ResponseEntity<String> response = template.postForEntity("https://sistemas5.ufms.br/passaporte-ws/api/ad_users", 
					request, String.class);
			
			if (response.getStatusCode() == HttpStatus.OK) {
				Map<String, String> details = (Map<String, String>) authentication.getDetails();
				
				String grantType = details.get("grant_type");
				String clientId = details.get("client_id");
				
				Authentication auth = new OAuth2Authentication(new OAuth2Request(details, clientId, null, true, null, null, null, null, null), authentication);
				
				return auth;
			} else {
				throw new AuthenticationCredentialsNotFoundException("Autenticação falhou para usuário " + login);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthenticationCredentialsNotFoundException("Autenticação falhou para usuário " + login);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	class LoginRequest {
		String login;
		String senha;
		
		LoginRequest(String login, String senha) {
			this.login = login;
			this.senha = senha;
		}
			
	}

}
