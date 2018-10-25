package br.com.rbh.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("jpa")
	private AuthenticationProvider authenticationProvider;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		// no op password encoder
		return new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return true;
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}
		};
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
	
	@Override
    protected void configure(final HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.authorizeRequests()
	        	.antMatchers("/login", "/token-handler/**", "/oauth/**", "/js/**", "/css/**", "/img/**")
	        	.permitAll()
	        .and()
		        .authorizeRequests()
		        .anyRequest()
		        .authenticated()
	        .and()
	        	.formLogin()
	        	.loginPage("/login")
	        	.failureUrl("/login?error")
	        	.permitAll();
    }
}
