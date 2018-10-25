package br.com.rbh.authserver.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.rbh.authserver.model.Usuario;
import br.com.rbh.authserver.repository.UsuarioRepository;

@Component("jpa")
public class JPAAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Usuario usuario = usuarioRepository.findByNome(authentication.getName());
		
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário inexistente!");
		}
		
		if (!usuario.getSenha().equals(authentication.getCredentials().toString())) {
			throw new BadCredentialsException("Senha inválida!");
		}
		
		return new UsernamePasswordAuthenticationToken(authentication.getName(), 
				authentication.getCredentials().toString(), usuario.getPermissoes());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
