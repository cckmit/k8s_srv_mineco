package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.Aviso;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AvisoRepository extends JpaRepository<Aviso, Integer> {

  List<Aviso> findByUsuarioReceptor(String usuarioReceptor, Pageable pageRequest);
  
  List<Aviso> findByUsuarioReceptor(String usuarioReceptor);

  List<Aviso> findByUsuarioReceptorAndIdIn(String usuarioReceptor, Collection<Integer> ids);

  @Query("select a from Aviso a where a.usuarioReceptor = ?1 and a.id in (select a.id from Aviso a left join a.documento documento left join documento.tipo tipodoc where tipodoc.esComunicable = ?2 ) order by aviso")
  List<Aviso> buscarAvisosPorUsuarioYDocumentoComunicable(String usuarioReceptor,
      Integer comunicable);

  @Query("select count(aviso.id) from Aviso aviso where aviso.usuarioReceptor =:usuarioReceptor and aviso.id in (select aviso.id from Aviso aviso left join aviso.documento documento left join documento.tipo tipodoc where tipodoc.esComunicable =:comunicable)")
  Integer numeroAvisosPorUsuarioYDocumentoComunicable(
      @Param("usuarioReceptor") String usuarioReceptor, @Param("comunicable") Integer comunicable);

  @Modifying
  @Query("delete Aviso where usuarioReceptor = :usuario")
  void deleteByUsuario(@Param("usuario") String usuario);
}
