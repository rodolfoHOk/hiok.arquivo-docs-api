package br.com.hioktec.arquivodocs.rest.exceptions;

public class BadRequestException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public BadRequestException(String mensagem) {
		super(mensagem);
	}
	
}
