package br.com.hioktec.arquivodocs.rest;

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

import br.com.hioktec.arquivodocs.model.Documento;
import br.com.hioktec.arquivodocs.repository.DocumentoRepository;
import br.com.hioktec.arquivodocs.rest.exceptions.BadRequestException;
import br.com.hioktec.arquivodocs.service.DocumentoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documentos")
@RequiredArgsConstructor
public class DocumentoController {
	
	private final DocumentoRepository repository;
	
	private final DocumentoService service;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Documento save(@RequestBody @Valid Documento documento) {
		try {
			return service.saveDocumento(documento);
		} catch (BadRequestException ex){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	
	@GetMapping
	public List<Documento> getAll(){
		return repository.findAll();
	}
	
	@GetMapping("{id}")
	public Documento getById(@PathVariable Integer id) {
		return repository.findById(id)
							.orElseThrow(() -> 
								new ResponseStatusException(HttpStatus.NOT_FOUND, "Cadastro do documento não encontrado!"));
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		repository.findById(id).ifPresentOrElse(documento -> {
			repository.delete(documento);
		}, () -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cadastro do documento não encontrado!");
		});
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Integer id, @RequestBody @Valid Documento documentoAtualizado) {
		try {
			service.updateDocumento(id, documentoAtualizado);
		} catch (BadRequestException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
}
