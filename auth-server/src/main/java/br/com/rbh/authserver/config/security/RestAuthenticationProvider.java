package br.com.rbh.authserver.config.security;

import java.io.Serializable;
import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("rest")
public class RestAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			String login = authentication.getName();
	        String senha = authentication.getCredentials().toString();
	        
	    	RestTemplate template = new RestTemplate();
    	
    		HttpHeaders headers = new HttpHeaders();
    		headers.add("X-AUTH-TOKEN", "c05e2183af5701686607c28a6839573f");
    		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    		
    		HttpEntity<String> request = new HttpEntity<String>(new ObjectMapper().writeValueAsString(new LoginRequest(login, senha)), headers);
    		
			ResponseEntity<String> response = template.postForEntity("https://sistemas5.ufms.br/passaporte-ws/api/ad_users", 
					request, String.class);
			
			if (response.getStatusCode() == HttpStatus.OK) {
				return new UsernamePasswordAuthenticationToken(login, senha, new ArrayList<>());
			} else {
				throw new AuthenticationCredentialsNotFoundException("Autenticação falhou para usuário " + login);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthenticationCredentialsNotFoundException("Problema durante a autenticação");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	class LoginRequest implements Serializable {
		private static final long serialVersionUID = 1L;
		
		String login;
		String senha;
		
		LoginRequest(String login, String senha) {
			this.login = login;
			this.senha = senha;
		}

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getSenha() {
			return senha;
		}

		public void setSenha(String senha) {
			this.senha = senha;
		}
			
	}

}
