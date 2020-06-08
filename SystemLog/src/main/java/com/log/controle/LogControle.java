package com.log.controle;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.log.dto.LogDto;
import com.log.entidades.DadosLog;
import com.log.entidades.Log;
import com.log.servico.LogServico;

@RestController
@RequestMapping(value = "/api", produces = "application/json; charset=utf-8")
public class LogControle {

	@Autowired
	LogServico logServico;

	@GetMapping("/versao/")
	public String versao() {
		return "1.0 - Teste Sistema de Log";
	}

	@GetMapping("/consultar-log/")
	public ResponseEntity<List<LogDto>> consultarLog(
			@RequestParam(value = "dtInsersaoInicio", required = false) String dtInsersaoInicio,
			@RequestParam(value = "dtInsersaoFim", required = false) String dtInsersaoFim,
			@RequestParam(value = "ip", required = false) String ip,
			@RequestParam(value = "userAgent", required = false) String userAgent) {

		List<DadosLog> dados = logServico.listarPorFiltro(dtInsersaoInicio, dtInsersaoFim, ip, userAgent);
		List<LogDto> lista = new ArrayList<>();
		for (DadosLog dadosLog : dados) {
			LogDto logDto = logServico.converteDto(dadosLog);
			lista.add(logDto);
		}

		return new ResponseEntity<List<LogDto>>(lista, HttpStatus.OK);
	}

	@GetMapping("/consultar-log-list/")
	public ResponseEntity<List<LogDto>> consultarLogLista(
			@RequestParam(value = "idDados", required = true) Long idDados) {

		List<Log> dados = logServico.consultarLogPorIdDados(idDados);
		List<LogDto> lista = new ArrayList<>();
		for (Log dadosLog : dados) {
			LogDto logDto = logServico.converteDtoLog(dadosLog);
			lista.add(logDto);
		}

		return new ResponseEntity<List<LogDto>>(lista, HttpStatus.OK);
	}

	@PostMapping("/salvar-user/")
	public ResponseEntity<LogDto> salvarUser(HttpServletRequest request) {

		String ip = request.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		String userAgent = request.getHeader("User-Agent");

		DadosLog dadosLog = logServico.salvarDadosAntesUpload(userAgent, ip);
		LogDto logDto = logServico.converteDto(dadosLog);

		return new ResponseEntity<LogDto>(logDto, HttpStatus.OK);
	}

	@PostMapping("/salvar-log/{idUser}")
	public String salvarLog(@RequestParam("file") MultipartFile uploadingFiles, @PathVariable("idUser") Long idUser) {
		DadosLog dados = logServico.pegarDadosLogPorId(idUser, true);
		logServico.salvarLogUpload(uploadingFiles, dados);
		return "";
	}

	@DeleteMapping("/excluir-log/{idUser}")
	public String excluirLog(@PathVariable("idUser") Long idUser) {
		logServico.excluirLog(idUser);
		return "";
	}

	@PostMapping("/salvar-log-texto/")
	public String salvarLogTexto(@RequestBody String texto, HttpServletRequest request) {

		String ip = request.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		String userAgent = request.getHeader("User-Agent");

		logServico.salvarLogTexto(texto, userAgent, ip);
		return "";
	}

	@GetMapping(value = "/baixar-arquivo-log/{idLog}")
	@ResponseBody
	public ResponseEntity<byte[]> baixarArquivoLog(@PathVariable(value = "idLog") Long idLog) {

		Log log = logServico.consultarLogPorId(idLog);
		byte[] arquivoBytes = log.getArquivoLog();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.add("Content-Disposition", "filename=" + log.getNomeLog());

		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<>(arquivoBytes, headers, HttpStatus.OK);
		return response;

	}

}
