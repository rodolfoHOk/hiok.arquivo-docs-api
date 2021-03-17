package br.com.hioktec.arquivodocs.service;


import org.springframework.stereotype.Service;

import br.com.hioktec.arquivodocs.model.Cliente;
import br.com.hioktec.arquivodocs.repository.ClienteRepository;
import br.com.hioktec.arquivodocs.restcontroller.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
	
	private final ClienteRepository repository;
	
	public Cliente saveCliente(Cliente cliente) {
		repository.findByCnpj(cliente.getCnpj()).ifPresent(clienteCadastrado -> {
			throw new BadRequestException("Já existe cliente com o CNPJ informado!");
		});
		return repository.save(cliente);
	}
}
