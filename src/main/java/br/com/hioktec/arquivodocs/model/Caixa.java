package br.com.hioktec.arquivodocs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="caixa", schema = "arquivojm")
@Data
public class Caixa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "cliente", nullable = false)
	@NotNull(message = "Id do cliente é obrigatório!")
	private Integer cliente;
	
}
