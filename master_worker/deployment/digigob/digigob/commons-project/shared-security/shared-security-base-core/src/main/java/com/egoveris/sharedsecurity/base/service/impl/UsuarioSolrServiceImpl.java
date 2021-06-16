package com.egoveris.sharedsecurity.base.service.impl;

import com.egoveris.sharedsecurity.base.exception.SecurityAccesoDatosException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.sharedsecurity.base.model.UsuarioSolr;
import com.egoveris.sharedsecurity.base.service.IUsuarioSolrService;
import com.egoveris.sharedsecurity.base.util.Constantes;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("usuarioSolrService")
public class UsuarioSolrServiceImpl implements IUsuarioSolrService{

	
	@Autowired
	private SolrServer solrServerUSUARIOS;

	protected final transient Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean addToIndex(UsuarioSolr usuario) {
		boolean success = true;
		try {
			solrServerUSUARIOS.addBean(usuario);
			solrServerUSUARIOS.commit();
		} catch (MalformedURLException e) {
			logger.error("Error parsing URL address:" + e.getMessage(), e);
			success = false;
		} catch (Exception ex) {
			logger.error("Could not add to solr index", ex);
			success = false;
		}
		return success;
	}
	
	@Override
	public void limpiarIndice(){
		try{
			solrServerUSUARIOS.deleteByQuery("*:*");
			solrServerUSUARIOS.commit();			
		}catch(Exception e){
			logger.error("Error al limpiar el solr: " + e.getMessage());
		}
	}

	@Override
	public boolean addToIndex(Collection<UsuarioSolr> usuarios) {
		boolean success = true;
		try {
			solrServerUSUARIOS.addBeans(usuarios);
			solrServerUSUARIOS.commit();
		} catch (MalformedURLException e) {
			logger.error("Error parsing URL address:" + e.getMessage(), e);
			success = false;
		} catch (Exception ex) {
			logger.error("Could not add to solr index", ex);
			success = false;
		}
		return success;
	}

	@Override
	public UsuarioSolr searchByUsername(String username) throws SecurityAccesoDatosException {
		try {
			SolrQuery solrQuery = new SolrQuery(Constantes.OBTENER_USERNAME_SOLR + username);
			QueryResponse response;
			List<UsuarioSolr> list = new ArrayList<UsuarioSolr>();

			solrQuery.setRows(1); // Maximo 1 resultado!!!

			response = solrServerUSUARIOS.query(solrQuery);
			SolrDocumentList documents = response.getResults();
			UsuarioSolr reg = null;
			for (SolrDocument document : documents) {

				reg = convertirSolrDocument(document);
				list.add(reg);
			}
			return reg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SecurityAccesoDatosException("No se han podido recuperar los registro de SOLR", e);
		}
	}

