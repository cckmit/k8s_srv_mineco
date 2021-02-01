package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.Firmante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FirmanteRepository extends JpaRepository<Firmante, Integer> {

  List<Firmante> findByWorkflowIdOrderByOrden(String workflowId);

  List<Firmante> findByWorkflowIdAndEstadoFirmaOrderByOrden(String workflowId,
      Boolean estadoFirma);

  List<Firmante> findByUsuarioFirmanteAndEstadoFirmaIsNull(String usuario);

  List<Firmante> findByWorkflowIdAndEstadoRevisionOrderByOrden(String workflowId,
      boolean estadoRevision);

  Firmante findByUsuarioFirmanteAndWorkflowIdAndEstadoFirmaOrderByOrden(String usuario,
      String workflowId, boolean estadoFirma);

  List<Firmante> findByUsuarioRevisorAndWorkflowIdAndEstadoRevisionOrderByOrden(String usuario,
      String workflowId, boolean estadoRevision);

  List<Firmante> findByUsuarioFirmanteAndWorkflowIdAndEstadoRevisionOrderByOrden(String usuario,
      String workflowId, boolean estadoRevision);

  List<Firmante> findByWorkflowIdAndEstadoFirmaOrderByOrdenDesc(String workflowId, boolean estadoFirma);

  Firmante findByWorkflowIdAndEstadoRevisionOrderByOrdenDesc(String workflowId,
      boolean estadoRevision);

  List<Firmante> findByWorkflowId(String workflowId);

  @Query("update Firmante set usuarioRevisor = null, estadoRevision = 0 where usuarioRevisor=:usuarioRevisor ")
  void deleteByUsuario(@Param("usuarioRevisor") String usuario);

}
