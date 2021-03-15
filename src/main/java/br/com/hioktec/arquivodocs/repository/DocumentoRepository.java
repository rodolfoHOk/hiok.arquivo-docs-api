package br.com.hioktec.arquivodocs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hioktec.arquivodocs.model.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

}
