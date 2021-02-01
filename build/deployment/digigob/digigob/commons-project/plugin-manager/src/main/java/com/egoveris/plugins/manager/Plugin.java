/**
 *
 */
package com.egoveris.plugins.manager;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.Manifest;

import org.apache.commons.collections4.Predicate;
import org.slf4j.LoggerFactory;

import com.egoveris.plugins.manager.helpers.EventManagerHelper;
import com.egoveris.plugins.manager.helpers.PluginHelper;
import com.egoveris.plugins.manager.model.FileMetadata;
import com.egoveris.plugins.manager.model.PluginInfo;
import com.egoveris.plugins.manager.plugins.annotations.Activatable;
import com.egoveris.plugins.manager.plugins.annotations.OnActivate;
import com.egoveris.plugins.manager.plugins.annotations.OnDeploy;
import com.egoveris.plugins.manager.tools.JarClassLoader;
import com.egoveris.plugins.manager.tools.ReflectionUtil;

/**
 * @author difarias
 * @param <T>
 *
 */
public class Plugin<T> {
	final static org.slf4j.Logger logger = LoggerFactory.getLogger(Plugin.class);
	public final static String KEY_WORKING_DIRECTORY = "WorkingDirectory";

	private String jarFilename;
	private File jarFile;
	private String name;
	private Manifest manifest;
	private List<Object> classInstances;
	private Map<String, Class<?>> executables;
	private Map<String, Map<String, Class>> scriptGroup;
	private PluginInfo pluginInfo;
	private FileMetadata metadata;

	public Plugin(final String jarFile) {
		this.jarFilename = jarFile;
		try {
			setMetadata(new FileMetadata(getJarFile()));
		} catch (final IOException e) {
			  logger.error("error FileMetadata", e);
		}
	}

	/**
	 * @return the jarFile
	 */
	public String getJarFilename() {
		return jarFilename;
	}

	/**
	 * @param jarFile
	 *            the jarFile to set
	 */
	public void setJarFilename(final String jarFile) {
		this.jarFilename = jarFile;
	}

	/**
	 * @return the jarFile
	 */
	public File getJarFile() {
		if (jarFile == null) {
			jarFile = new File(getJarFilename());
		}
		return jarFile;
	}

	/**
	 * @param jarFile
	 *            the jarFile to set
	 */
	public void setJarFile(final File jarFile) {
		this.jarFile = jarFile;
	}

	/**
	 * @return the metadata
	 */
	public FileMetadata getMetadata() {
		return metadata;
	}

	/**
	 * @param metadata
	 *            the metadata to set
	 */
	public void setMetadata(final FileMetadata metadata) {
		this.metadata = metadata;
	}

	/**
	 * @return the classInstances
	 */
	public List<Object> getClassInstances() {
		if (classInstances == null) {
			classInstances = new ArrayList<Object>();
		}
		return classInstances;
	}

	/**
	 * @param classInstances
	 *            the classInstances to set
	 */
	public void setClassInstances(final List<Object> classInstances) {
		this.classInstances = classInstances;
	}

	/**
	 * @return the manifest
	 */
	public Manifest getManifest() {
		if (manifest == null) {
			try {
				manifest = PluginHelper.getManifest(getJarFile());
			} catch (final Exception e) {
			    logger.error("error PluginHelper ", e);
			}
		}
		return manifest;
	}

	/**
	 * @param manifest
	 *            the manifest to set
	 */
	public void setManifest(final Manifest manifest) {
		this.manifest = manifest;
	}

	public String findAttribute(final String attrName) {
		return getManifest().getMainAttributes().getValue(attrName);
	}

