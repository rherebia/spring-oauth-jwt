package br.com.rbh.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("jpa")
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
    }// @formatter:on
	
	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManager();
    }
		
	@Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
		http.authorizeRequests()
	        	.antMatchers("/login", "/token-handler**", "/oauth**").permitAll()
	        .and()
		        .authorizeRequests()
		        .anyRequest()
		        .authenticated()
	        .and()
	        	.formLogin().permitAll();
		// @formatter:on
    }
}
