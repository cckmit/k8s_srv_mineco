package com.egoveris.deo.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.deo.base.model.TipoDocumento;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {

  TipoDocumento findByAcronimoAndVersion(String acronimo, String version);

  @Modifying
  @Query(value = "UPDATE JBPM4_VARIABLE SET string_value_ = ?0 WHERE key_ = 'tipoDocumento' AND string_value_ = ?1", nativeQuery = true)
  void updateTareasExistentesConNuevaVersion(String idTipo, String idTipoAnterior);

  @Query("select tipoDocumento from TipoDocumento tipoDocumento where tipoDocumento.acronimo = :acronimo and tipoDocumento.version = (select max(tipoDocumento.version) from TipoDocumento tipoDocumento where tipoDocumento.acronimo = :acronimo)")
  List<TipoDocumento> buscarTipoDocumentoByAcronimo(@Param("acronimo") String acronimo);
  
  @Query("select tipoDocumento from TipoDocumento tipoDocumento where (tipoDocumento.acronimo = :acronimo or tipoDocumento.nombre = :acronimo) and tipoDocumento.version = (select max(tipoDocumento.version) from TipoDocumento tipoDocumento where tipoDocumento.acronimo = :acronimo or tipoDocumento.nombre = :acronimo)")
  List<TipoDocumento> buscarTipoDocumentoByAcronimoOrNombre(@Param("acronimo") String acronimo);

  @Query("select tipoDocumento from TipoDocumento tipoDocumento where tipoDocumento.acronimo = :acronimo and tipoDocumento.esManual=true and tipoDocumento.version = (select max(tipoDocumento.version) from TipoDocumento tipoDocumento where tipoDocumento.acronimo = :acronimo)")
  TipoDocumento buscarTipoDocumentoManualByAcronimo(@Param("acronimo") String acronimo);

  @Query("select tipoDocumento from TipoDocumento tipoDocumento left join fetch tipoDocumento.familia order by tipoDocumento.nombre asc")
  List<TipoDocumento> buscarTipoDocumento();

  @Query("select tipoDocumento from TipoDocumento tipoDocumento left join fetch tipoDocumento.familia order by tipoDocumento.familia.nombre asc, tipoDocumento.nombre asc, tipoDocumento.version desc")
  List<TipoDocumento> buscarTipoDocumentoOrdenadoPorFamiliaYTipo();

  @Query("select tipoDocumento from TipoDocumento tipoDocumento  left join fetch tipoDocumento.familia where tipoDocumento.estado = :estado AND tipoDocumento.esManual=:manual order by tipoDocumento.familia.nombre asc, tipoDocumento.acronimo asc, tipoDocumento.version desc")
  List<TipoDocumento> buscarTipoDocumentoByEstadosFiltradosManual(@Param("estado") String estado,
      @Param("manual") Boolean esManual);

  @Query("select tipoDocumento from TipoDocumento tipoDocumento  left join fetch tipoDocumento.familia where tipoDocumento.esComunicable = 1 AND tipoDocumento.estado = :estado AND tipoDocumento.esManual=:manual order by tipoDocumento.familia.nombre asc, tipoDocumento.acronimo asc, tipoDocumento.version desc")
  List<TipoDocumento> buscarTipoDocumentoByFiltradoComunicable(@Param("estado") String estado,
      @Param("manual") Boolean manual);

  @Query("select tipoDocumento from TipoDocumento tipoDocumento left join fetch tipoDocumento.familia where tipoDocumento.acronimo = :acronimo and tipoDocumento.version = (select max(tipoDocumento.version) from TipoDocumento tipoDocumento where tipoDocumento.acronimo = :acronimo)")
  List<TipoDocumento> buscarTipoDocumentoByAcronimoConFamilia(@Param("acronimo") String acronimo);

  @Query("select tipoDocumento from TipoDocumento tipoDocumento left join fetch tipoDocumento.familia where acronimo = :acronimo order by tipoDocumento.version desc")
  List<TipoDocumento> buscarDocumentoPorVersiones(@Param("acronimo") String acronimo);

  @Query("select tipoDocumento from TipoDocumento tipoDocumento where tipoDocumento.estado = :estado AND tipoDocumento.esManual=:manual AND tipoDocumento.familia.id=:familia AND tipoDocumento.version >= (select max(tipo.version) from TipoDocumento tipo where tipo.acronimo = tipoDocumento.acronimo order by tipoDocumento.familia.nombre asc, tipoDocumento.acronimo asc, tipoDocumento.version desc)")
  List<TipoDocumento> buscarTipoDocumentoByFamilia(@Param("estado") String estado,
      @Param("manual") Boolean manual, @Param("familia") Integer familia);

  @Query("select tipoDocumento from TipoDocumento tipoDocumento where tipoDocumento.familia.id =:familia AND tipoDocumento.version >= (select max(tipo.version) from TipoDocumento tipo where tipo.acronimo = tipoDocumento.acronimo) order by tipoDocumento.familia.nombre asc, tipoDocumento.acronimo asc, tipoDocumento.version desc")
  List<TipoDocumento> buscarTipoDocumentoSinFiltroByFamilia(@Param("familia") Integer familia);

  @Query("select tipoDocumento from TipoDocumento tipoDocumento  where tipoDocumento.estado = :estado AND tipoDocumento.esManual=:manual AND tipoDocumento.esComunicable=:comunicable AND tipoDocumento.familia=:familia AND tipoDocumento.version >= (select max(tipo.version) from TipoDocumento tipo where tipo.acronimo = tipoDocumento.acronimo) order by tipoDocumento.familia.nombre asc, tipoDocumento.acronimo asc, tipoDocumento.version desc")
  List<TipoDocumento> buscarTipoDocumentoByFamiliayComunicable(@Param("estado") String estado,
      @Param("manual") Boolean manual, @Param("familia") String familia,
      @Param("comunicable") Boolean comunicable);

  @Query("select tipoDocumento from TipoDocumento tipoDocumento where tipoDocumento.esComunicable=:comunicable AND tipoDocumento.familia=:familia AND tipoDocumento.version >= (select max(tipo.version) from TipoDocumento tipo where tipo.acronimo = tipoDocumento.acronimo) order by tipoDocumento.familia.nombre asc, tipoDocumento.acronimo asc, tipoDocumento.version desc")
  List<TipoDocumento> buscarTipoDocumentoSinFiltroByFamiliayComunicable(
      @Param("comunicable") Boolean comunicable, @Param("familia") String familia);

  @Query("select tipoDocumento from TipoDocumento tipoDocumento  where tipoDocumento.familia.id=:familia and tipoDocumento.estado = :estado AND tipoDocumento.esManual=:manual AND (tipoDocumento.acronimo like :acronimo or upper(tipoDocumento.nombre) like :nombre or upper(tipoDocumento.descripcion) like :descripcion) AND tipoDocumento.version >= (select max(tipo.version) from TipoDocumento tipo where tipo.acronimo = tipoDocumento.acronimo or upper(tipo.nombre) = tipoDocumento.nombre or upper(tipo.descripcion) = tipoDocumento.descripcion) order by tipoDocumento.familia.nombre asc, tipoDocumento.acronimo asc, tipoDocumento.version desc")
  List<TipoDocumento> buscarTipoDocumentoByFamiliaYAcronimo(@Param("familia") Integer familia,
      @Param("estado") String estado, @Param("manual") Boolean manual,
      @Param("acronimo") String acronimo, @Param("nombre") String nombre,
      @Param("descripcion") String descripcion);

  @Query("select tipoDocumento from TipoDocumento tipoDocumento  where tipoDocumento.familia=:familia and tipoDocumento.esComunicable=:comunicable and tipoDocumento.estado = :estado AND tipoDocumento.esManual=:manual AND (tipoDocumento.acronimo like :acronimo or upper(tipoDocumento.nombre) like :nombre or upper(tipoDocumento.descripcion) like :descripcion) AND tipoDocumento.version >= (select max(tipo.version) from TipoDocumento tipo where tipo.acronimo = tipoDocumento.acronimo or upper(tipo.nombre) = tipoDocumento.nombre or upper(tipo.descripcion) = tipoDocumento.descripcion) order by tipoDocumento.familia.nombre asc, tipoDocumento.acronimo asc, tipoDocumento.version desc")
  List<TipoDocumento> buscarTipoDocumentoByFamiliaAcronimoYComunicable(
      @Param("familia") String familia, @Param("comunicable") Boolean comunicable,
      @Param("estado") String estado, @Param("manual") Boolean manual,
      @Param("acronimo") String acronimo, @Param("nombre") String nombre,
      @Param("descripcion") String descripcion);

	@Query("select distinct t from TipoDocumento t join fetch t.familia where tipoProduccion = :tipo  and t.version >= (select max(tipo.version) from TipoDocumento tipo where tipo.acronimo = t.acronimo)")
	List<TipoDocumento> getDocumentTypeByProduction(@Param("tipo") Integer tipo);
	
	@Query("SELECT tipoDoc FROM TipoDocumento tipoDoc LEFT JOIN tipoDoc.familia fam WHERE tipoDoc.familia.id = fam.id AND fam.nombre = :famNombre AND tipoDoc.version = (SELECT MAX(version) FROM TipoDocumento tipo WHERE tipo.acronimo = tipoDoc.acronimo) ORDER BY tipoDoc.codigoTipoDocumentoSade")
	List<TipoDocumento> getTipoDocumentoByFamiliaNombre(@Param("famNombre")String famNombre);

	@Query("SELECT tipoDoc FROM TipoDocumento tipoDoc WHERE tipoDoc.version = (SELECT MAX(t.version) FROM TipoDocumento t WHERE t.acronimo= tipoDoc.acronimo) ORDER BY tipoDoc.acronimo")
	List<TipoDocumento> getTiposDocumentoHabilitados();
	
	@Query("SELECT td FROM TipoDocumento td WHERE td.esEspecial = :especial  AND td.estado= :estado AND "
    + "td.version = (SELECT MAX(t.version) FROM TipoDocumento t WHERE t.acronimo= td.acronimo) ORDER BY td.codigoTipoDocumentoSade,td.acronimo")
	List<TipoDocumento> getTipoDocumentoEspecial(@Param("especial")Boolean esEspecial, @Param("estado")String estado);
	
	@Query("SELECT td FROM TipoDocumento td WHERE td.esAutomatica = :automatica AND td.tieneTemplate = :tieneTemplate"
    + " AND td.estado = :estado AND td.esEspecial = :especial AND td.tieneToken = :tieneToken AND td.esFirmaConjunta = :firmaConjunta")
	List<TipoDocumento> getTiposDocumento(@Param("automatica")Boolean esAutomatica, @Param("tieneTemplate")Boolean tieneTemplate, @Param("estado")String estado, @Param("especial")Boolean esEspecial, @Param("tieneToken")Boolean tieneToken, @Param("firmaConjunta")Boolean esFirmaconjunta);
	
	List<TipoDocumento> findByAcronimo(String acronimo);
	
	@Query("SELECT tipoDoc FROM TipoDocumento tipoDoc WHERE tipoDoc.resultado = :resultado AND tipoDoc.version = (SELECT MAX(t.version) FROM TipoDocumento t WHERE t.acronimo= tipoDoc.acronimo) ORDER BY tipoDoc.acronimo")
	List<TipoDocumento> getTiposDocumentoByResultado(@Param("resultado") Boolean resultado);
	
	@Query("SELECT tipoDoc FROM TipoDocumento tipoDoc WHERE tipoDoc.idTipoDocumentoSade = :id AND tipoDoc.version = (SELECT MAX(t.version) FROM TipoDocumento t WHERE t.acronimo= tipoDoc.acronimo) ORDER BY tipoDoc.acronimo")
	List<TipoDocumento> buscarDocumentosPorIdSade(@Param("id") Integer id);
}
