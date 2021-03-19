package br.com.hioktec.arquivodocs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.hioktec.arquivodocs.model.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {
	
	@Query("select d from Documento d"
			+ " where upper(nome) like upper(:nome)"
			+ " and (:cliente is null or cliente = :cliente)"
			+ " and (:caixa is null or caixa = :caixa)"
			+ " and (:tipoDocumento is null or tipoDocumento = :tipoDocumento)")
	List<Documento> findAllBy(@Param(value = "cliente") Integer cliente,
							  @Param(value = "caixa") Integer caixa,
							  @Param(value = "tipoDocumento") Integer tipoDocumento,
							  @Param(value = "nome") String nome);
}
