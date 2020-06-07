package com.log.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.log.entidades.DadosLog;

@Repository
public interface LogDao extends JpaRepository<DadosLog,Long> , LogDaoCustom{
	
}
