package br.com.hioktec.arquivodocs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.hioktec.arquivodocs.model.Cliente;
import br.com.hioktec.arquivodocs.model.dto.ClienteSucintoDto;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	Optional<Cliente> findByCnpj(String cnpj);
	
	@Query("select c from Cliente c where upper(nome) like upper(:nome)")
	List<Cliente> findByNome(@Param("nome") String nome);
	
	@Query("select new br.com.hioktec.arquivodocs.model.dto.ClienteSucintoDto(c.id , c.nome) from Cliente c where upper(nome) like upper (:nome)")
	List<ClienteSucintoDto> getClientesSucintoByNome(@Param("nome") String nome);
}
