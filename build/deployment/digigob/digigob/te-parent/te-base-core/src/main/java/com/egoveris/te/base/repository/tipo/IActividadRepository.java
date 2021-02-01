package com.egoveris.te.base.repository.tipo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.te.base.model.Actividad;
import com.egoveris.te.base.model.TipoActividad;

public interface IActividadRepository extends JpaRepository<Actividad, Long> {
 
//	@Query("SELECT DISTINCT act FROM Actividad act where act.idObjetivo = :idObjetivo  ")
	@Query("SELECT DISTINCT act FROM Actividad act where act.parentId is null and act.idObjetivo = :idObjetivo order by act.fechaAlta")
//	@Query("select a from Aviso a where a.usuarioReceptor = ?1 and a.id in (select a.id from Aviso a left join a.documento documento left join documento.tipo tipodoc where tipodoc.esComunicable = ?2 ) order by aviso")
	  List<Actividad> buscarHistoricoActividades(@Param("idObjetivo") String idObjetivo);
	
	@Query(value = "SELECT ee FROM Actividad ee WHERE ee.tipoActividad = :tipoActividad AND ee.estado = :tipoDocumento AND ee.fechaAlta <= :hoy AND ee.fechaCierre IS NULL")
	List<Actividad> obtenerActividadSubsanacionPendiente(@Param("tipoActividad") TipoActividad tipoActividad,
			@Param("tipoDocumento") String tipoDocumento, @Param("hoy") Date hoy);
}
