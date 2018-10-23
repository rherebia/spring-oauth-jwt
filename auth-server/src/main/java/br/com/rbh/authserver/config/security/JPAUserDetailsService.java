package br.com.rbh.authserver.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.rbh.authserver.model.Usuario;
import br.com.rbh.authserver.repository.UsuarioRepository;

@Service("jpa")
public class JPAUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByNome(username);
		
		if (usuario != null) {
			return usuario;
		}
		
		throw new UsernameNotFoundException(username);
	}

}