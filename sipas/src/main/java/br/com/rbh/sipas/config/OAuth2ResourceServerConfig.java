package br.com.rbh.sipas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
    }
 
    @Primary
	@Bean
	public ResourceServerTokenServices tokenServices() {
	   RemoteTokenServices tokenServices = new RemoteTokenServices();
	   tokenServices.setClientId("bse");
	   tokenServices.setClientSecret("123");
	   tokenServices.setCheckTokenEndpointUrl("http://localhost:8083/oauth/check_token");
	   return tokenServices;
	}
}
