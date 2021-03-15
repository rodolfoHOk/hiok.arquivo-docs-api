package br.com.hioktec.arquivodocs.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.hioktec.arquivodocs.report.ListagemDocumentosReport;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {
	
	@Autowired
	private ListagemDocumentosReport listagemDocumentosReport;
	
	@GetMapping(value = "/get-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public @ResponseBody byte[] getRelatorio(@RequestParam(name = "clientede", defaultValue = "1") Integer clienteDe,
											 @RequestParam(name = "clienteate", defaultValue = "5") Integer clienteAte) {
		try {
			listagemDocumentosReport.imprimir(clienteDe, clienteAte);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao gerar relatorio!");
		}
		try {
			File pdfFile = new File(listagemDocumentosReport.getPdfPath());
			InputStream is = new FileInputStream(pdfFile);
			return IOUtils.toByteArray(is);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao enviar relatorio!");
		}
	}
}
