package br.com.hioktec.arquivodocs.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.hioktec.arquivodocs.model.Caixa;
import br.com.hioktec.arquivodocs.model.Cliente;
import br.com.hioktec.arquivodocs.model.Documento;
import br.com.hioktec.arquivodocs.repository.CaixaRepository;
import br.com.hioktec.arquivodocs.repository.ClienteRepository;
import br.com.hioktec.arquivodocs.repository.DocumentoRepository;
import br.com.hioktec.arquivodocs.repository.TipoDocumentoRepository;
import br.com.hioktec.arquivodocs.restcontroller.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentoService {
	
	private final DocumentoRepository documentoRepository;
	
	private final CaixaRepository caixaRepository;
	
	private final ClienteRepository clienteRepository;
	
	private final TipoDocumentoRepository tipoDocumentoRepository;
	
	public Documento saveDocumento(Documento documento) {
		if(isCaixaDoCliente(documento.getCaixa(), documento.getCliente())) {
			if(tipoDocumentoRepository.existsById(documento.getTipoDocumento())) {
				return documentoRepository.save(documento);
			} else {
				throw new BadRequestException("Cadastro do tipo de documento não encontrado!");
			}
		} else {
			throw new BadRequestException("Caixa não pertence ao cliente informado!");
		}
	}
	
	public void updateDocumento(Integer id, Documento documentoAtualizado) {
		if(isCaixaDoCliente(documentoAtualizado.getCaixa(), documentoAtualizado.getCliente())) {
			if(tipoDocumentoRepository.existsById(documentoAtualizado.getTipoDocumento())) {
				documentoRepository.findById(id).ifPresentOrElse(documento -> {
					documento.setCaixa(documentoAtualizado.getCaixa());
					documento.setTipoDocumento(documentoAtualizado.getTipoDocumento());
					documento.setCliente(documentoAtualizado.getCliente());
					documento.setNome(documentoAtualizado.getNome());
					documento.setObservacao(documentoAtualizado.getObservacao());
					documentoRepository.save(documento);
				}, () -> {
					throw new BadRequestException("Cadastro do documento não encontrado!");
				});
			} else {
				throw new BadRequestException("Cadastro do tipo de documento não encontrado!");
			}
		} else {
			throw new BadRequestException("Caixa não pertence ao cliente informado!");
		}
	}
	
	private Boolean isCaixaDoCliente(Integer idCaixa, Integer idCliente) {
		Optional<Caixa> caixa = caixaRepository.findById(idCaixa);
		Optional<Cliente> cliente = clienteRepository.findById(idCliente);
		if(caixa.isPresent()) {
			if(cliente.isPresent()) {
				if(caixa.get().getCliente() == cliente.get().getId()) {
					return true;
				}
			} else {
				throw new BadRequestException("Cadastro do cliente não encontrado!");
			}
		} else {
			throw new BadRequestException("Cadastro da caixa não encontrado!");
		}
		return false;
	}

}
