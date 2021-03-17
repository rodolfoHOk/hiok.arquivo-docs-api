package br.com.hioktec.arquivodocs.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.hioktec.arquivodocs.model.TipoDocumento;
import br.com.hioktec.arquivodocs.repository.TipoDocumentoRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documentos/tipos")
@RequiredArgsConstructor
public class TipoDocumentoController {
	
	private final TipoDocumentoRepository repository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TipoDocumento newTipoDocumento(@RequestBody @Valid TipoDocumento tipoDocumento) {
		return repository.save(tipoDocumento);
	}
	
	@GetMapping
	public List<TipoDocumento> getTipos() {
		return repository.findAll();
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		repository.findById(id).ifPresentOrElse(tipoDocumento -> {
			repository.delete(tipoDocumento);
		}, () -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cadastro do tipo de documento não encontrado");
		});
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateNome(@PathVariable Integer id, @RequestBody @Valid TipoDocumento tipoDocumentoAtualizado) {
		repository.findById(id).ifPresentOrElse(tipoDocumento -> {
			if(tipoDocumentoAtualizado.getId() == id) {
				tipoDocumento.setNome(tipoDocumentoAtualizado.getNome());
				repository.save(tipoDocumento);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados informados inconsistentes!");
			}
		}, () -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cadastro do tipo de documento não encontrado");
		});
	}
}
