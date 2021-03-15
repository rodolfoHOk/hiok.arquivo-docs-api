package br.com.hioktec.arquivodocs.report;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class ListagemDocumentosReport {
	
	private static final String BASE_PATH = "src/main/resources/relatorio/";

	private static final String ARQUIVO_JRXML = "listagemDocumentos.jrxml";
	
	private static final String ARQUIVO_PDF = "listagemDocumentos.pdf";
		
	private final String jrxmlPath;
	
	private final String pdfPath;
	
	public ListagemDocumentosReport() {
		this.jrxmlPath = BASE_PATH + ARQUIVO_JRXML;
		this.pdfPath = BASE_PATH + ARQUIVO_PDF;
	}
	
	public void imprimir(Integer clienteDe, Integer clienteAte) throws Exception {
		
		// Cria conexao com o banco de dados
		Connection conexao = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/arquivo", "postgres", "pgtestes");
			conexao.setSchema("arquivojm");
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		// Configura os parâmetros.
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("clienteDe", clienteDe);
		parametros.put("clienteAte", clienteAte);
		
		// Compila o template.
		JasperReport report = JasperCompileManager.compileReport(jrxmlPath);
		
		// Preenche o relatório buscando os dados no banco com a query sql que está no relatório.
		JasperPrint print = JasperFillManager.fillReport(report, parametros, conexao);

		// exporta como pdf
		JasperExportManager.exportReportToPdfFile(print, pdfPath);
	}

	public String getPdfPath() {
		return this.pdfPath;
	}
}
