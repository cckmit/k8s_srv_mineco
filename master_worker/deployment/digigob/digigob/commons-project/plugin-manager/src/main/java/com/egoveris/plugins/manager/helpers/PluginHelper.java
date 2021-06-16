/**
 *
 */
package com.egoveris.plugins.manager.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

import com.egoveris.plugins.manager.Plugin;
import com.egoveris.plugins.manager.model.ExecutableInfo;
import com.egoveris.plugins.manager.model.PluginInfo;
import com.egoveris.plugins.manager.model.Version;
import com.egoveris.plugins.manager.plugins.annotations.Executable;
import com.egoveris.plugins.manager.plugins.annotations.Scriptable;
import com.egoveris.plugins.manager.plugins.interfaces.IExecutable;
import com.egoveris.plugins.manager.tools.JarClassLoader;
import com.egoveris.plugins.manager.tools.ReflectionUtil;

/**
 * @author difarias
 *
 */
public class PluginHelper {
	public static final String WEB_TYPE = "WEB";
	public static final String PLUGIN_TYPE = "Plugin-Type";
	public static final String PLUGIN_OWNER = "Plugin-Owner";
	public static final String PLUGIN_NAME = "Plugin-Name";
	public static final String PLUGIN_VERSION = "Plugin-Version";
	public static final String PLUGIN_DESCRIPTION = "Plugin-Description";
	public static final String ACTIVATION_CLASS = "Activation-Class";

	public static final String DEPENDENCIES_DIRECTORY = "pm_dependencies";

	final static org.slf4j.Logger logger = LoggerFactory.getLogger(PluginHelper.class);

	public static JarFile openJar(final File jarFile) {
		try {
			return new JarFile(jarFile);
		} catch (final IOException e) {
    logger.error("error openJar", e);
		}
		return null;
	}

	public static void closeJar(final JarFile jarFile) {
		if (jarFile != null) {
			try {
				jarFile.close();
			} catch (final IOException e) {
	    logger.error("error closeJar", e);
			}
		}
	}

	public static Manifest getManifest(final File jarFile) throws IOException {
		final JarFile file = new JarFile(jarFile);
	 Manifest manifest = null;
		try {
		 manifest = file.getManifest();
  } catch (Exception e) {
     logger.error("error al cerra el archivo jar", e);
  }finally {
    file.close();
  }
	
		return manifest;
	}

	@SuppressWarnings("rawtypes")
	public static List<Plugin> generatePlugins(final Version version) {
		final List<Plugin> plugins = new ArrayList<>();

		for (final String jarFile : version.getFiles()) {
			if (isPlugin(jarFile)) {
				plugins.add(new Plugin(jarFile));
			}
		}

		return plugins;
	}

	public static boolean isPlugin(final String jarFile) {
		JarFile jf = null;
		try {
			jf = new JarFile(new File(jarFile));
				final Manifest manifest = jf.getManifest();
				final Attributes attr = manifest.getMainAttributes();
				final String pluginType = attr.getValue(PLUGIN_TYPE);
				jf.close();
				return pluginType != null && !pluginType.isEmpty();
			
		} catch (final Exception e) {
		  logger.error("error isPlugin", e);
		} finally {
			closeJar(jf);
		}

		return false;
	}

	private static String getWebPath(final JarClassLoader activeClassLoader) {
		final URL url = activeClassLoader.getClass().getResource("/");
		String directoryToDeploy = null;

		if (url.getFile().contains("/WEB-INF")) {
			directoryToDeploy = url.getFile().substring(0, url.getFile().indexOf("/WEB-INF"));
		}

		return directoryToDeploy;
	}

	public static List<Class<?>> findClasses(final File jarFile, final String ignoreClass, final ClassLoader loader,
			final Predicate<Class<?>> filter) {
		final List<Class<?>> lst = new ArrayList<>();
		// --- busco a travez del jar ---
		JarFile jf = null;
		try {
			jf = openJar(jarFile);
			if(jf != null){
			final Enumeration<JarEntry> entries = jf.entries();
			
			while (entries.hasMoreElements()) {
				final JarEntry jarEntry = entries.nextElement();
				final String name = jarEntry.getName().replace("/", ".");
			
				if (ignoreClass != null && !ignoreClass.isEmpty() && name.matches(ignoreClass)) {
					continue;
				}

				if (name.endsWith(".class") && !name.contains("$")) {
					final Class<?> clazz = loader.loadClass(name);
				
					if (clazz != null) {
						if (filter != null && filter.evaluate(clazz)) { // Filtered
																		// by
																		// Predicated
							lst.add(clazz);
						}
						if (filter == null) { // if not filtered, don't remove
							lst.add(clazz);
						}
					}
				}
				}
			}
		} catch (final Exception e) {
		  logger.error("error findClasses", e);
		} finally {
			closeJar(jf);
		}

		return lst;
	}

