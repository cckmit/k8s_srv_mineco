package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.ArchivoEmbebido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivoEmbebidoRepository extends JpaRepository<ArchivoEmbebido, Integer> {

	List<ArchivoEmbebido> findByIdTask(String idTask);
}
