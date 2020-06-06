package com.log.entidades;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TB_DADOS_LOG")
public class DadosLog {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_dados_log", unique = true, nullable = false)
	private long idDadosLog;
		
	@Column(name = "dt_insercao" , nullable = false)
	Date dtInsercao;
	
	@Column(name = "ip_insercao" , nullable = false)
	String ipInsercao;
	
	@Column(name = "user_agent" , nullable = false)
	String userAgent;
	
	@OneToMany(mappedBy = "dadosLog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Log> logs;

	public long getIdDadosLog() {
		return idDadosLog;
	}

	public void setIdDadosLog(long idDadosLog) {
		this.idDadosLog = idDadosLog;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public Date getDtInsercao() {
		return dtInsercao;
	}


	public void setDtInsercao(Date dtInsercao) {
		this.dtInsercao = dtInsercao;
	}


	public String getIpInsercao() {
		return ipInsercao;
	}


	public void setIpInsercao(String ipInsercao) {
		this.ipInsercao = ipInsercao;
	}


	public String getUserAgent() {
		return userAgent;
	}


	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	
}
