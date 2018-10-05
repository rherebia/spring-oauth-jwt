package br.com.rbh.clientoauth.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("publicacoes")
public class PublicacoesConsumer {
	
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping
	public ResponseEntity<?> listar() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8081/publicacoes", String.class);
		
		return ResponseEntity.ok(responseEntity.getBody()); 
	}
	
	@GetMapping("estados")
	public ResponseEntity<?> listarJersey() {
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<String> responseEstados = restTemplate.getForEntity("https://sistemas5.ufms.br:8443/api/grh-ws/estados?pais=33", String.class);
		
		return ResponseEntity.ok(responseEstados.getBody());
	}
	
}

class Publicacao {
	private Long id;
	
	private String nome;

	public Publicacao(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

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
}