	public static boolean isWebPlugin(final File jarFile) throws IOException {
		final Manifest manifest = getManifest(jarFile);
		final String pluginType = manifest.getMainAttributes().getValue(PLUGIN_TYPE);
		return pluginType != null && !pluginType.isEmpty() ? pluginType.equalsIgnoreCase(WEB_TYPE) : false;
	}

	public static void syncResources(final File jarFile, final JarClassLoader activeClassLoader) throws IOException {
		String directoryToDeploy = getWebPath(activeClassLoader);

		if (directoryToDeploy == null) {
			logger.debug("No directory for resources was found.");
		}

		directoryToDeploy = URLDecoder.decode(directoryToDeploy, "UTF-8");

		if (isWebPlugin(jarFile) && directoryToDeploy != null && !directoryToDeploy.isEmpty()) {
			logger.debug(String.format("Deploying a web component in %s...", directoryToDeploy));

			final JarFile jf = openJar(jarFile);

			if (jf != null) {
				final Enumeration<JarEntry> entries = jf.entries();
				while (entries.hasMoreElements()) {
					final JarEntry jarEntry = entries.nextElement();

					if (jarEntry.getName().contains("META-INF")) {
						continue;
					}

					if (jarEntry.isDirectory()) {
						final String dirFinal = directoryToDeploy + "/" + jarEntry.getName();
						FileUtils.forceMkdir(new File(dirFinal));
					}

					if (!jarEntry.isDirectory() && !jarEntry.getName().endsWith("class")) {
						logger.debug("jarEntry --> " + jarEntry.getName());
						logger.debug("directoryToDeploy --> " + directoryToDeploy);
						activeClassLoader.findResourceAndExtract(jarEntry.getName(), directoryToDeploy);
					}
				}
			}
			closeJar(jf);
		}
	}

	/**
	 * Method to copy all JARs dependencies into a common classpath
	 * 
	 * @param jarFile
	 *            File
	 * @param activeClassLoader
	 *            ClassLoader (@see JarClassLoader)
	 * @param workingDirectory
	 *            String destiny directory
	 */
	public static void copyJarDependencies(final File jarFile, final JarClassLoader activeClassLoader,
			final String workingDirectory) {
		if (workingDirectory != null && !workingDirectory.isEmpty()) {
			final JarFile jf = openJar(jarFile);

			if (jf != null) {
				URL urlWorkingDir = null;
				String dirFinal = "";

				try {
					urlWorkingDir = new URL(workingDirectory + "/" + DEPENDENCIES_DIRECTORY);
				} catch (final MalformedURLException e1) {
					e1.printStackTrace();
				}

				if (urlWorkingDir != null) {
					dirFinal = urlWorkingDir.getPath();
				}

				try {
					FileUtils.forceMkdir(new File(dirFinal));
				} catch (final IOException ioe) { 
				  logger.error("error copyJarDependencies", ioe);
				}

				final Enumeration<JarEntry> entries = jf.entries();
				while (entries.hasMoreElements()) {
					final JarEntry jarEntry = entries.nextElement();

					if (!jarEntry.isDirectory() && jarEntry.getName().endsWith("jar")) {
						final String jarFilename = FilenameUtils.getName(jarEntry.getName());
						final String jarFileDestiny = dirFinal + File.separator + jarFilename;
						FileOutputStream jaos = null;

						try {
							jaos = new FileOutputStream(new File(jarFileDestiny));
							logger.debug("jar Dependency --> " + jarFileDestiny);
							IOUtils.copy(jf.getInputStream(jarEntry), jaos);

							jaos.flush();
							jaos.close();

							activeClassLoader.includeJarDependencies(jarFileDestiny); // include
																						// jar
																						// file
																						// generated
																						// into
																						// dependencies
																						// classpath
						} catch (final FileNotFoundException e) {
				    logger.error("error copyJarDependencies() - FileNotFoundException", e);
						} catch (final IOException e) {
						  logger.error("error copyJarDependencies() - FileNotFoundException", e);
						}finally {
						  closeFile(jaos);
      }
					}
				}
			}
			closeJar(jf);

		}
	}

	@SuppressWarnings("rawtypes")
	private static List<Object> getInstances(final File jarFile, final JarClassLoader loader) {
		final List<Object> lst = new ArrayList<>();

		// --- busco a travez del jar ---
		JarFile jf = null;
		try {
			jf = openJar(jarFile);
			if (jf != null) {
				final Enumeration<JarEntry> entries = jf.entries();
				while (entries.hasMoreElements()) {
					final JarEntry jarEntry = entries.nextElement();
					final String name = jarEntry.getName().replace("/", ".");
					if (name.endsWith(".class") && !name.contains("$")) {
						logger.debug("searching for : " + name);
						final Class clase = loader.loadClass(name);
						if (clase != null) {
							final Object obj = ReflectionUtil.createInstance(clase);
							if (obj != null) {
								lst.add(obj);
							}
						}
					}
				}
			}
		} catch (final Exception e) {
		  logger.error("error getInstances", e);
		} finally {
			closeJar(jf);
		}

		return lst;
	}
	
