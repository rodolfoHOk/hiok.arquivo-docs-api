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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.hioktec.arquivodocs.model.Caixa;
import br.com.hioktec.arquivodocs.model.Cliente;
import br.com.hioktec.arquivodocs.repository.ClienteRepository;
import br.com.hioktec.arquivodocs.rest.exceptions.BadRequestException;
import br.com.hioktec.arquivodocs.service.CaixaService;
import br.com.hioktec.arquivodocs.service.ClienteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
	
	private final ClienteRepository repository;
	
	private final ClienteService service;
	
	private final CaixaService caixaService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente save(@RequestBody @Valid Cliente cliente){
		try {
			return service.saveCliente(cliente);
		} catch (BadRequestException ex){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	
	@GetMapping
	public List<Cliente> getAll(){
		return repository.findAll();
	}
	
	@GetMapping("{id}")
	public Cliente getById(@PathVariable Integer id) {
		return repository.findById(id)
							.orElseThrow(() -> 
								new ResponseStatusException(HttpStatus.NOT_FOUND, "Cadastro do cliente não encontrado!"));
	}
	
	@GetMapping("nome")
	public List<Cliente> searchByName(@RequestParam("nome") String nome){
		return repository.findByNome(nome);
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		repository.findById(id).ifPresentOrElse(cliente -> {
			repository.delete(cliente);
		}, () -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cadastro do cliente não encontrado!");
		});
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Integer id, @Valid @RequestBody Cliente clienteAtualizado) {
		repository.findById(id).ifPresentOrElse(cliente -> {
			cliente.setNome(clienteAtualizado.getNome());
			cliente.setCnpj(clienteAtualizado.getCnpj());
			cliente.setEndereco(clienteAtualizado.getEndereco());
			repository.save(cliente);
		}, () -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cadastro do cliente não encontrado!");
		});
	}
	
	@PostMapping("{id}/caixas")
	@ResponseStatus(HttpStatus.CREATED)
	public Caixa saveCaixa (@PathVariable("id") Integer idCliente, @RequestBody @Valid Caixa caixa) {
		try {
			return caixaService.persistCaixa(idCliente, caixa);
		} catch (BadRequestException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	
	@GetMapping("{id}/caixas")
	public List<Caixa> getCaixasByCliente(@PathVariable("id") Integer clienteId){
		return caixaService.getCaixasByClienteId(clienteId);
	}
	
	@DeleteMapping("{idCliente}/caixas/{idCaixa}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCaixa( @PathVariable("idCliente") Integer idCliente, @PathVariable("idCaixa") Integer idCaixa) {
		try {
			caixaService.removeCaixa(idCliente, idCaixa);
		} catch (BadRequestException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cadastro da caixa não encontrado!");
		}
	}
}