	private UsuarioSolr convertirSolrDocument(SolrDocument document) {
		UsuarioSolr reg = new UsuarioSolr();

		reg.setUsername(this.<String> getFromDocument(Constantes.SOLR_FIELD_USERNAME, document));
		reg.setCodigoReparticion(this.<String> getFromDocument(Constantes.SOLR_FIELD_CODIGO_REPARTICION, document));
		reg.setCodigoSectorInterno(this.<String> getFromDocument(Constantes.SOLR_FIELD_CODIGO_SECTOR, document));
		reg.setCodigoSectorInternoOriginal(this.<String>getFromDocument(Constantes.SOLR_FIELD_CODIGO_SECTOR_ORIGINAL, document));
		reg.setCuit(this.<String> getFromDocument(Constantes.SOLR_FIELD_CUIT, document));
		reg.setEmail(this.<String> getFromDocument(Constantes.SOLR_FIELD_EMAIL, document));
		reg.setIsAccountNonExpired(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_ACCOUNT_NON_EXPIRED, document));
		reg.setIsAccountNonLocked(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_ACCOUNT_NON_LOCKED, document));
		reg.setIsCredentialsNonExpired(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_CREDENCIAL_NON_EXPIRED, document));
		reg.setIsEnabled(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_ENABLED, document));
		reg.setIsMultireparticion(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_MULTIREPARTICION, document));
		reg.setNombreApellido(this.<String> getFromDocument(Constantes.SOLR_FIELD_NOMBRE_APELLIDO, document));
		reg.setSupervisor(this.<String> getFromDocument(Constantes.SOLR_FIELD_SUPERVISOR, document));

		reg.setApoderado(this.<String> getFromDocument(Constantes.SOLR_FIELD_APODERADO, document));
		reg.setExternalizarFirmaGEDO(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_EXT_GEDO, document));
		reg.setExternalizarFirmaLOYS(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_EXT_LOYS, document));
		reg.setExternalizarFirmaCCOO(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_EXT_CCOO, document));
		reg.setExternalizarFirmaSIGA(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_EXT_SIGA, document));
		reg.setCargo(this.<String> getFromDocument(Constantes.SOLR_FIELD_CARGO, document));
		reg.setUsuarioRevisor(this.<String> getFromDocument(Constantes.SOLR_FIELD_REVISOR, document));
		reg.setAceptacionTYC(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_ACEPTACION_TYC, document));
		reg.setSectorMesa(this.<String> getFromDocument(Constantes.SOLR_FIELD_SECTOR_MESA, document));
		reg.setCodigoReparticionOriginal(this.<String> getFromDocument(Constantes.SOLR_FIELD_CODIGO_REPARTICION_ORIGINAL, document));
		reg.setNombreReparticionOriginal(this.<String> getFromDocument(Constantes.SOLR_FIELD_NOMBRE_REPARTICION_ORIGINAL, document));

		return reg;
	}
	
	private Usuario convertirSolrDocumentUsuario (SolrDocument document) {
		Usuario reg = new Usuario();

		reg.setUsername(this.<String> getFromDocument(Constantes.SOLR_FIELD_USERNAME, document));
		reg.setCodigoReparticion(this.<String> getFromDocument(Constantes.SOLR_FIELD_CODIGO_REPARTICION, document));
		reg.setCodigoSectorInterno(this.<String> getFromDocument(Constantes.SOLR_FIELD_CODIGO_SECTOR, document));
		reg.setCodigoSectorInternoOriginal(this.<String>getFromDocument(Constantes.SOLR_FIELD_CODIGO_SECTOR_ORIGINAL, document));
		reg.setCuit(this.<String> getFromDocument(Constantes.SOLR_FIELD_CUIT, document));
		reg.setEmail(this.<String> getFromDocument(Constantes.SOLR_FIELD_EMAIL, document));
		reg.setIsAccountNonExpired(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_ACCOUNT_NON_EXPIRED, document));
		reg.setIsAccountNonLocked(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_ACCOUNT_NON_LOCKED, document));
		reg.setIsCredentialsNonExpired(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_CREDENCIAL_NON_EXPIRED, document));
		reg.setIsEnabled(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_ENABLED, document));
		reg.setIsMultireparticion(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_MULTIREPARTICION, document));
		reg.setNombreApellido(this.<String> getFromDocument(Constantes.SOLR_FIELD_NOMBRE_APELLIDO, document));
		reg.setSupervisor(this.<String> getFromDocument(Constantes.SOLR_FIELD_SUPERVISOR, document));

		reg.setApoderado(this.<String> getFromDocument(Constantes.SOLR_FIELD_APODERADO, document));
		reg.setExternalizarFirmaGEDO(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_EXT_GEDO, document));
		reg.setExternalizarFirmaLOYS(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_EXT_LOYS, document));
		reg.setExternalizarFirmaCCOO(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_EXT_CCOO, document));
		reg.setExternalizarFirmaSIGA(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_EXT_SIGA, document));
		reg.setCargo(this.<String> getFromDocument(Constantes.SOLR_FIELD_CARGO, document));
		reg.setUsuarioRevisor(this.<String> getFromDocument(Constantes.SOLR_FIELD_REVISOR, document));
		reg.setAceptacionTYC(this.<Boolean> getFromDocument(Constantes.SOLR_FIELD_ACEPTACION_TYC, document));
		reg.setSectorMesa(this.<String> getFromDocument(Constantes.SOLR_FIELD_SECTOR_MESA, document));
		reg.setCodigoReparticionOriginal(this.<String> getFromDocument(Constantes.SOLR_FIELD_CODIGO_REPARTICION_ORIGINAL, document));
		reg.setNombreReparticionOriginal(this.<String> getFromDocument(Constantes.SOLR_FIELD_NOMBRE_REPARTICION_ORIGINAL, document));

		return reg;
	}

	@SuppressWarnings("unchecked")
	private <T> T getFromDocument(String fieldName, SolrDocument doc) {
		if (doc.get(fieldName) != null) {
			return (T) doc.get(fieldName);
		}
		return null;
	}

	@Override
	public List<UsuarioSolr> searchByQuery(String value) throws SecurityAccesoDatosException {
		try {
			SolrQuery solrQuery = new SolrQuery(value);
			QueryResponse response;
			List<UsuarioSolr> list = new LinkedList<UsuarioSolr>();

			solrQuery.setRows(100);

			response = solrServerUSUARIOS.query(solrQuery);
			SolrDocumentList documents = response.getResults();
			UsuarioSolr reg = null;
			for (SolrDocument document : documents) {
				reg = convertirSolrDocument(document);
				list.add(reg);
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SecurityAccesoDatosException("No se han podido recuperar los registro de SOLR", e);
		}
	}

	@Override
	public HashMap<String, Usuario> obtenerTodos () throws SecurityAccesoDatosException {
		try {			
			HashMap<String, Usuario> list = new HashMap<String, Usuario>();
			SolrDocumentList documents = obtenerDocumentosSolrPorQuery(Constantes.OBTENER_TODOS_SOLR);
			for (SolrDocument document : documents) {
				Usuario reg = convertirSolrDocumentUsuario(document);
				list.put(reg.getUsername(), reg);
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SecurityAccesoDatosException(
					"No se han podido recuperar los registro de SOLR", e);
		}
	}
	
	private SolrDocumentList obtenerDocumentosSolrPorQuery(String query) throws SolrServerException{
		SolrQuery solrQuery = new SolrQuery(query);
		solrQuery.setRows(5000000);
		QueryResponse response;
		response = solrServerUSUARIOS.query(solrQuery);
		return (response.getResults());
	}
	
	@Override
	public List<UsuarioReducido> searchByQueryUsuarioReducido(String query) throws SecurityAccesoDatosException {
		try {
			List<UsuarioReducido> list = new LinkedList<UsuarioReducido>();
			SolrDocumentList documents = obtenerDocumentosSolrPorQuery(query);
			for (SolrDocument document : documents) {		
				UsuarioReducido reg = new UsuarioReducido();
				reg.setUsername(this.<String> getFromDocument(Constantes.SOLR_FIELD_USERNAME, document));
				reg.setNombreApellido(this.<String> getFromDocument(Constantes.SOLR_FIELD_NOMBRE_APELLIDO, document));
				list.add(reg);
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SecurityAccesoDatosException("No se han podido recuperar los registro de SOLR", e);
		}
	}

}
