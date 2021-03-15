package br.com.hioktec.arquivodocs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hioktec.arquivodocs.model.TipoDocumento;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer>{

}
