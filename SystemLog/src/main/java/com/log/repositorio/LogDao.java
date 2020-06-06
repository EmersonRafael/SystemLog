package com.log.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.log.entidades.DadosLog;
import com.log.entidades.Log;

@Repository
public interface LogDao extends JpaRepository<DadosLog,Long> , LogDaoCustom{
	
}
