package br.com.rbh.sipas.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rbh.sipas.model.Procedimento;

@RestController
@RequestMapping("procedimentos")
public class ProcedimentosResource {

	@GetMapping
	public List<Procedimento> listar() {
		return Arrays.asList(new Procedimento(1L, "Procedimento 1"), 
				new Procedimento(2L, "Procedimento 2"), new Procedimento(3L, "Procedimento 3")); 
	}
}
