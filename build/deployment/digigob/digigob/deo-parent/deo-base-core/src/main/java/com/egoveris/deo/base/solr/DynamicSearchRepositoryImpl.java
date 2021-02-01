package com.egoveris.deo.base.solr;

import com.egoveris.deo.model.model.ConsultaSolrRequest;
import com.egoveris.deo.model.model.DocumentoSolr;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.solr.VersionUtil;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.convert.DateTimeConverters;
import org.springframework.data.solr.core.convert.NumberConverters;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
public class DynamicSearchRepositoryImpl implements DynamicSearchRepository<DocumentoSolr> {

  @Resource
  private SolrTemplate solrTemplate;

  @Value("${gedo.deltaimport.enable: false}")
  private boolean deltaImpEnabled;

  private final GenericConversionService conversionService = new GenericConversionService();
  {
    if (!conversionService.canConvert(java.util.Date.class, String.class)) {
      conversionService.addConverter(DateTimeConverters.JavaDateConverter.INSTANCE);
    }
    if (!conversionService.canConvert(Number.class, String.class)) {
      conversionService.addConverter(NumberConverters.NumberConverter.INSTANCE);
    }
    if (VersionUtil.isJodaTimeAvailable()) {
      if (!conversionService.canConvert(org.joda.time.ReadableInstant.class, String.class)) {
        conversionService.addConverter(DateTimeConverters.JodaDateTimeConverter.INSTANCE);
      }
      if (!conversionService.canConvert(org.joda.time.LocalDateTime.class, String.class)) {
        conversionService.addConverter(DateTimeConverters.JodaLocalDateTimeConverter.INSTANCE);
      }
    }
  }

  private static final String WILDCARD = "*";
  private static final String OR = " OR ";
  private static final String AND = " AND ";
  private static final String EQUALS = ":";
  private static final String PARENTHESES_START = "(";
  private static final String PARENTHESES_END = ")";
  private static final String SQUARE_BRACKETS_START = "[";
  private static final String SQUARE_BRACKETS_END = "]";
  private static final String BETWEEN = " TO ";

  @Scheduled(cron = "${gedo.deltaimport.cron: 0 * * * * ?}")
  public void deltaImport() throws SolrServerException, IOException {
    if (deltaImpEnabled) {
      ModifiableSolrParams params = new ModifiableSolrParams();
      params.set("command", "full-import");
      params.set("clean", false);
      params.set("commit", true);
      QueryRequest request = new QueryRequest(params);
      request.setPath("/dataimport");
      solrTemplate.getSolrServer().request(request);
    }
  }

  public void save(SolrInputDocument solrDoc) throws SolrServerException, IOException {
    solrTemplate.saveDocument(solrDoc);
    solrTemplate.getSolrServer().commit();
  }

  public Page<DocumentoSolr> search(ConsultaSolrRequest consulta) {
    String conditions = createSearchCondition(consulta);
    Pageable pageSort = createPageSort(consulta);
    SimpleQuery search = new SimpleQuery(conditions, pageSort);
    return solrTemplate.queryForPage(search, DocumentoSolr.class);
  }

  private Pageable createPageSort(ConsultaSolrRequest consulta) {
    Direction dir = null;
    if (consulta.getCriteria().equals(ConsultaSolrRequest.ORDER_DESC)) {
      dir = Direction.DESC;
    } else {
      dir = Direction.ASC;
    }
    return new PageRequest(consulta.getPageIndex(), consulta.getPageSize(),
        new Sort(dir, consulta.getSortColumn()));
  }

  private String createSearchCondition(ConsultaSolrRequest consulta) {
    StringBuffer buffer = new StringBuffer();
    buffer = agregarCondicionFirmante(consulta, agregarCondicionFecha(consulta,
        agregarCondicionEquals(consulta, agregarCondicionContains(consulta, buffer))));
    return buffer.toString();
  }

  private StringBuffer agregarCondicionEquals(ConsultaSolrRequest consulta, StringBuffer buffer) {
    for (String key : consulta.getEqualsMap().keySet()) {
      andEqualsCondition(buffer, key);
      buffer.append(convert(consulta.getEqualsMap().get(key)));
    }
    return buffer;
  }

  private StringBuffer agregarCondicionContains(ConsultaSolrRequest consulta,
      StringBuffer buffer) {
    for (String key : consulta.getContainsMap().keySet()) {

      andEqualsCondition(buffer, key);

      String[] terminos = consulta.getContainsMap().get(key).toLowerCase().split(" ");

      if (terminos.length > 0) {
        buffer.append(PARENTHESES_START);
      }

      for (int i = 0; i < terminos.length; i++) {
        if (i != 0) {
          buffer.append(OR);
        }
        buffer.append(WILDCARD).append(removeWildcards(terminos[i])).append(WILDCARD);
      }
      if (terminos.length > 0) {
        buffer.append(PARENTHESES_END);
      }
    }
    return buffer;
  }

  private StringBuffer agregarCondicionFecha(ConsultaSolrRequest consulta, StringBuffer buffer) {
    if (consulta.getFechaDesde() != null && consulta.getFechaHasta() != null) {

      andEqualsCondition(buffer, ConsultaSolrRequest.FIELD_FECHA_CREACION);
      buffer.append(SQUARE_BRACKETS_START).append(convert(consulta.getFechaDesde()))
          .append(BETWEEN).append(convert(consulta.getFechaHasta())).append(SQUARE_BRACKETS_END);
    }
    return buffer;
  }

  private StringBuffer agregarCondicionFirmante(ConsultaSolrRequest consulta,
      StringBuffer buffer) {
    if (consulta.getUsuarioFirmante() != null) {
      andCondition(buffer).append(PARENTHESES_START)
          .append(ConsultaSolrRequest.FIELD_USUARIO_FIRMANTE).append(EQUALS)
          .append(consulta.getUsuarioFirmante()).append(OR)
          .append(ConsultaSolrRequest.FIELD_USUARIO_GENERADOR).append(EQUALS)
          .append(consulta.getUsuarioFirmante()).append(PARENTHESES_END);
    }
    return buffer;
  }

  private StringBuffer andEqualsCondition(StringBuffer buffer, String key) {
    return andCondition(buffer).append(key).append(EQUALS);
  }

  private StringBuffer andCondition(StringBuffer buffer) {
    if (buffer.length() != 0) {
      buffer.append(AND);
    }
    return buffer;
  }

  private String removeWildcards(String string) {
    return string.replaceAll("[^a-zA-Z0-9-]", "");
  }

  private String convert(Object o) {
    if (o instanceof String) {
      return formatSpace((String) o);
    } else if (conversionService.canConvert(o.getClass(), String.class)) {
      return conversionService.convert(o, String.class);
    } else {
      return String.valueOf(o);
    }
  }

  private String formatSpace(String o) {
    return o.replaceAll(" ", "\\\\ ");
  }
}
