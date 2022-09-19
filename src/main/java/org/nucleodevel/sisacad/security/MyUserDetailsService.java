package org.nucleodevel.sisacad.security;

import java.util.List;

import org.nucleodevel.sisacad.domain.Usuario;
import org.nucleodevel.sisacad.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = repository.findByUsername(username);

		if (usuario == null) {
			throw new UsernameNotFoundException(username);
		}

		Integer id = usuario.getId();
		boolean isAdmin = id % 2 == 0;

		GrantedAuthority roleAdmin = new SimpleGrantedAuthority(Role.ADMIN.getTag());
		GrantedAuthority roleUser = new SimpleGrantedAuthority(Role.USER.getTag());

		List<GrantedAuthority> listRole = List.of(isAdmin ? roleAdmin : roleUser);

		return new User(usuario.getUsername(), "{noop}" + usuario.getPassword(), true, true, true, true, listRole);
	}

}
