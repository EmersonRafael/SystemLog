package com.log.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogDto {

	// Campos Tabela Pai
	long idDadosLog;
	String userAgent;
	String ip;
	Date dtInsercao;
	String dataFormatada;
	
	// Campos Filho
	String nome;
	Long idLog;
	byte[] arquivo;
	
	public byte[] getArquivo() {
		return arquivo;
	}
	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getIdLog() {
		return idLog;
	}
	public void setIdLog(Long idLog) {
		this.idLog = idLog;
	}
	public long getIdDadosLog() {
		return idDadosLog;
	}
	public void setIdDadosLog(long idDadosLog) {
		this.idDadosLog = idDadosLog;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getDtInsercao() {
		return dtInsercao;
	}
	public void setDtInsercao(Date dtInsercao) {
		this.dtInsercao = dtInsercao;
	}
	public String getDataFormatada() {
		
		SimpleDateFormat formatBR = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		if(dtInsercao != null) {
			dataFormatada = formatBR.format(dtInsercao);
		}
		
		return dataFormatada;
	}
	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}
	
	
}
