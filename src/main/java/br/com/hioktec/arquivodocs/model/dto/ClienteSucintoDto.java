package br.com.hioktec.arquivodocs.model.dto;


import lombok.Getter;

@Getter
public class ClienteSucintoDto {

	private Integer id;
	
	private String nome;
	
	public ClienteSucintoDto(Integer id, String nome) {	
		this.id = id;
		this.nome = nome;
	}
}
