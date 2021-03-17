package br.com.hioktec.arquivodocs.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import br.com.hioktec.arquivodocs.restcontroller.exceptions.ErrosApi;

@RestControllerAdvice
public class AvisosDaApiController {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrosApi handlerErrosValidacao(MethodArgumentNotValidException ex) {
		List<String> mensagens = ex.getBindingResult().getAllErrors()
									.stream()
									.map(erro -> erro.getDefaultMessage())
									.collect(Collectors.toList());
		return new ErrosApi(mensagens);
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErrosApi> handlerResponseStatusException(ResponseStatusException ex){
		String mensagem = ex.getReason();
		HttpStatus status = ex.getStatus();
		return new ResponseEntity<ErrosApi>(new ErrosApi(mensagem), status);
	}
}
