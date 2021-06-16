/**
 *
 */
package com.egoveris.plugins.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.Predicate;
import org.slf4j.LoggerFactory;

import com.egoveris.plugins.manager.dao.GenericDao;
import com.egoveris.plugins.manager.deployers.IDeployer;
import com.egoveris.plugins.manager.deployers.strategies.IDeployStrategy;
import com.egoveris.plugins.manager.deployers.strategies.RawDeploy;
import com.egoveris.plugins.manager.helpers.EventManagerHelper;
import com.egoveris.plugins.manager.helpers.PluginHelper;
import com.egoveris.plugins.manager.interfaces.IPluginManager;
import com.egoveris.plugins.manager.model.ExecutableInfo;
import com.egoveris.plugins.manager.model.PluginInfo;
import com.egoveris.plugins.manager.model.Version;
import com.egoveris.plugins.manager.plugins.annotations.Executable;
import com.egoveris.plugins.manager.plugins.exceptions.ExecutableException;
import com.egoveris.plugins.manager.plugins.exceptions.UnauthorizedException;
import com.egoveris.plugins.manager.plugins.interfaces.IAuthorization;
import com.egoveris.plugins.manager.plugins.interfaces.IExecutable;
import com.egoveris.plugins.manager.plugins.interfaces.PMEventListener;
import com.egoveris.plugins.manager.tools.ReflectionUtil;

/**
 * @author difarias
 *
 */
public final class PluginManager implements IDeployHandler, IPluginManager {
	final static org.slf4j.Logger logger = LoggerFactory.getLogger(PluginManager.class);

	private static PluginManager ourInstance = null;
	// private Version version=new Version("deploy");
	// private Map<Version,JarClassLoader> deploys;

	private List<IDeployer> deployers;

	private String workingDirectory;
	private boolean initialized = false;

	// private Map<String, List<Object>> classInstances;

	private GenericDao dao;

	private Map<String, Object> context;

	// private ScriptUtils scriptUtil = ScriptUtils.getInstance();

	// private List<String> lastDeploys;

	private IDeployStrategy deployStrategy;

	public static synchronized PluginManager getInstance() {
		if (ourInstance == null) {
			ourInstance = new PluginManager();
		}
		return ourInstance;
	}

	private PluginManager() {
	}

