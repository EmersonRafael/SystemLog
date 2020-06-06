package com.log.servico;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.log.dto.LogDto;
import com.log.entidades.DadosLog;
import com.log.entidades.Log;
import com.log.repositorio.LogDao;

@Service
public class LogServico {

	@Autowired LogDao logDao;
	
	
	@Transactional(readOnly = true)
	public List<DadosLog> listarPorFiltro(String dtInsersaoInicio, String dtInsersaoFim, String ip,  String userAgent){
		
		List<DadosLog> listaDados = new ArrayList<DadosLog>();
		List<Object[]> lista = logDao.listarPorFiltro(dtInsersaoInicio, dtInsersaoFim, ip, userAgent);
		
		if(lista != null && !lista.isEmpty()) {
			
			SimpleDateFormat formatUS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for (Object[] objects : lista) {
				DadosLog dados = new DadosLog();
				dados.setIdDadosLog(Long.parseLong(objects[0].toString()));
				
				try {
					Date data = formatUS.parse(objects[1].toString());
					dados.setDtInsercao(data);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				if(objects[2] != null)
				dados.setIpInsercao(objects[2].toString());
				
				if(objects[3] != null)
				dados.setUserAgent(objects[3].toString());
				
				listaDados.add(dados);
			}
		}
		
		
		return listaDados;
	}
	
	
	public DadosLog salvarDadosAntesUpload(String userAgent, String ip) {

		DadosLog dadosLog = new DadosLog();
		dadosLog.setDtInsercao(new Date());
		dadosLog.setIpInsercao(ip);
		dadosLog.setUserAgent(userAgent);
		logDao.save(dadosLog);
		
		return dadosLog;
	}
	
	
	@Transactional(readOnly = true)
	public DadosLog pegarDadosLogPorId(Long id, boolean listaLog) {
		
		DadosLog dadosLog = logDao.getOne(id);
		
		if(listaLog)
		Hibernate.initialize(dadosLog.getLogs());
		
		return dadosLog;
	}
	
	public void salvarLogUpload(MultipartFile uploadingFiles, DadosLog dadosLog) {
		
		if(dadosLog.getLogs() == null)
			dadosLog.setLogs(new ArrayList<>());
		
		Log log = new Log();
		log.setNomeLog(uploadingFiles.getOriginalFilename());
		
		try {
			log.setArquivoLog(uploadingFiles.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		log.setDadosLog(dadosLog);
		dadosLog.getLogs().add(log);

		logDao.save(dadosLog);
	}
	

	public void salvarLogTexto(String texto, String userAgent, String ip) {
		
		DadosLog dadosLog = new DadosLog();
		dadosLog.setDtInsercao(new Date());
		dadosLog.setIpInsercao(ip);
		dadosLog.setUserAgent(userAgent);

		dadosLog.setLogs(new ArrayList<>());
		Log log = new Log();
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		log.setNomeLog(format.format(dadosLog.getDtInsercao())+"_log.log");
		log.setArquivoLog(texto.getBytes());
		log.setDadosLog(dadosLog);
		
		dadosLog.getLogs().add(log);

		logDao.save(dadosLog);
	}
	
	
	public LogDto converteDto(DadosLog dados) {
		
		LogDto logDto = new LogDto();
		logDto.setIdDadosLog(dados.getIdDadosLog());
		logDto.setIp(dados.getIpInsercao());
		logDto.setUserAgent(dados.getUserAgent());
		logDto.setDtInsercao(dados.getDtInsercao());
		
		return logDto;
	}
	
	public LogDto converteDtoLog(Log dados) {
		
		LogDto logDto = new LogDto();
		logDto.setNome(dados.getNomeLog());
//		logDto.setArquivo(dados.getArquivoLog());
		logDto.setIdLog(dados.getIdLog());
		
		return logDto;
	}
	
	public void excluirLog(Long id) {
		logDao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<Log> consultarLogPorIdDados(Long id){
		
		List<Log> log = new ArrayList<Log>();
		List<Object[]> lista = logDao.consultarLogPorIdDados(id);
		
		for (Object[] objects : lista) {
			
			Log lg = new Log();
			lg.setIdLog(Long.parseLong(objects[0].toString()));
			
			if(objects[1] != null)
			lg.setNomeLog(objects[1].toString());
			
			
			log.add(lg);
		}
		
		return log;
	}
	
	@Transactional(readOnly = true)
	public Log consultarLogPorId(Long id){
		
		List<Object[]> lista = logDao.consultarLogPorId(id);
		Log lg = new Log();
		
		for (Object[] objects : lista) {
			
			lg.setIdLog(Long.parseLong(objects[0].toString()));
			
			if(objects[1] != null)
			lg.setNomeLog(objects[1].toString());
			
			if(objects[2] != null)
			 lg.setArquivoLog((byte[]) objects[2]);
			
		}
		
		return lg;
	}
	
	
}
