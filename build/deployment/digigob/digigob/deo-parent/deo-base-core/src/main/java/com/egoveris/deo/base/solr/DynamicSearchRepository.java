package com.egoveris.deo.base.solr;

import com.egoveris.deo.model.model.ConsultaSolrRequest;
import com.egoveris.deo.model.model.DocumentoSolr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.data.domain.Page;

public interface DynamicSearchRepository<T> {

	public Page<DocumentoSolr> search(ConsultaSolrRequest consulta);
	
	public void save(SolrInputDocument doc) throws SolrServerException, IOException;
	
	public void deltaImport() throws SolrServerException, IOException;

}
