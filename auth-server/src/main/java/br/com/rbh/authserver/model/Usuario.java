package br.com.rbh.authserver.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Usuario {
	
	@Id
	private Long id;

	private String nome;
	
	private String senha;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_permissao", 
		joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "permissao_id", referencedColumnName = "id"))
	private Set<Permissao> permissoes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Set<Permissao> getPermissoes() {
		return permissoes;
	}
}
