package br.com.rbh.bse.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rbh.bse.model.Publicacao;

@RestController
@RequestMapping("publicacoes")
public class PublicacoesResource {

	@GetMapping
	public List<Publicacao> listar() {
		return Arrays.asList(new Publicacao(1L, "Edital 1"), 
				new Publicacao(2L, "Resolução 1"), new Publicacao(3L, "Portaria 1")); 
	}
}
