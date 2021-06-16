package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.FamiliaTipoDocumento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FamiliaTipoDocumentoRepository
    extends JpaRepository<FamiliaTipoDocumento, Integer> {

  @Query("SELECT f FROM FamiliaTipoDocumento f, TipoDocumento t where t.familia = f.id and t.id = :idTipoDocumento")
  List<FamiliaTipoDocumento> traerNombresFamiliasByIdTipoDocumento(
      @Param("idTipoDocumento") Integer idTipoDocumento);

  FamiliaTipoDocumento findByNombre(String nombre);

  List<FamiliaTipoDocumento> findByNombreOrderByNombre(String nombre);

  @Query("select familia from FamiliaTipoDocumento familia where familia.id in (select distinct(tipo.familia) from TipoDocumento tipo where tipo.esComunicable=:comunicable) order by familia.nombre")
  List<FamiliaTipoDocumento> buscarFamiliasByComunicable(@Param("comunicable") Boolean comunicable);

  // Condition familia.nombre was removed because it was not used in TipoDocumento service
  @Query("select familia from FamiliaTipoDocumento familia where familia.id in (select distinct(tipo.familia) from TipoDocumento tipo where tipo.estado=:estado AND tipo.esManual=:manual or tipo.acronimo like :acronimo or upper(tipo.nombre) =:nombre or upper(tipo.descripcion)=:descripcion) order by familia.nombre")
  List<FamiliaTipoDocumento> buscarFamiliaPorTipoDocumento(@Param("estado") String estado,
      @Param("manual") Boolean manual, @Param("acronimo") String acronimo,
      @Param("nombre") String nombre, @Param("descripcion") String descripcion);

  @Query("select familia from FamiliaTipoDocumento familia where familia.nombre like :nombrefamilia or familia.id in (select distinct(tipo.familia) from TipoDocumento tipo where tipo.estado=:estado and tipo.esComunicable=:comunicable AND tipo.esManual=:manual  or tipo.acronimo like :acronimo or upper(tipo.nombre)=:nombre or upper(tipo.descripcion)=:descripcion) order by familia.nombre")
  List<FamiliaTipoDocumento> buscarFamiliaPorTipoDocumentoYComunicable(
      @Param("estado") String estado, @Param("manual") Boolean manual,
      @Param("acronimo") String acronimo, @Param("comunicable") Boolean comunicable,
      @Param("nombre") String nombre, @Param("descripcion") String descripcion,
      @Param("nombrefamilia") String nombrefamilia);

  @Query("select familia from FamiliaTipoDocumento familia where familia.id in (select distinct(tipo.familia) from TipoDocumento tipo where tipo.estado = :estado AND tipo.esManual=:manual AND tipo.esComunicable=:comunicable) order by familia.nombre")
  List<FamiliaTipoDocumento> buscarTodasLasFamiliasByComunicable(@Param("estado") String estado,
      @Param("manual") Boolean manual, @Param("comunicable") Boolean comunicable);

  @Query("select familia from FamiliaTipoDocumento familia where familia.id in (select distinct(tipo.familia) from TipoDocumento tipo where tipo.estado = :estado AND tipo.esManual=:manual) order by familia.nombre")
  List<FamiliaTipoDocumento> buscarTodasLasFamilias(@Param("estado") String estado,
      @Param("manual") Boolean manual);

}