	private static void closeFile(FileOutputStream jaos){
	  try {
	    jaos.close();
  } catch (Exception e) {
    logger.error("error al cerrar un archivo", e);
  }
	  
	}

	/**
	 * @return the executables
	 */
	public static Map<String, Class<?>> findExecutables(final File jarFile, final ClassLoader loader) {
		Map<String, Class<?>> result = null;
		final List<Class<?>> foundClasses = findClasses(jarFile, null, loader, new Predicate<Class<?>>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.apache.commons.collections4.Predicate#evaluate(java.lang.
			 * Object)
			 */
			@Override
			public boolean evaluate(final Class<?> clazz) {
				return clazz.getAnnotation(Executable.class) != null;
			}
		});

		if (foundClasses != null && !foundClasses.isEmpty()) {
			result = new HashMap<>();
			for (final Class<?> clazz : foundClasses) {
				final Executable exec = clazz.getAnnotation(Executable.class);
				if (exec != null && ReflectionUtil.hasInterface(clazz, IExecutable.class)) {
					result.put(exec.name(), clazz);
				}
			}
		}

		return result;
	}

	/**
	 * Method to find scriptables clases defined in the plugin
	 * 
	 * @param loader
	 * @param groupName
	 * @return
	 */
	public static Map<String, Map<String, Class>> findScriptables(final File jarFile, final ClassLoader loader) {
		final Map<String, Map<String, Class>> scriptGroup = new HashMap<>();
		Map<String, Class> scriptables;

		final List<Class<?>> foundClasses = findClasses(jarFile, null, loader, new Predicate<Class<?>>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.apache.commons.collections4.Predicate#evaluate(java.lang.
			 * Object)
			 */
			@Override
			public boolean evaluate(final Class<?> clazz) {
				return clazz.getAnnotation(Scriptable.class) != null;
			}
		});

		if (foundClasses != null && !foundClasses.isEmpty()) {
			for (final Class<?> clazz : foundClasses) {
				final Scriptable script = clazz.getAnnotation(Scriptable.class);
				if (script != null) {
					final String reference = script.refName().equals("none") ? clazz.getSimpleName() : script.refName();
					if (scriptGroup.containsKey(script.groupName())) { // Agrego
																		// grupo
																		// y
																		// datos
						scriptables = scriptGroup.get(script.groupName());
						// if (scriptables==null) scriptables = new
						// HashMap<String, Class>();
						scriptables.put(reference, clazz);
						logger.debug(
								String.format("Obtaining ref: (%s) %s - %s", script.groupName(), reference, clazz));
					} else { // si existe el grupo le agrego los datos de la
								// clase
						scriptables = new HashMap<>();
						scriptables.put(reference, clazz);
						logger.debug(
								String.format("Obtaining ref: (%s) %s - %s", script.groupName(), reference, clazz));
						scriptGroup.put(script.groupName(), scriptables);
					}
				}
			}
		}

		return scriptGroup;
	}

	public static PluginInfo createPluginInfo(final File jarFile, final ClassLoader loader) throws IOException {
		final PluginInfo pluginInfo = new PluginInfo();

		final Manifest manifest = getManifest(jarFile);
		final Attributes attr = manifest.getMainAttributes();

		pluginInfo.setJarFilename(jarFile.getName());
		pluginInfo.setActivationClass(attr.getValue(ACTIVATION_CLASS));
		pluginInfo.setType(attr.getValue(PLUGIN_TYPE));
		pluginInfo.setVersion(attr.getValue(PLUGIN_VERSION));
		pluginInfo.setName(attr.getValue(PLUGIN_NAME));
		pluginInfo.setAuthor(attr.getValue(PLUGIN_OWNER));
		pluginInfo.setDescription(attr.getValue(PLUGIN_DESCRIPTION));

		for (final Object key : attr.keySet()) {
			final String keyName = ((Attributes.Name) key).toString();
			final String value = (String) attr.get(key);

			pluginInfo.getDataManifest().put(keyName, value);
		}

		/*
		 * for (Object key: attr.keySet()) {
		 * pluginInfo.getDataManifest().put((String) key, (String)
		 * attr.get(key)); }
		 */

		return pluginInfo;
	}

	@SuppressWarnings("unchecked")
	public static List<ExecutableInfo> findExecutableInfo(final File jarFile, final ClassLoader loader)
			throws IOException {
		final List<ExecutableInfo> executables = new ArrayList<>();
		final Map<String, Class<?>> executablesMap = PluginHelper.findExecutables(jarFile, loader);

		if (executablesMap != null && !executablesMap.isEmpty()) {
			final Iterator<?> entries = executablesMap.entrySet().iterator();
			while (entries.hasNext()) {
				final Entry<String, Class<?>> thisEntry = (Entry<String, Class<?>>) entries.next();
				final Class<?> exec = thisEntry.getValue();
				if (exec != null) {
					final Executable annonData = exec.getAnnotation(Executable.class);
					executables.add(new ExecutableInfo(annonData.name(), annonData.icon(), annonData.tooltip(),
							annonData.isVisible()));
				}
			}
		}
		return executables;
	}
}
