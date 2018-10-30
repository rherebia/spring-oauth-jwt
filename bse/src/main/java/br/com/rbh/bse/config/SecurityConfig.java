package br.com.rbh.bse.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.authorizeRequests()
	        	.antMatchers("/token-handler**").permitAll()
	        	.anyRequest().authenticated()
	        	.and()
	        		.logout().logoutSuccessUrl("https://localhost:8083/logout");
	}
}
