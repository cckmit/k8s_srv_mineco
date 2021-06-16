package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.ConsultaSolrRequest;
import com.egoveris.deo.model.model.DocumentoSolr;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.data.domain.Page;
import org.terasoluna.plus.common.exception.ApplicationException;

public interface DynamicSearchRepository<T> {

	public Page<DocumentoSolr> search(ConsultaSolrRequest consulta);
	
	public void save(SolrInputDocument doc) throws SolrServerException, ApplicationException;
	
	public void deltaImport() throws SolrServerException, ApplicationException;

}
