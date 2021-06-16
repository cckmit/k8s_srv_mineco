package com.egoveris.deo.base.util;

import com.egoveris.deo.base.model.Documento;
import com.egoveris.deo.base.model.TipoDocumento;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class DocumentoQueryFactory {

	private SessionFactory sessionFactory;

	public DocumentoQuery getInstance() {
		DocumentoQuery object = new DocumentoQueryDAOHbn(sessionFactory);
		return object;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private class DocumentoQueryDAOHbn extends HibernateDaoSupport implements DocumentoQuery {

		private String query;
		private String queryCount;
		private String queryWhere;
		private String queryJoin;
		private String queryOrder;

		private String reparticionParam;
		private String usuarioParam;
		private String usuarioFirmanteParam;
		private TipoDocumento tipoDocumentoParam;
		private String numeroSadeParam;
		private String numeroSadePapelParam;
		private String numeroEspecialParam;
		private Date desdeParam;
		private Date hastaParam;
		private int paginaActual;
		private int cantidadResultados;

		private int contMetadatos = 0;
		private List<DocumentoMetadataDTO> listMetadatos = new ArrayList<>();

		private static final String INICIO_QUERY = "from Documento documento";

		private Collection<String> clausulas = new Vector<String>();
		private Collection<String> parametros = new Vector<String>();
		private Map condiciones = null;

		DocumentoQueryDAOHbn(SessionFactory sessionFactory) {
			query = INICIO_QUERY;
			super.setSessionFactory(sessionFactory);
		}

		public DocumentoQuery reparticion(String reparticion) {
			if (reparticion != null) {
				if (this.reparticionParam == null) {
					if (queryWhere == null) {
						queryWhere = " documento.reparticion=:reparticion";
					} else {
						queryWhere = queryWhere + " and documento.reparticion=:reparticion";
					}
				}
				this.reparticionParam = reparticion;
			}
			return this;
		}

		public DocumentoQuery usuario(String usuario) {
			if (usuario != null) {
				if (this.usuarioParam == null) {
					if (queryWhere == null) {
						queryWhere = " documento.usuarioGenerador=:usuario";
					} else {
						queryWhere = queryWhere + " and documento.usuarioGenerador=:usuario";
					}
				}
				this.usuarioParam = usuario;
			}
			return this;
		}

		public DocumentoQuery desdeHasta(Date desde, Date hasta) {
			if (desde != null && hasta != null) {
				if (queryWhere == null) {
					queryWhere = " documento.fechaCreacion between :desde and :hasta";
				} else {
					queryWhere = queryWhere + " and documento.fechaCreacion between :desde and :hasta";
				}
				this.desdeParam = desde;
				this.hastaParam = hasta;
			}
			return this;
		}

		public DocumentoQuery criterioOrden(String orden, String criterio) {
			if (orden.equals("descending")) {
				orden = "desc";
			} else {
				orden = "asc";
			}
			queryOrder = " order by documento." + criterio + " " + orden;
			return this;
		}

		public DocumentoQuery usuarioFirmante(String usuarioFirmante) {
			if (usuarioFirmante != null) {
				if (this.usuarioFirmanteParam == null) {
					if (queryWhere == null) {
						queryWhere = " (documento.workflowOrigen in (select firmantes.workflowId from Firmante firmantes where firmantes.usuarioFirmante =:usuarioFirmante) "
								+ "or documento.usuarioGenerador=:usuarioFirmante)";
					} else {
						queryWhere = queryWhere
								+ " and (documento.workflowOrigen in (select firmantes.workflowId from Firmante firmantes where firmantes.usuarioFirmante =:usuarioFirmante) "
								+ "or documento.usuarioGenerador=:usuarioFirmante)";
					}
				}
				this.usuarioFirmanteParam = usuarioFirmante;
			}
			return this;
		}

		public DocumentoQuery documentoMetaData(DocumentoMetadataDTO documentoMetaData) {
			contMetadatos++;
			if (documentoMetaData != null) {
				if (queryJoin == null) {
					queryJoin = " join documento.listaMetadatos metaDato" + contMetadatos;
				} else {
					queryJoin = queryJoin + " join documento.listaMetadatos metaDato" + contMetadatos;
				}
				if (queryWhere == null) {
					queryWhere = "(metaDato" + contMetadatos + ".nombre=:nombre" + contMetadatos + " and metaDato"
							+ contMetadatos + ".valor like :valor" + contMetadatos + ")";
				} else {
					queryWhere = queryWhere + " and (metaDato" + contMetadatos + ".nombre=:nombre" + contMetadatos
							+ " and metaDato" + contMetadatos + ".valor like :valor" + contMetadatos + ")";
				}
				this.listMetadatos.add(documentoMetaData);
			}
			return this;
		}

		public DocumentoQuery tipoDocumento(TipoDocumento tipoDocumento) {
			if (tipoDocumento != null) {
				if (tipoDocumentoParam == null) {
					if (queryWhere == null) {
						queryWhere = " documento.tipo=:tipoDocumento";
					} else {
						queryWhere = queryWhere + " and documento.tipo=:tipoDocumento";
					}
				}
				this.tipoDocumentoParam = tipoDocumento;
			}
			return this;
		}

		public DocumentoQuery numeroSade(String numeroSade) {
			if (numeroSade != null) {
				if (this.numeroSadeParam == null) {
					if (queryWhere == null) {
						queryWhere = " documento.numero=:numeroSade";
					} else {
						queryWhere = queryWhere + " and documento.numero=:numeroSade";
					}
				}
				this.numeroSadeParam = numeroSade;
			}
			return this;
		}

		public DocumentoQuery numeroEspecial(String numeroEspecial) {
			if (numeroEspecial != null) {
				if (this.numeroEspecialParam == null) {
					if (queryWhere == null) {
						queryWhere = " documento.numeroEspecial=:numeroEspecial";
					} else {
						queryWhere = queryWhere + " and documento.numeroEspecial=:numeroEspecial";
					}
				}
				this.numeroEspecialParam = numeroEspecial;
			}
			return this;
		}

		public DocumentoQuery numeroSadePapel(String numeroSadePapel) {
			if (numeroSadePapel != null) {
				if (this.numeroSadePapelParam == null) {
					if (queryWhere == null) {
						queryWhere = " documento.numeroSadePapel=:numeroSadePapel";
					} else {
						queryWhere = queryWhere + " and documento.numeroSadePapel=:numeroSadePapel";
					}
				}
				this.numeroSadePapelParam = numeroSadePapel;
			}
			return this;
		}

		public List<Documento> listar() {
			query = INICIO_QUERY;
			if (queryJoin != null) {
				query = query + queryJoin;
			}
			if (queryWhere != null) {
				query = query + " where" + queryWhere;
			}
			if (queryOrder != null) {
				query = query + queryOrder;
			}
			return this.buscarDocumento(query);
		}

		private List<Documento> buscarDocumento(String query) {

			Session session = null;
			try {
				this.getHibernateTemplate().setCacheQueries(true);
				List<Documento> result = null;
				session = sessionFactory.getCurrentSession();
				Query hqlQuery = session.createQuery(query);
				validarHqlQuery(hqlQuery);
				if (listMetadatos != null) {
					int i = 1;
					for (DocumentoMetadataDTO metaDatos : listMetadatos) {
						hqlQuery.setString("nombre" + i, metaDatos.getNombre());
						hqlQuery.setString("valor" + i, metaDatos.getValor());
						i++;
					}
				}
				if (this.numeroSadeParam != null) {
					hqlQuery.setString("numeroSade", numeroSadeParam);
				}
				if (this.numeroEspecialParam != null) {
					hqlQuery.setString("numeroEspecial", numeroEspecialParam);
				}
				if (this.numeroSadePapelParam != null) {
					hqlQuery.setString("numeroSadePapel", numeroSadePapelParam);
				}
				if (cantidadResultados != 0) {
					hqlQuery.setFirstResult(paginaActual);
					hqlQuery.setMaxResults(cantidadResultados);
				}
				result = hqlQuery.list();
				return result;
			} catch (Exception e) {
				logger.error(e);
				throw new IllegalAccessError("Ha ocurrido un error en la busqueda del Documento. " + e);
			} finally {
				if (session != null) {
					session.clear();
				}
			}
		}

		public long contar() {
			queryCount = "select count(*) " + INICIO_QUERY;
			if (queryJoin != null) {
				queryCount = queryCount + queryJoin;
			}
			if (queryWhere != null) {
				queryCount = queryCount + " where" + queryWhere;
			}
			Session session = null;
			try {
				this.getHibernateTemplate().setCacheQueries(true);
				session = this.getSessionFactory().getCurrentSession();
				Query hqlQuery = session.createQuery(queryCount);
				validarHqlQuery(hqlQuery);
				if (this.numeroSadePapelParam != null) {
					hqlQuery.setString("numeroSadePapel", this.numeroSadePapelParam);
				}
				if (listMetadatos != null) {
					int i = 1;
					for (DocumentoMetadataDTO metaDatos : listMetadatos) {
						hqlQuery.setString("nombre" + i, metaDatos.getNombre());
						hqlQuery.setString("valor" + i, metaDatos.getValor());
						i++;
					}
				}
				return ((Long) hqlQuery.iterate().next()).intValue();
			} catch (Exception e) {
				logger.error(e);
				throw new IllegalAccessError("Ha ocurrido un error en la busqueda del Documento. " + e);
			} finally {
				if (session != null) {
					session.close();
				}
			}

		}

		private void validarHqlQuery(Query hqlQuery) {

			if (this.reparticionParam != null) {
				hqlQuery.setString("reparticion", reparticionParam);
			}
			if (this.usuarioParam != null) {
				hqlQuery.setString("usuario", usuarioParam);
			}
			if (this.desdeParam != null) {
				hqlQuery.setDate("desde", desdeParam);
			}
			if (this.hastaParam != null) {
				hqlQuery.setDate("hasta", hastaParam);
			}
			if (this.usuarioFirmanteParam != null) {
				hqlQuery.setString("usuarioFirmante", usuarioFirmanteParam);
			}
			if (this.tipoDocumentoParam != null) {
				hqlQuery.setInteger("tipoDocumento", tipoDocumentoParam.getId());
			}
		}

		public DocumentoQuery paginar(int paginaActual, int cantidadResultados) {
			this.paginaActual = paginaActual;
			this.cantidadResultados = cantidadResultados;
			return this;
		}

		/**
		 * Arma un criterio de <code>Documento</code>
		 * 
		 * @return <code>List<Documento></code>
		 * @throws IllegalAccessError
		 */
		public List<Documento> getDocumentosPorCriteriaQuery(Map inputMap) throws IllegalAccessError {
			Session session = null;

			this.addCondicionesConParametrosImpl(inputMap);

			try {
				this.getHibernateTemplate().setCacheQueries(true);
				session = this.getSessionFactory().getCurrentSession();
				Criteria criteria = session.createCriteria(Documento.class);

				if (!this.clausulas.isEmpty()) {
					for (String clausula : this.clausulas) {
						if (trimmed(clausula)) {
							criteria.add(Restrictions.eq(getParamNombre(clausula), getParamValor(clausula)));
						}
					}
				}
				return criteria.list();
			} catch (Exception e) {
				logger.error("Error al obtener documentos por criterio " + e.getMessage(), e);
				throw new IllegalAccessError(
						"Hubo un error en la busqueda por criterio de documento oficial, la excepcion es "
								+ e.getMessage());
			} finally {
				this.clean();
				if (session != null) {
					session.close();
				}

			}

		}

		private String getParamNombre(String paramNombre) {
			String nuevoParamNombre = null;
			nuevoParamNombre = paramNombre.substring(paramNombre.indexOf('.') + 1, paramNombre.length());

			return nuevoParamNombre;
		}

		public void addCondicionesConParametrosImpl(Map<String, String> condiciones) {
			this.condiciones = condiciones;
			for (Entry<String, String> nuevaCondicion : condiciones.entrySet()) {
				if (nuevaCondicion.getKey().contains("documento."))
					clausulas.add(nuevaCondicion.getKey());
			}
			this.addParametrosImpl(this.condiciones.entrySet());
		}

		public void addParametrosImpl(Set<Map.Entry<String, String>> values) {
			for (Entry<String, String> nuevoParametro : values) {
				if (nuevoParametro.getKey().contains("documento."))
					parametros.add(nuevoParametro.getValue());
			}
		}

		private boolean trimmed(String trimmed) {
			return (trimmed != null && !"".equalsIgnoreCase(trimmed));
		}

		private Object getParamValor(String keyParamValor) {
			return this.condiciones.get(keyParamValor);
		}

		private void clean() {
			this.clausulas = new Vector<>();
			this.parametros = new Vector<>();
		}

	}
}
