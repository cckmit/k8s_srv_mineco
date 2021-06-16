package com.egoveris.ffdd.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.ffdd.base.model.Item;

public interface ItemsRepository extends JpaRepository<Item, Integer>{
	
	@Query("SELECT c FROM Item c WHERE c.componente.id=:id")
	List<Item> findByValor(@Param("id") final Integer id);
	
	@Modifying
	@Query("UPDATE Item i SET i.multivalor=:data WHERE i.id = :id")
	void updateMultivalor(@Param("data") final String data, @Param("id") final Integer id);
	
	@Query("SELECT c FROM Item c WHERE c.multivalor like :id")
	List<Item> findByMultivalor(@Param("id") final String elementoSeleccionado);
	
	List<Item> findByComponenteId(Integer idComponente);
	
}