	/**
	 * @return the dao
	 */
	public GenericDao getDao() {
		if (dao == null) {
			dao = new GenericDao();
		}
		return dao;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.egoveris.plugins.manager.IPluginManager#addEventListener(com.egoveris
	 * .plugins.manager.plugins.interfaces.PMEventListener)
	 */
	@Override
	public void addEventListener(final PMEventListener listener) {
		EventManagerHelper.getInstance().addEventListener(listener);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.plugins.manager.IPluginManager#isInitialized()
	 */
	@Override
	public boolean isInitialized() {
		return initialized;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.plugins.manager.IPluginManager#setInitialized(boolean)
	 */
	@Override
	public void setInitialized(final boolean initialized) {
		this.initialized = initialized;
	}

	/*
	 * @return the lstPluginInfo
	 *
	 * public List<PluginInfo> getLstPluginInfo() { if (lstPluginInfo==null) {
	 * lstPluginInfo = new ArrayList<PluginInfo>(); } return lstPluginInfo; }
	 */

	/**
	 * @return the deployers
	 */
	public List<IDeployer> getDeployers() {
		if (deployers == null) {
			deployers = ReflectionUtil.searchClasses(IDeployer.class);

			if (deployers != null && !deployers.isEmpty()) {
				for (final IDeployer deployer : deployers) {
					deployer.setDeployHandler(this);
				}
			}
		}
		return deployers;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.egoveris.plugins.manager.IPluginManager#setDeployers(java.util.List)
	 */
	@Override
	public void setDeployers(final List<IDeployer> deployers) {
		this.deployers = deployers;
	}

	// /**
	// * @return the deploys
	// */
	// private Map<Version, JarClassLoader> getDeploys() {
	// if (deploys==null) {
	// deploys = new HashMap<Version,JarClassLoader>();
	// }
	// return deploys;
	// }
	//
	// /**
	// * @return the lastDeploys
	// */
	// public List<String> getLastDeploys() {
	// if (lastDeploys==null) {
	// lastDeploys = new ArrayList<String>();
	// }
	// return lastDeploys;
	// }
	//
	// /**
	// * @param lastDeploys the lastDeploys to set
	// */
	// public void setLastDeploys(List<String> lastDeploys) {
	// this.lastDeploys = lastDeploys;
	// }

	// public Version getActiveVersion() {
	// return version;
	// }
	//
	// /**
	// * @param version the version to set
	// */
	// public void setActiveVersion(Version version) {
	// this.version = version;
	// if (version!=null) {
	// logger.info("Active Version "+version.getName());
	// }
	// }

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.plugins.manager.IPluginManager#getActiveVersion()
	 */
	@Override
	public Version getActiveVersion() {
		return getDeployStrategy().getActiveVersion();
	}

	/**
	 * @return the deployStrategy
	 */
	public IDeployStrategy getDeployStrategy() {
		if (deployStrategy == null) {
			setDeployStrategy(new RawDeploy());
		}
		return deployStrategy;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.plugins.manager.IPluginManager#setDeployStrategy(com.
	 * egoveris.plugins.manager.deployers.strategies.IDeployStrategy)
	 */
	@Override
	public void setDeployStrategy(final IDeployStrategy deployStrategy) {
		this.deployStrategy = deployStrategy;

		if (this.deployStrategy != null) {
			this.deployStrategy.setDao(getDao());
			this.deployStrategy.setWorkingDirectory(getWorkingDirectory());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.plugins.manager.IPluginManager#switchVersion(java.lang.
	 * String)
	 */
	@Override
	public void switchVersion(final String newVersion) {
		getDeployStrategy().switchVersion(newVersion);
	}

	// public void switchVersion(String newVersion) {
	// if (!getDeploys().isEmpty()) {
	// for (Version version: getDeploys().keySet()) {
	// if (version.getNumber().equals(newVersion)) {
	// logger.info("Switching to version: "+version.getName());
	// version.getNumber().equals(newVersion);
	//
	// logger.info("Contain files: ");
	// for (String filename: version.getFiles()) {
	// logger.info(String.format(" # %s \n",filename));
	// }
	// setActiveVersion(version);
	// break;
	// }
	// }
	// }
	// }

	// public JarClassLoader getActiveClassLoader() {
	// return getDeploys().get(getActiveVersion());
	// }
	//
	// public boolean existVersion(String checksum) {
	// try {
	// for (Version ver : getDeploys().keySet()) {
	// if (ver.getSignal()!=null && ver.getSignal().equals(checksum)) return
	// true;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return false;
	// }
	//
	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.plugins.manager.IPluginManager#printVersions()
	 */
	@Override
	public void printVersions() {
		getDeployStrategy().printVersions();
	}

	// private List<String> checkRemoved(List<String> lastDeployed, List<String>
	// pluginJar) {
	// List<String> result = new ArrayList<String>();
	// // --- normalize names ---
	// if (pluginJar!=null && !pluginJar.isEmpty()) {
	// for(String aux: lastDeployed) {
	// boolean toRemove=true;
	// aux = FilenameUtils.getName(aux);
	// for(String newDeploy: pluginJar) {
	// newDeploy = FilenameUtils.getName(newDeploy);
	// if (aux.equals(newDeploy)) toRemove=false;
	// }
	//
	// if (toRemove) result.add(aux);
	// }
	// } else {
	// result.addAll(pluginJar);
	// }
	//
	// return result;
	// }

	/**
	 * Method to deploy a JAR in the context
	 *
	 * @param pluginJar
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void deploy(final List<String> pluginJar, final boolean isRedeploy) {
		getContext().put(Plugin.KEY_WORKING_DIRECTORY, getWorkingDirectory());
		getDeployStrategy().deploy(getContext(), pluginJar, isRedeploy);
		/*
		 *
		 * String checksum = "";
		 *
		 * try { checksum = String.format("%x",Checksum.entireCRC(pluginJar)); }
		 * catch (Exception e1) { }
		 *
		 * if (!isRedeploy && existVersion(checksum)) { // is has no new files
		 * to deploy or if removed then redeploy if
		 * (checkRemoved(getLastDeploys(),pluginJar).isEmpty()) return; }
		 *
		 * setLastDeploys(pluginJar);
		 *
		 * Version newVersion =
		 * SnapshotManager.makeSnapshot(getWorkingDirectory(),
		 * getActiveVersion(), pluginJar, checksum); String activity =
		 * (isRedeploy?"Redeploying":"Deploying");
		 *
		 * logger.info(String.format("%s version: (%s) ...\n"
		 * ,activity,newVersion.getName())); for (String jarname: pluginJar) {
		 * System.out.printf(" - %s \n",jarname); }
		 *
		 *
		 * JarClassLoader currentClassLoader =
		 * getDeploys().get(getActiveVersion()); JarClassLoader newClassLoader =
		 * null;
		 *
		 * if (currentClassLoader==null) { newClassLoader = new
		 * JarClassLoader(Thread.currentThread().getContextClassLoader()); }
		 * else { newClassLoader = currentClassLoader.clone(); }
		 *
		 * newClassLoader.includeJars(newVersion.getFiles());
		 *
		 * getDeploys().put(newVersion, newClassLoader);
		 *
		 * getDao().save(getActiveVersion()); getDao().save(newVersion);
		 *
		 * setActiveVersion(newVersion);
		 *
		 * // ---- aca se modificaria ---
		 * setPlugins(PluginHelper.generatePlugins(newVersion));
		 *
		 * Map<String, Map<String, Class>> scriptGroup = new HashMap<String,
		 * Map<String,Class>>();
		 *
		 * getContext().put(Plugin.KEY_WORKING_DIRECTORY,
		 * getWorkingDirectory());
		 *
		 * for (Plugin plug : getPlugins()) { plug.activate(newClassLoader,
		 * true, getContext());
		 *
		 * Map<String, Map<String, Class>> obj =
		 * plug.findScriptables(newClassLoader); if (obj!=null) {
		 * for(Map.Entry<String, Map<String,Class>> entry: obj.entrySet()) { if
		 * (scriptGroup.containsKey(entry.getKey())) { for (Map.Entry<String,
		 * Class> entryClass: entry.getValue().entrySet()) {
		 * scriptGroup.get(entry.getKey()).put(entryClass.getKey(),entryClass.
		 * getValue()); } } else {
		 * scriptGroup.put(entry.getKey(),entry.getValue()); } } } }
		 *
		 * EventManagerHelper.getInstance().notifyAllActivation(getPluginsInfo()
		 * ); // notify all activations activarScriptables(scriptGroup);
		 */
	}

	/**
	 * @return the context
	 */
	public Map<String, Object> getContext() {
		if (context == null) {
			context = new HashMap<>();
		}
		return context;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(final Map<String, Object> context) {
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.egoveris.plugins.manager.IDeployCallback#deploy(java.lang.String)
	 */
	@Override
	public void deploy(final String... pluginJar) {
		deploy(Arrays.asList(pluginJar), false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.plugins.manager.IDeployHandler#deploy(java.util.List)
	 */
	@Override
	public void deploy(final List<String> pluginJar) {
		deploy(pluginJar, false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.plugins.manager.IDeployHandler#redeploy(java.util.List)
	 */
	@Override
	public void redeploy(final List<String> pluginJar) {
		deploy(pluginJar, true);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.egoveris.plugins.manager.IDeployCallback#redeploy(java.lang.String)
	 */
	@Override
	public void redeploy(final String... pluginJar) {
		deploy(Arrays.asList(pluginJar), true);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.plugins.manager.IPluginManager#searchClasses(java.lang.
	 * String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <V> List<V> searchClasses(final String packageName) {
		return (List<V>) ReflectionUtil.searchClasses(packageName, getDeployStrategy().getActiveClassLoader());
	}

	/**
	 * @return the plugins
	 */
	@SuppressWarnings("rawtypes")
	protected List<Plugin> getPlugins() {
		return getDeployStrategy().getPlugins();
	}

	/**
	 * @param plugins
	 *            the plugins to set
	 */
	public void setPlugins(@SuppressWarnings("rawtypes") final List<Plugin> plugins) {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.plugins.manager.IPluginManager#getExecsInfo()
	 */
	@Override
	@SuppressWarnings({ "rawtypes" })
	public List<ExecutableInfo> getExecsInfo() {
		final List<ExecutableInfo> executables = new ArrayList<>();
		List<ExecutableInfo> lst;

		for (final Plugin pg : getPlugins()) {
			try {
				lst = PluginHelper.findExecutableInfo(pg.getJarFile(), getDeployStrategy().getActiveClassLoader());
				if (lst != null && !lst.isEmpty()) {
					executables.addAll(lst);
				}
			} catch (final Exception e) {
				logger.error("error in getExecsInfo", e);
			}
		}

		return executables;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.egoveris.plugins.manager.IPluginManager#execute(java.lang.String,
	 * java.util.Map,
	 * com.egoveris.plugins.manager.plugins.interfaces.IAuthorization)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object execute(final String executableName, final Map<String, Object> context,
			final IAuthorization autorization) throws ExecutableException, UnauthorizedException {
		final List<Class<?>> executables = new ArrayList<>();
		for (final Plugin pg : getPlugins()) {
			final Map<String, Class<?>> executablesMap = pg.getExecutables(getDeployStrategy().getActiveClassLoader());
			if (executablesMap != null && !executablesMap.isEmpty()) {
				final Class<?> exec = executablesMap.get(executableName);
				if (exec != null) {
					executables.add(exec);
				}
			}
		}

		if (!executables.isEmpty()) {
			if (executables.size() > 1) {
				throw new ExecutableException(
						String.format("Too many executables with the same name [%s].", executableName));
			}
			IExecutable executable;
			try {
				executable = (IExecutable) executables.get(0).newInstance();
				final Executable execAnnotation = executable.getClass().getAnnotation(Executable.class);

				if (autorization != null) {
					final ExecutableInfo execInfo = new ExecutableInfo(execAnnotation.name(), execAnnotation.icon(),
							execAnnotation.tooltip(), execAnnotation.isVisible());
					if (!autorization.isAutorized(execInfo)) {
						throw new UnauthorizedException(execInfo, "Unauthorized to execute.");
					}
				}

				if (executable != null) {
					return executable.execute(context);
				}
			} catch (final ExecutableException e) {
			  logger.error("error ExecutableException", e);
				throw new ExecutableException(e.getMessage());
			} catch (final InstantiationException e) {
			  logger.error("error InstantiationException", e);
				throw new ExecutableException(e.getMessage());
			} catch (final IllegalAccessException e) {
			  logger.error("error IllegalAccessException", e);
				throw new ExecutableException(e.getMessage());
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.egoveris.plugins.manager.IPluginManager#execute(java.lang.String,
	 * com.egoveris.plugins.manager.plugins.interfaces.IAuthorization)
	 */
	@Override
	public Object execute(final String executableName, final IAuthorization autorization)
			throws ExecutableException, UnauthorizedException {
		return execute(executableName, null, autorization);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.egoveris.plugins.manager.IPluginManager#execute(java.lang.String)
	 */
	@Override
	public Object execute(final String executableName) throws ExecutableException, UnauthorizedException {
		return execute(executableName, null, null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		if (!isInitialized()) { // si no esta inicializado entonces lo
								// inicializo
			getContext().put(Plugin.KEY_WORKING_DIRECTORY, getWorkingDirectory());
			getDeployStrategy().initialize(getContext());
		}

		/*
		 * if (!isInitialized()) { // si no esta inicializado entonces lo
		 * inicializo logger.info("PluginManager.initialize()");
		 * getDao().init(getWorkingDirectory());
		 *
		 * List<Version> versions = getDao().getAllVersions();
		 *
		 * if (versions!=null && !versions.isEmpty()) { getDeploys().clear();
		 * for (Version version : versions) { JarClassLoader newClassLoader =
		 * new JarClassLoader(Thread.currentThread().getContextClassLoader());
		 * newClassLoader.includeJars(version.getFiles());
		 * getDeploys().put(version, newClassLoader);
		 *
		 * if (version.isActive()) { setActiveVersion(version);
		 *
		 * setPlugins(PluginHelper.generatePlugins(version));
		 *
		 * Map<String, Map<String, Class>> scriptGroup = new HashMap<String,
		 * Map<String,Class>>(); getLastDeploys().clear();
		 *
		 * getContext().put(Plugin.KEY_WORKING_DIRECTORY,
		 * getWorkingDirectory());
		 *
		 * for (Plugin plug : getPlugins()) { plug.activate(newClassLoader,
		 * false, getContext()); getLastDeploys().add(plug.getJarFilename());
		 *
		 * Map<String, Map<String, Class>> obj =
		 * plug.findScriptables(newClassLoader); if (obj!=null) {
		 * for(Map.Entry<String, Map<String,Class>> entry: obj.entrySet()) { if
		 * (scriptGroup.containsKey(entry.getKey())) { for (Map.Entry<String,
		 * Class> entryClass: entry.getValue().entrySet()) {
		 * scriptGroup.get(entry.getKey()).put(entryClass.getKey(),entryClass.
		 * getValue()); } } else {
		 * scriptGroup.put(entry.getKey(),entry.getValue()); } } }
		 *
		 * List<Object> clsRef = plug.getClassInstances();
		 *
		 * if (clsRef!=null) { getClassInstances().put(plug.getName(), clsRef);
		 * } }
		 *
		 * EventManagerHelper.getInstance().notifyAllActivation(getPluginsInfo()
		 * ); // notify all activations
		 *
		 * activarScriptables(scriptGroup); } } } }
		 */
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.egoveris.plugins.manager.IPluginManager#startDeployers(java.lang.
	 * String)
	 */
	@Override
	public void startDeployers(final String pathToCheck) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.plugins.manager.IPluginManager#startDeployers()
	 */
	@Override
	public void startDeployers() {
		initialize();
		for (final IDeployer deployer : getDeployers()) {
			deployer.setDeployHandler(this);
			deployer.start();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.plugins.manager.IPluginManager#pauseDeployers()
	 */
	@Override
	public void pauseDeployers() {
		for (final IDeployer deployer : getDeployers()) {
			deployer.pause();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.plugins.manager.IPluginManager#stopDeployers()
	 */
	@Override
	public void stopDeployers() {
		for (final IDeployer deployer : getDeployers()) {
			deployer.stop();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.plugins.manager.IPluginManager#getWorkingDirectory()
	 */
	@Override
	public String getWorkingDirectory() {
		return workingDirectory;
	}

	/**
	 * @param workingDirectory
	 *            the workingDirectory to set
	 */
	public void setWorkingDirectory(final String workingDirectory) {
		getDeployStrategy().setWorkingDirectory(workingDirectory);
		this.workingDirectory = workingDirectory;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.egoveris.plugins.manager.IPluginManager#getInstancesOf(org.apache.
	 * commons.collections4.Predicate,
	 * org.apache.commons.collections4.Predicate)
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Object> getInstancesOf(final Predicate<Class<?>> classFilter, final Predicate<Object> objectFilter) {
		final List<Class<?>> lst = new ArrayList<>();
		final List<Object> result = new ArrayList<>();

		for (final Plugin plugin : getPlugins()) {
			lst.addAll(plugin.findClasses(null, getDeployStrategy().getActiveClassLoader(), classFilter));

			for (final Object obj : plugin.getClassInstances()) { // check
																	// autoinstantiated
																	// objects,
																	// if exist
				if (objectFilter != null && objectFilter.evaluate(obj)) { // and
																			// it's
																			// evaluated
					result.add(obj);
				}

				if (objectFilter == null) { // if don't exist objectFilter
					result.add(obj);
				}
			}
		}

    for (final Class<?> clazz : lst) {
      Object obj = ReflectionUtil.createInstance(clazz);
      if (objectFilter != null) { // if exist objectFilter
        if (objectFilter.evaluate(obj)) { // and it's evaluated
          result.add(obj);
        } 
      } else { // if don't exist objectFilter
        result.add(obj);
      }
    }

    return result;
  }

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.plugins.manager.IPluginManager#getPluginsInfo()
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List<PluginInfo> getPluginsInfo() {
		// getLstPluginInfo().clear();
		// for (Plugin plugin: getPlugins()) {
		// getLstPluginInfo().add(plugin.getPluginInfo());
		// }
		// return getLstPluginInfo();
		return getDeployStrategy().getPluginsInfo();
	}

}
