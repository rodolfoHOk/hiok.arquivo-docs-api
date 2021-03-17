package br.com.hioktec.arquivodocs.restcontroller.exceptions;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public class ErrosApi {
	
	private List<String> mensagensErros;
	
	public ErrosApi (List<String> mensagens) {
		this.mensagensErros = mensagens;
	}
	
	public ErrosApi(String mensagem) {
		this.mensagensErros = Arrays.asList(mensagem);
	}
}
