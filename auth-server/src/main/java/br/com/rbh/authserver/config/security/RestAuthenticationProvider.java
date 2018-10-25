package br.com.rbh.authserver.config.security;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import br.com.rbh.authserver.dto.DefaultResponse;
import br.com.rbh.authserver.dto.LoginRequest;

@Component("rest")
public class RestAuthenticationProvider implements AuthenticationProvider {
	
	@Value("${authentication.authToken}")
	private String authToken;
	
	@Value("${authentication.loginURI}")
	private String loginURI;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String login = authentication.getName();
        String senha = authentication.getCredentials().toString();
	
		RequestEntity<LoginRequest> requestEntity = RequestEntity
				.post(URI.create(loginURI))
				.accept(MediaType.APPLICATION_JSON)
				.header("X-AUTH-TOKEN", authToken)
				.body(new LoginRequest(login, senha));
		
		RestTemplate template = new RestTemplate();
		template.setErrorHandler(new ResponseErrorHandler() {
			
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return !response.getStatusCode().equals(HttpStatus.OK);
			}
			
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
					StringWriter writer = new StringWriter();
					IOUtils.copy(response.getBody(), writer, Charset.defaultCharset());
					String body = writer.toString();
					
					DefaultResponse defaultResponse = new ObjectMapper().readValue(body, DefaultResponse.class);
					
					throw new AuthenticationCredentialsNotFoundException(defaultResponse.getMsg());
				}
			}
		});
		
		template.exchange(requestEntity, String.class);
		
		return new UsernamePasswordAuthenticationToken(login, senha, new ArrayList<>());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
