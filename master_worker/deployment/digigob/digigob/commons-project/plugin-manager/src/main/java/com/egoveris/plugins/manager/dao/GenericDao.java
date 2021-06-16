/**
 * 
 */
package com.egoveris.plugins.manager.dao;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.plugins.manager.model.Version;

/**
 * @author difarias
 *
 */
public class GenericDao implements IDao{
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private final String dbName="PluginManager.odb";
	private static final Logger logger = LoggerFactory.getLogger(GenericDao.class);
	/* (non-Javadoc)
	 * @see com.egoveris.plugins.manager.dao.IDao#init(java.lang.String)
	 */
	@Override
	public void init(String directory) {
		URL dir = null;
		
		try {
			dir = new URL(directory);
		} catch (Exception e) {
		  logger.error("error al acceder al directorio", e);
			try {
				dir = new URL((new File(System.getProperty("java.io.tmpdir"))).toURI().toURL().toString()); // File.createTempDirectory("").toUri().toURL();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
			  logger.error("error al acceder al directorio", e1);
				e1.printStackTrace();
			}
		}
		
		if(dir != null){
		    String directoryStr=dir.getFile();
		emf = Persistence.createEntityManagerFactory(directoryStr+File.separator+dbName);
		}
		if (emf!=null) {
			setEm(emf.createEntityManager());
		}
	}
	
	/**
	 * @return the em
	 */
	public EntityManager getEm() {
		return em;
	}

	/**
	 * @param em the em to set
	 */
	public void setEm(EntityManager em) {
		this.em = em;
	}

	/* (non-Javadoc)
	 * @see com.egoveris.plugins.manager.dao.IDao#save(T)
	 */
	@Override
	public <T> void save(T obj) {
		getEm().getTransaction().begin();
		getEm().persist(obj);
		getEm().getTransaction().commit();
	}
	
	/* (non-Javadoc)
	 * @see com.egoveris.plugins.manager.dao.IDao#getAllVersions()
	 */
	@Override
	public List<Version> getAllVersions() {
		Query query = getEm().createQuery("select v from Version v");
		List result = null;
		
		try {
			result = query.getResultList();
			return (List<Version>) result;
		} catch (Exception e) {
			logger.error("error al ejecutar query", e);
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.egoveris.plugins.manager.dao.IDao#getActiveSnapshot()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Version getActiveSnapshot() {
		//TypedQuery<Version> query = 
		Query query = getEm().createQuery("select v from Version v where v.active=true");
		Version currentVersion = null;
		
		try {
			currentVersion = (Version) query.getSingleResult();
		} catch (Exception e) {
    logger.error("error al ejecutar query singleResult", e);
		}
		
		return currentVersion;
	}
}