	/**
	 * Method to find classes, filtered by AnnotationClass
	 *
	 * @param ignoreClass
	 *            RegEx used to ignore class or classes
	 * @param annotationFilterClass
	 *            Annotation to Filter the list of classes
	 * @param loader
	 *            ClassLoader
	 * @return List<Class>
	 */
	public List<Class<?>> findClasses(final String ignoreClass, final ClassLoader loader,
			final Predicate<Class<?>> filter) {
		List<Class<?>> lst = new ArrayList<Class<?>>();

		try {
			lst = PluginHelper.findClasses(getJarFile(), ignoreClass, loader, filter);
		} catch (final Exception e) {
			logger.error("Error findind classes" + filter.toString() + "in " + getJarFile().getAbsolutePath(), e);
		}

		return lst;
	}

	/**
	 * Wrapped method
	 *
	 * @see Plugin#findClasses(String, ClassLoader, Predicate)
	 * @return List<Class<?>>
	 */
	public List<Class<?>> findClasses(final String ignoreClass, final ClassLoader loader) {
		return findClasses(ignoreClass, loader, null);
	}

	/**
	 * @return the executables
	 */
	public Map<String, Class<?>> getExecutables(final ClassLoader loader) {
		if (executables == null) {
			executables = PluginHelper.findExecutables(getJarFile(), loader);
		}
		return executables;
	}

	/**
	 * Method to find scriptables clases defined in the plugin
	 *
	 * @param loader
	 * @param groupName
	 * @return
	 */
	public Map<String, Map<String, Class>> findScriptables(final ClassLoader loader) {
		if (scriptGroup == null) {
			scriptGroup = PluginHelper.findScriptables(getJarFile(), loader);
		}
		return scriptGroup;
	}

	/**
	 * Method to find class methods, filtered by annotationClass
	 *
	 * @param clazz
	 *            Class to explore
	 * @param annotationClass
	 *            Annotation Class
	 * @return List<Method>
	 */
	public List<Method> findMethodsByAnnotation(final Class<?> clazz,
			final Class<? extends Annotation> annotationClass) {
		final List<Method> lstMethods = new ArrayList<Method>();
		final List<Method> lstResult = new LinkedList<Method>();
		boolean isOkToUse;
		for (final Method method : clazz.getDeclaredMethods()) {
			final int methodModifiers = method.getModifiers();
			isOkToUse = Modifier.isPublic(methodModifiers) && !Modifier.isStatic(methodModifiers);
			if (method.getAnnotation(annotationClass) != null && isOkToUse) {
				lstMethods.add(method);
			}
		}

		if (lstMethods != null && !lstMethods.isEmpty()) {
			// sorting methods by order in OnDeploy
			if (annotationClass.getName().equalsIgnoreCase(OnDeploy.class.getName())) {
				Collections.sort(lstMethods, new Comparator<Method>() {
					/*
					 * (non-Javadoc)
					 *
					 * @see java.util.Comparator#compare(java.lang.Object,
					 * java.lang.Object)
					 */
					@Override
					public int compare(final Method o1, final Method o2) {
						final OnDeploy an1 = o1.getAnnotation(OnDeploy.class);
						final OnDeploy an2 = o2.getAnnotation(OnDeploy.class);
						return an1.order() == an2.order() ? 0 : an1.order() > an2.order() ? 1 : -1;
					}
				});
			}

			// sorting methods by order in OnActivate
			if (annotationClass.getName().equalsIgnoreCase(OnActivate.class.getName())) {
				Collections.sort(lstMethods, new Comparator<Method>() {
					/*
					 * (non-Javadoc)
					 *
					 * @see java.util.Comparator#compare(java.lang.Object,
					 * java.lang.Object)
					 */
					@Override
					public int compare(final Method o1, final Method o2) {
						final OnActivate an1 = o1.getAnnotation(OnActivate.class);
						final OnActivate an2 = o2.getAnnotation(OnActivate.class);
						return an1.order() == an2.order() ? 0 : an1.order() > an2.order() ? 1 : -1;
					}
				});
			}

			for (final Method method : lstMethods) {
				lstResult.add(method);
			}
		}

		return lstResult;
	}

