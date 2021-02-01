/**
 *
 */
package com.egoveris.workflow.designer.module.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.shareddocument.base.model.FileAsStreamConnectionWebDav;
import com.egoveris.shareddocument.base.model.WebDAVResourceBean;
import com.egoveris.shareddocument.base.service.IWebDavService;
import com.egoveris.workflow.designer.module.exception.DesginerException;
import com.egoveris.workflow.designer.module.exception.TransformerException;
import com.egoveris.workflow.designer.module.model.Project;
import com.egoveris.workflow.designer.module.model.ProjectDesignerDTO;
import com.egoveris.workflow.designer.module.service.DesignerService;
import com.egoveris.workflow.designer.module.service.ProjectService;
import com.egoveris.workflow.designer.module.service.TransformerService;
import com.egoveris.workflow.designer.module.util.StringUtil;
import com.egoveris.workflow.designer.module.util.TypeWorkFlow;
import com.google.gson.Gson;

@Service
public class DesignerServiceImpl implements DesignerService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(DesignerServiceImpl.class);

	@Autowired
	private IWebDavService webDavService;
	private final String webdavJsonPath = "Workflow_Designer/Workflows";
	@Autowired
	private TransformerService transformerService;
	@Autowired
	private ProjectService projectServiceImpl;

	public String toJson(final Object obj) {
		if (logger.isDebugEnabled()) {
			logger.debug("toJson(Object) - start"); //$NON-NLS-1$
		}

		final Gson gson = new Gson();
		final String json = gson.toJson(obj);

		if (logger.isDebugEnabled()) {
			logger.debug("toJson(Object) - end"); //$NON-NLS-1$
		}
		return json;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V fromJson(final String json, final Class<?> clazz) {
		if (logger.isDebugEnabled()) {
			logger.debug("fromJson(String, Class<?>) - start"); //$NON-NLS-1$
		}
		final Gson gson = new Gson();
		final V obj = (V) gson.fromJson(json, clazz);
		if (logger.isDebugEnabled()) {
			logger.debug("fromJson(String, Class<?>) - end"); //$NON-NLS-1$
		}
		return obj;
	}

	@Override
	public String getStringFromResource(final String resource) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getStringFromResource(String) - start"); //$NON-NLS-1$
		}
		final URL urlFileDesicion = DesignerServiceImpl.class.getResource(resource);
		String wrappedScript = "";
		final URLConnection connection = urlFileDesicion.openConnection();
		wrappedScript = IOUtils.toString(connection.getInputStream());
		if (logger.isDebugEnabled()) {
			logger.debug("getStringFromResource(String) - end"); //$NON-NLS-1$
		}
		return wrappedScript;
	}

	/***
	 *
	 * @param projectJson:
	 *            Json del proyecto que se quiere guardar
	 * @param name:
	 *            Nombre del archivo
	 * @param nameSpace:
	 *            Subdirectorio donde se guardara el archivo
	 * @return Booleano, indicara true si tuvo errores.
	 */
	@Override
	public Boolean saveOnWevDav(final String projectJson, final String name, final String nameSpace) {
		if (logger.isDebugEnabled()) {
			logger.debug("saveOnWevDav(String, String, String) - start"); //$NON-NLS-1$
		}
		final Boolean hasErrors = false;
		final InputStream stream = new ByteArrayInputStream(projectJson.getBytes());
		webDavService.createSpace(null, nameSpace, null, null);
		webDavService.createFile(nameSpace, name, null, null, stream);
		if (logger.isDebugEnabled()) {
			logger.debug("saveOnWevDav(String, String, String) - end"); //$NON-NLS-1$
		}
		return hasErrors;
	}

	/***
	 *
	 * @param project:
	 *            Proyecto que se quiere guardar.
	 * @param name:
	 *            Nombre del archivo.
	 * @param nameSpace:
	 *            Indicara si se guarda en hml o prd.
	 * @return Booleano, indicara true si tuvo errores.
	 * @throws Exception
	 *             Indicara la excepcion que ocurrió.
	 */
	@Override
	public Boolean saveJarOnWevDav(final Project project, final String name, final String nameSpace)
			throws DesginerException {
		if (logger.isDebugEnabled()) {
			logger.debug("saveJarOnWevDav(Project, String, String) - start"); //$NON-NLS-1$
		}

		final Boolean hasErrors = false;
		FileInputStream fis = null;
		try {
			final String jarFile = transformerService.createProjectJar(project);
			fis = new FileInputStream(new File(jarFile));
			final String ahora = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
			final String webdavDestiny = webdavJsonPath + "/toDeploy";
			final String simplename = StringUtil.camelName(name.substring(0, name.indexOf(".")));
			final String filename = String.format("%s_%s_%s_WEB.jar", simplename, ahora, project.getVersion());
			webDavService.createFile(webdavDestiny, filename, null, null, fis);
			fis.close();
		} catch (IOException | TransformerException e) {
			logger.error("saveJarOnWevDav(Project, String, String)", e); //$NON-NLS-1$
			throw new DesginerException(e.getMessage());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("saveJarOnWevDav(Project, String, String) - end"); //$NON-NLS-1$
		}
		return hasErrors;
	}

	/***
	 *
	 * @param nameSpace:
	 *            Subdirectorio de WebDav que se quiera mostrar.
	 * @return Lista de Strings con los nombres de los archivos en el
	 *         subDirectorio.
	 */
	@Override
	public List<String> getListOfWebDav(final String nameSpace) {
		if (logger.isDebugEnabled()) {
			logger.debug("getListOfWebDav(String) - start"); //$NON-NLS-1$
		}

		final List<String> fileNames = new ArrayList<String>();
		;
		try {
			final List<WebDAVResourceBean> listOfWebDav = webDavService.listSpace(nameSpace, null, null);
			for (final WebDAVResourceBean wdR : listOfWebDav) {
				if (!wdR.isDirectorio()) {
					fileNames.add(wdR.getNombre());
				}
			}
		} catch (final Exception e) {
			logger.error("getListOfWebDav(String)", e); //$NON-NLS-1$
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getListOfWebDav(String) - end"); //$NON-NLS-1$
		}
		return fileNames;
	}

	/***
	 *
	 * @param fileName:
	 *            Nombre del archivo que se quiere obtener.
	 * @param nameSpace:
	 *            Subdirectorio donde se encuentra el archivo
	 * @return Proyecto generado a partir del json obtenido.
	 */
	@Override
	public Project getProjectFromWebDav(final String nameSpace, final String fileName, final TypeWorkFlow type) {
		if (logger.isDebugEnabled()) {
			logger.debug("getProjectFromWebDav(String, String, TypeWorkFlow) - start"); //$NON-NLS-1$
		}

		try {
			final FileAsStreamConnectionWebDav listOfWebDav = webDavService.getFileAsStream(nameSpace + fileName, null,
					null);
			final String sb = IOUtils.toString(listOfWebDav.getFileAsStream(), CharEncoding.UTF_8);
			final Project returnProject = fromJson(sb, Project.class);
			if (type.equals(TypeWorkFlow.ALL) || type.equals(returnProject.getTypeWorkFlow())) {
				return returnProject;
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getProjectFromWebDav(String, String, TypeWorkFlow) - end"); //$NON-NLS-1$
			}
			return null;
		} catch (final IOException e) {
			logger.error("getProjectFromWebDav(String, String, TypeWorkFlow)", e); //$NON-NLS-1$
			if (logger.isDebugEnabled()) {
				logger.debug("getProjectFromWebDav(String, String, TypeWorkFlow) - end"); //$NON-NLS-1$
			}
			return null;
		}
	}

	@Override
	public void saveOrUpdateProject(final Project project) {
		projectServiceImpl.saveOrUpdate(project);

	}

	@Override
	public List<ProjectDesignerDTO> getAllProjects(final TypeWorkFlow type) {
		return projectServiceImpl.getAllProjects(type);
	}

	@Override
	public void changeNameProject(final Project project, final String projectNameOld) {
		projectServiceImpl.changeNameProject(project, projectNameOld);
	}

}
