package br.com.hioktec.arquivodocs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.hioktec.arquivodocs.model.Caixa;
import br.com.hioktec.arquivodocs.repository.CaixaRepository;
import br.com.hioktec.arquivodocs.repository.ClienteRepository;
import br.com.hioktec.arquivodocs.restcontroller.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CaixaService {
	
	private final CaixaRepository caixaRepository;
	
	private final ClienteRepository clienteRepository;
	
	public Caixa persistCaixa (Integer idCliente, Caixa caixa) {
		if(clienteRepository.findById(idCliente).isPresent()) {
			if( idCliente == caixa.getCliente()) {
				return caixaRepository.save(caixa);
			} else {
				throw new BadRequestException("Dados informados inconsistentes!");
			}
		} else {
			throw new BadRequestException("Cadastro do cliente não encontrado!");
		}
	}
	
	public List<Caixa> getCaixasByClienteId(Integer clienteId){
		return caixaRepository.getAllByCliente(clienteId);
	}
	
	public List<Integer> getCaixasIdsByClienteId(@PathVariable("id") Integer clienteId){
		return caixaRepository.getIdsByCliente(clienteId);
	}
	
	public void removeCaixa(Integer idCliente, Integer idCaixa) {
		if(clienteRepository.findById(idCliente).isPresent()) {
			caixaRepository.findById(idCaixa).ifPresentOrElse(caixa -> {
				if(caixa.getCliente() == idCliente) {
					caixaRepository.delete(caixa);
				} else {
					throw new BadRequestException("Caixa não pertence ao cliente informado!");
				}
			}, () -> {
				throw new BadRequestException("Cadastro do caixa não encontrada!");
			});
		} else {
			throw new BadRequestException("Cadastro do cliente não encontrado!");
		}
	};
}
