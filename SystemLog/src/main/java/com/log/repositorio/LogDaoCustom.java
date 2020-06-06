package com.log.repositorio;

import java.util.List;


public interface LogDaoCustom {

	List<Object[]> listarPorFiltro(String dtInsersaoInicio, String dtInsersaoFim, String ip,  String userAgent);
	List<Object[]> consultarLogPorIdDados(Long id);
	List<Object[]> consultarLogPorId(Long id);
}
