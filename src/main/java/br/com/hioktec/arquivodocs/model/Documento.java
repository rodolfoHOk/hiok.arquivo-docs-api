package br.com.hioktec.arquivodocs.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="documento", schema = "arquivojm")
@Data
public class Documento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "caixa", nullable = false)
	@NotNull(message = "Id da caixa é obrigatório!")
	private Integer caixa;
	
	@Column(name = "tipo_documento", nullable = false)
	@NotNull(message = "Id do tipo de documento é obrigatório!")
	private Integer tipoDocumento;
	
	@Column(name = "cliente", nullable = false)
	@NotNull(message = "Id do cliente é obrigatório!")
	private Integer cliente;
	
	@Column(name = "nome", nullable = false, length = 45)
	@NotEmpty(message = "Campo nome é Obrigatório")
	private String nome;
	
	@Column(name = "dt_entrada")
	private Date dtEntrada;
	
	@Column(name = "observacao")
	private String observacao;

}
