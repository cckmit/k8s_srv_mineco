/**
 *
 */
package com.egoveris.plugins.manager.deployers.strategies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.plugins.manager.Plugin;
import com.egoveris.plugins.manager.dao.IDao;
import com.egoveris.plugins.manager.helpers.EventManagerHelper;
import com.egoveris.plugins.manager.helpers.PluginHelper;
import com.egoveris.plugins.manager.model.PluginInfo;
import com.egoveris.plugins.manager.model.Version;
import com.egoveris.plugins.manager.plugins.annotations.Scriptable;
import com.egoveris.plugins.manager.tools.Checksum;
import com.egoveris.plugins.manager.tools.JarClassLoader;
import com.egoveris.plugins.manager.tools.SnapshotManager;
import com.egoveris.plugins.manager.tools.scripts.ScriptUtils;

/**
 * @author difarias
 *
 */
public class RawDeploy implements IDeployStrategy {
	final static Logger logger = LoggerFactory.getLogger(RawDeploy.class);

	private IDao dao;
	private List<String> lastDeploys;
	private List<Plugin> plugins;
	private List<PluginInfo> lstPluginInfo;
	private String workingDirectory;
	private Map<String, List<Object>> classInstances;

	private Version version = new Version("deploy");
	private Map<Version, JarClassLoader> deploys;
	private final ScriptUtils scriptUtil = ScriptUtils.getInstance();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.plugins.manager.deployers.strategies.IDeployStrategy#
	 * getActiveVersion()
	 */
	@Override
	public Version getActiveVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setActiveVersion(final Version version) {
		this.version = version;
		if (version != null) {
			logger.info("Active Version " + version.getName());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.plugins.manager.deployers.strategies.IDeployStrategy#
	 * getClassInstances()
	 */
	@Override
	public Map<String, List<Object>> getClassInstances() {
		if (classInstances == null) {
			classInstances = new HashMap<>();
		}
		return classInstances;
	}

	/**
	 * @param classInstances
	 *            the classInstances to set
	 */
	public void setClassInstances(Map<String, List<Object>> classInstances) {
		if (classInstances == null) {
			classInstances = new HashMap<>();
		}
		this.classInstances = classInstances;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.plugins.manager.deployers.strategies.IDeployStrategy#
	 * getAllClassInstances()
	 */
	@Override
	@Deprecated
	public List<Object> getAllClassInstances() {
		final List<Object> result = new ArrayList<>();
		for (final String key : getClassInstances().keySet()) {
			result.addAll(getClassInstances().get(key));
		}
		return result;
	}

	/**
	 * @return the workingDirectory
	 */
	public String getWorkingDirectory() {
		return workingDirectory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.plugins.manager.deployers.strategies.IDeployStrategy#
	 * setWorkingDirectory(java.lang.String)
	 */
	@Override
	public void setWorkingDirectory(final String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.plugins.manager.deployers.strategies.IDeployStrategy#
	 * getPlugins()
	 */
	@Override
	public List<Plugin> getPlugins() {
		if (plugins == null) {
			plugins = new ArrayList<>();
		}
		return plugins;
	}

	/**
	 * @param plugins
	 *            the plugins to set
	 */
	public void setPlugins(final List<Plugin> plugins) {
		this.plugins = plugins;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.plugins.manager.deployers.strategies.IDeployStrategy#
	 * getLstPluginInfo()
	 */
	@Override
	public List<PluginInfo> getLstPluginInfo() {
		if (lstPluginInfo == null) {
			lstPluginInfo = new ArrayList<>();
		}
		return lstPluginInfo;
	}

	/**
	 * @param lstPluginInfo
	 *            the lstPluginInfo to set
	 */
	public void setLstPluginInfo(final List<PluginInfo> lstPluginInfo) {
		this.lstPluginInfo = lstPluginInfo;
	}

	/**
	 * @return the dao
	 */
	public IDao getDao() {
		return dao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.egoveris.plugins.manager.deployers.strategies.IDeployStrategy#setDao(
	 * com.egoveris.plugins.manager.dao.IDao)
	 */
	@Override
	public void setDao(final IDao dao) {
		this.dao = dao;
	}

	/**
	 * @return the lastDeploys
	 */
	public List<String> getLastDeploys() {
		if (lastDeploys == null) {
			lastDeploys = new ArrayList<>();
		}
		return lastDeploys;
	}

	/**
	 * @param lastDeploys
	 *            the lastDeploys to set
	 */
	public void setLastDeploys(final List<String> lastDeploys) {
		this.lastDeploys = lastDeploys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.plugins.manager.deployers.strategies.IDeployStrategy#
	 * getActiveClassLoader()
	 */
	@Override
	public JarClassLoader getActiveClassLoader() {
		return getDeploys().get(getActiveVersion());
	}

	/**
	 * @return the deploys
	 */
	protected Map<Version, JarClassLoader> getDeploys() {
		if (deploys == null) {
			deploys = new HashMap<>();
		}
		return deploys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.plugins.manager.deployers.strategies.IDeployStrategy#
	 * getPluginsInfo()
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List<PluginInfo> getPluginsInfo() {
		getLstPluginInfo().clear();
		for (final Plugin plugin : getPlugins()) {
			getLstPluginInfo().add(plugin.getPluginInfo());
		}
		return getLstPluginInfo();
	}

	public boolean existVersion(final String checksum) {
		try {
			for (final Version ver : getDeploys().keySet()) {
				if (ver.getSignal() != null && ver.getSignal().equals(checksum)) {
					return true;
				}
			}
		} catch (final Exception e) {
		  logger.error("existVersion", e);
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.plugins.manager.deployers.strategies.IDeployStrategy#
	 * printVersions()
	 */
	@Override
	public void printVersions() {
		for (final Version ver : getDeploys().keySet()) {
			logger.info(ver.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.plugins.manager.deployers.strategies.IDeployStrategy#
	 * switchVersion(java.lang.String)
	 */
	@Override
	public void switchVersion(final String newVersion) {
		if (!getDeploys().isEmpty()) {
			for (final Version version : getDeploys().keySet()) {
				if (version.getNumber().equals(newVersion)) {
					logger.info("Switching to version: " + version.getName());
					version.getNumber().equals(newVersion);

					logger.info("Contain files: ");
					for (final String filename : version.getFiles()) {
						logger.info(String.format(" # %s %n", filename));
					}
					setActiveVersion(version);
					break;
				}
			}
		}
	}

	private List<String> checkRemoved(final List<String> lastDeployed, final List<String> pluginJar) {
		final List<String> result = new ArrayList<>();
		// --- normalize names ---
		if (pluginJar != null && !pluginJar.isEmpty()) {
			for (String aux : lastDeployed) {
				boolean toRemove = true;
				aux = FilenameUtils.getName(aux);
				for (String newDeploy : pluginJar) {
					newDeploy = FilenameUtils.getName(newDeploy);
					if (aux.equals(newDeploy)) {
						toRemove = false;
					}
				}

				if (toRemove) {
					result.add(aux);
				}
			}
		} else {
			result.addAll(pluginJar);
		}
		return result;
	}

	// #######################################################################################

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.egoveris.plugins.manager.deployers.strategies.IDeployStrategy#deploy(
	 * java.util.Map, java.util.List, boolean)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void deploy(final Map<String, Object> context, final List<String> pluginJar, final boolean isRedeploy) {
		String checksum = "";

		try {
			checksum = String.format("%x", Checksum.entireCRC(pluginJar));
		} catch (final Exception e1) {
		  logger.error("deploy", e1);
		}

		if (!isRedeploy && existVersion(checksum)) { // is has no new files to
														// deploy or if removed
														// then redeploy
			if (checkRemoved(getLastDeploys(), pluginJar).isEmpty()) {
				return;
			}
		}

		setLastDeploys(pluginJar);

		final Version newVersion = SnapshotManager.makeSnapshot(getWorkingDirectory(), getActiveVersion(), pluginJar,
				checksum);
		final String activity = isRedeploy ? "Redeploying" : "Deploying";

		logger.info(String.format("%s version: (%s) ...\n", activity, newVersion.getName()));
		for (final String jarname : pluginJar) {
			System.out.printf(" - %s %n", jarname);
		}

		final JarClassLoader currentClassLoader = getDeploys().get(getActiveVersion());
		JarClassLoader newClassLoader;

		if (currentClassLoader == null) {
			newClassLoader = new JarClassLoader(Thread.currentThread().getContextClassLoader());
		} else {
			newClassLoader = currentClassLoader.clone();
		}

		newClassLoader.includeJars(newVersion.getFiles());

		getDeploys().put(newVersion, newClassLoader);

		getDao().save(getActiveVersion());
		getDao().save(newVersion);

		setActiveVersion(newVersion);

		// ---- aca se modificaria ---
		setPlugins(PluginHelper.generatePlugins(newVersion));
		final Map<String, Map<String, Class>> scriptGroup = new HashMap<>();
		for (Plugin<?> plug : getPlugins()) {
			plug.activate(newClassLoader, true, context);
			final Map<String, Map<String, Class>> obj = plug.findScriptables(newClassLoader);
			if (obj != null) {
				for (final Map.Entry<String, Map<String, Class>> entry : obj.entrySet()) {
					if (scriptGroup.containsKey(entry.getKey())) {
						for (final Map.Entry<String, Class> entryClass : entry.getValue().entrySet()) {
							scriptGroup.get(entry.getKey()).put(entryClass.getKey(), entryClass.getValue());
						}
					} else {
						scriptGroup.put(entry.getKey(), entry.getValue());
					}
				}
			}
		}
		EventManagerHelper.getInstance().notifyAllActivation(getPluginsInfo()); // notify
		activarScriptables(scriptGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.plugins.manager.deployers.strategies.IDeployStrategy#
	 * initialize(java.util.Map)
	 */
	@Override
	public void initialize(final Map<String, Object> context) {
		logger.info("- depoy strategy initialize()");
		getDao().init(getWorkingDirectory());

		final List<Version> versions = getDao().getAllVersions();

		if (versions != null && !versions.isEmpty()) {
			getDeploys().clear();
			for (final Version version : versions) {
				final JarClassLoader newClassLoader = new JarClassLoader(
						Thread.currentThread().getContextClassLoader());
				newClassLoader.includeJars(version.getFiles());
				getDeploys().put(version, newClassLoader);

				if (version.isActive()) {
					setActiveVersion(version);

					setPlugins(PluginHelper.generatePlugins(version));

					final Map<String, Map<String, Class>> scriptGroup = new HashMap<>();
					getLastDeploys().clear();

					// getContext().put(Plugin.KEY_WORKING_DIRECTORY,
					// getWorkingDirectory());

					for (final Plugin plug : getPlugins()) {
						plug.activate(newClassLoader, false, context);
						getLastDeploys().add(plug.getJarFilename());

						final Map<String, Map<String, Class>> obj = plug.findScriptables(newClassLoader);
						if (obj != null) {
							for (final Map.Entry<String, Map<String, Class>> entry : obj.entrySet()) {
								if (scriptGroup.containsKey(entry.getKey())) {
									for (final Map.Entry<String, Class> entryClass : entry.getValue().entrySet()) {
										scriptGroup.get(entry.getKey()).put(entryClass.getKey(), entryClass.getValue());
									}
								} else {
									scriptGroup.put(entry.getKey(), entry.getValue());
								}
							}
						}

						final List<Object> clsRef = plug.getClassInstances();

						if (clsRef != null) {
							getClassInstances().put(plug.getName(), clsRef);
						}
					}

					activarScriptables(scriptGroup);
				}
			}

			EventManagerHelper.getInstance().notifyAllActivation(getPluginsInfo()); // notify
																					// all
																					// activations
		}
	}

	public void activarScriptables(final Map<String, Map<String, Class>> scriptGroup) {
		scriptUtil.setActiveClassLoader(getActiveClassLoader());
		scriptUtil.setScriptGroup(scriptGroup);

		for (final Map<String, Class> classMap : scriptGroup.values()) {
			for (final Class clazz : classMap.values()) {
				final Scriptable script = (Scriptable) clazz.getAnnotation(Scriptable.class);
				if (script != null && script.initJS() != null && script.initJS().length() > 0) {
					String jsContent = null;
					try {
						jsContent = IOUtils.toString(getActiveClassLoader().getResourceAsStream(script.initJS()));
						scriptUtil.contentRegisterJS(jsContent);
					} catch (final ScriptException e) {
					  logger.error("error - activarScriptables", e);
					} catch (final IOException e) {
					  logger.error("error - activarScriptables", e);
					}
				}
			}
		}
	}

}
