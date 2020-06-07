package com.log.repositorio;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class LogDaoImpl implements LogDaoCustom {

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listarPorFiltro(String dtInsersaoInicio, String dtInsersaoFim, String ip, String userAgent) {

		StringBuffer sql = new StringBuffer();
		sql.append("select id_dados_log, dt_insercao, ip_insercao, user_agent from tb_dados_log");
		String where = null;

		if (dtInsersaoInicio != null && !dtInsersaoInicio.isEmpty()) {
			where = " where dt_insercao >= '" + dtInsersaoInicio + "'";
		}

		if (dtInsersaoFim != null && !dtInsersaoFim.isEmpty() && where == null) {
			where = " where dt_insercao <= '" + dtInsersaoFim + "'";
		} else if (dtInsersaoFim != null && !dtInsersaoFim.isEmpty()) {
			where = where + " and dt_insercao <= '" + dtInsersaoFim + "'";
		}

		if (ip != null && !ip.isEmpty() && where == null) {
			where = " where ip_insercao = '" + ip + "'";
		} else if (ip != null && !ip.isEmpty()) {
			where = where + " and ip_insercao = '" + ip + "'";
		}

		if (userAgent != null && !userAgent.isEmpty() && where == null) {
			where = " where user_agent like '%" + userAgent + "%'";
		} else if (userAgent != null && !userAgent.isEmpty()) {
			where = where + " and user_agent like '%" + userAgent + "%'";
		}

		if (where != null)
			sql.append(where);

		Query query = entityManager.createNativeQuery(sql.toString());
		List<Object[]> lista = query.getResultList();

		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> consultarLogPorIdDados(Long id) {

		StringBuffer sql = new StringBuffer();
		sql.append("select id_log , nome_log from tb_log where id_dados_log = " + id);
		Query query = entityManager.createNativeQuery(sql.toString());
		List<Object[]> lista = query.getResultList();

		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> consultarLogPorId(Long id) {

		StringBuffer sql = new StringBuffer();
		sql.append("select id_log , nome_log , texto_log from tb_log where id_log = " + id);
		Query query = entityManager.createNativeQuery(sql.toString());
		List<Object[]> lista = query.getResultList();

		return lista;
	}

}
