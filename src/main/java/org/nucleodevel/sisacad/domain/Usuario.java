package org.nucleodevel.sisacad.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.nucleodevel.sisacad.security.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
public class Usuario extends AbstractEntity<Integer> {

	private static final long serialVersionUID = -2060797194055814399L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "nome")
	private String nome;

	@Column(name = "email")
	private String email;

	@Column(name = "roles")
	private String roles;

	public Usuario() {
		super();
	}

	public Usuario(String username, String password, String nome, String email, String roles) {
		super();
		this.username = username;
		this.password = password;
		this.nome = nome;
		this.email = email;
		this.roles = roles;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public List<Role> getListRole() {
		String[] arrayRole = roles.split(",");

		List<Role> listRole = new ArrayList<>();
		for (String tag : arrayRole) {
			Role role = Role.getByTag(tag);

			if (role != null) {
				listRole.add(Role.getByTag(tag));
			}
		}

		return listRole;
	}

	public List<GrantedAuthority> getListAuthority() {
		List<GrantedAuthority> listAuthority = new ArrayList<>();
		for (Role role : getListRole()) {
			listAuthority.add(new SimpleGrantedAuthority(role.getTag()));
		}

		return listAuthority;
	}

	public void addRole(Role role) {
		if (!getListRole().contains(role)) {
			roles += "," + role.getTag();
		}
	}

}
