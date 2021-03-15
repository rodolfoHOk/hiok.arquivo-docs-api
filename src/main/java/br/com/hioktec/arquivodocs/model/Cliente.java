package br.com.hioktec.arquivodocs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CNPJ;

import lombok.Data;

@Entity
@Table(name="cliente", schema = "arquivojm")
@Data
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nome", length = 45, nullable = false)
	@NotEmpty(message = "Campo nome é Obrigatório")
	private String nome;
	
	@Column(name = "cnpj", length = 45, unique = true)
	@CNPJ(message = "CNPJ Inválido")
	private String cnpj;
	
	@Column(name = "endereco", length = 45)
	private String endereco;

}
