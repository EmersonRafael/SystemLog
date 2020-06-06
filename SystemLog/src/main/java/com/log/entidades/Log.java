package com.log.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TB_LOG")
public class Log {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_log", unique = true, nullable = false)
	private long idLog;
	
	@Column(name = "texto_log" , nullable = false)
	byte[] arquivoLog;
	
	@Column(name = "nome_Log" , nullable = false)
	String nomeLog;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "idDadosLog")
    private DadosLog dadosLog;

	public long getIdLog() {
		return idLog;
	}

	public void setIdLog(long idLog) {
		this.idLog = idLog;
	}

	public byte[] getArquivoLog() {
		return arquivoLog;
	}

	public void setArquivoLog(byte[] arquivoLog) {
		this.arquivoLog = arquivoLog;
	}

	public String getNomeLog() {
		return nomeLog;
	}

	public void setNomeLog(String nomeLog) {
		this.nomeLog = nomeLog;
	}

	public DadosLog getDadosLog() {
		return dadosLog;
	}

	public void setDadosLog(DadosLog dadosLog) {
		this.dadosLog = dadosLog;
	}
	
	
	
}
