package br.com.hioktec.arquivodocs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.hioktec.arquivodocs.model.Caixa;

public interface CaixaRepository extends JpaRepository<Caixa, Integer> {
	
	@Query("select c from Caixa c where c.cliente = :idCliente")
	List<Caixa> getAllByCliente(@Param("idCliente") Integer idCliente);
	
	@Query("select c.id from Caixa c where c.cliente = :idCliente")
	List<Integer> getIdsByCliente(@Param("idCliente") Integer idCliente);
}