	/**
	 *
	 * @param activeClassLoader
	 * @param isDeploy
	 * @param context
	 */
	public void activate(final JarClassLoader activeClassLoader, final boolean isDeploy,
			final Map<String, Object> context) {
		try {

			// new - Copy all jar dependies
			PluginHelper.copyJarDependencies(getJarFile(), activeClassLoader,
					(String) context.get(KEY_WORKING_DIRECTORY));

			String activationClass = findAttribute(PluginHelper.ACTIVATION_CLASS);

			final LinkedList<Class<?>> activationsClasses = new LinkedList<Class<?>>();

			if (activationClass != null && !activationClass.isEmpty()) {
				activationClass = activationClass.substring(0, activationClass.lastIndexOf("."));

				try {
					final Class<?> cls = activeClassLoader.findClass(activationClass);
					if (cls != null) {
						activationsClasses.add(cls);
					}
				} catch (final NoClassDefFoundError er) {
					logger.error(String.format("Cannot activate plugin: %s ", getJarFilename()), er);
				}
			}

			// get list of class
			final List<Class<?>> foundClasses = findClasses(activationClass, activeClassLoader,
					new Predicate<Class<?>>() {
						/*
						 * (non-Javadoc)
						 *
						 * @see
						 * org.apache.commons.collections4.Predicate#evaluate(
						 * java.lang.Object)
						 */
						@Override
						public boolean evaluate(final Class<?> clazz) {
							return clazz.getAnnotation(Activatable.class) != null;
						}
					});

			if (foundClasses != null && !foundClasses.isEmpty()) {
				Collections.sort(foundClasses, new Comparator<Class<?>>() {
					/*
					 * (non-Javadoc)
					 *
					 * @see java.util.Comparator#compare(java.lang.Object,
					 * java.lang.Object)
					 */
					@Override
					public int compare(final Class<?> o1, final Class<?> o2) {
						final Activatable an1 = o1.getAnnotation(Activatable.class);
						final Activatable an2 = o2.getAnnotation(Activatable.class);
						return an1.order() == an2.order() ? 0 : an1.order() > an2.order() ? 1 : -1;
					}
				});

				for (final Class<?> cls : foundClasses) {
					activationsClasses.add(cls);
				}
			}

			Object activationClassInstance;

			for (final Class<?> cls : activationsClasses) {
				if (cls != null) {

					activationClassInstance = cls.newInstance();

					if (isDeploy) {
						ReflectionUtil.invoke(activationClassInstance, "onDeploy", context);

						for (final Method method : findMethodsByAnnotation(cls, OnDeploy.class)) {
							ReflectionUtil.invoke(activationClassInstance, method.getName(), context);
						}

					}

					ReflectionUtil.invoke(activationClassInstance, "onActivate", getClassInstances());

					for (final Method method : findMethodsByAnnotation(cls, OnActivate.class)) {
						ReflectionUtil.invoke(activationClassInstance, method.getName(), getClassInstances());
					}
				}
			}

			PluginHelper.syncResources(getJarFile(), activeClassLoader);
			// setScriptGroup(calculateScriptables(activeClassLoader));
			setPluginInfo(PluginHelper.createPluginInfo(getJarFile(), activeClassLoader));

			if (isDeploy) {
				EventManagerHelper.getInstance().notifyDeploy(getPluginInfo()); // notify
																				// deploy
			} else {
				EventManagerHelper.getInstance().notifyActivation(getPluginInfo()); // notify
																					// activation
			}
		} catch (final Exception e) {
			logger.error("ooops, something hapened activating class loader", e);
		}
	}

	public String getName() {
		if (name == null) {
			name = findAttribute(PluginHelper.PLUGIN_NAME);
		}
		return name;
	}

	/**
	 * @return the pluginInfo
	 */
	public PluginInfo getPluginInfo() {
		return pluginInfo;
	}

	/**
	 * @param pluginInfo
	 *            the pluginInfo to set
	 */
	private void setPluginInfo(final PluginInfo pluginInfo) {
		this.pluginInfo = pluginInfo;
	}
}
