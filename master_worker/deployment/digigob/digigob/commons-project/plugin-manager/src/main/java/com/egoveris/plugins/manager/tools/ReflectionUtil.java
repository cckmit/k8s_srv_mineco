/**
 *
 */
package com.egoveris.plugins.manager.tools;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author difarias
 *
 */
public final class ReflectionUtil {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);
	public static ClassLoader activeClassLoader;

	/**
	 * @return the activeClassLoader
	 */
	public static ClassLoader getActiveClassLoader() {
		if (activeClassLoader == null) {
			while (activeClassLoader == null) {
				activeClassLoader = Thread.currentThread().getContextClassLoader();
			}
		}
		return activeClassLoader;
	}

	/**
	 * @param activeClassLoader
	 *            the activeClassLoader to set
	 */
	public static void setActiveClassLoader(final ClassLoader activeClassLoader) {
		ReflectionUtil.activeClassLoader = activeClassLoader;
	}

	@SuppressWarnings("unchecked")
	public static <V> List<V> searchClasses(final Class<V> rootCls, final ClassLoader loader) {
		final List<V> destination = new ArrayList<>();

		final Package pkg = rootCls.getPackage();
		String packageName = pkg.getName();
		final String rootPath = rootCls.getProtectionDomain().getCodeSource().getLocation() + "/"
				+ packageName.replace(".", "/");
		final ArrayList<String> names = new ArrayList<>();

		// ------------------------------------
		JarFile jf = null;
		try {
			URL packageURL;

			packageName = packageName.replace(".", "/");
			packageURL = loader.getResource(packageName);

			if (packageURL.getProtocol().equals("jar")) {
				String jarFileName;
				
				Enumeration<JarEntry> jarEntries;
				String entryName;

				// build jar file name, then loop through zipped entries
				jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
				jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));
				// System.out.println(">"+jarFileName);
				jf = new JarFile(jarFileName);
				jarEntries = jf.entries();
				while (jarEntries.hasMoreElements()) {
					entryName = jarEntries.nextElement().getName();
					if (entryName.startsWith(packageName) && entryName.length() > packageName.length() + 5) {
						// entryName =
						// entryName.substring(packageName.length()+1,entryName.lastIndexOf('.'));
						entryName = entryName.substring(packageName.length() + 1);
						// entryName = entryName.replace("/", ".");
						names.add(entryName);
					}
				}
				

				// loop through files in classpath
			} else {
				final URI uri = new URI(packageURL.toString());
				final File folder = new File(uri.getPath());
				// won't work with path which contains blank (%20)
				// File folder = new File(packageURL.getFile());
				final File[] contenuti = folder.listFiles();
				String entryName;
				if (contenuti != null) {
					for (final File actual : contenuti) {
						entryName = actual.getName();
						entryName = entryName.substring(0, entryName.lastIndexOf('.'));
						names.add(entryName);
					}
				}
			}

		} catch (final Exception e) {
			logger.error("oops something is wrong here", e);
		}finally {
    try {
      jf.close();
    } catch (Exception e2) {
      logger.error("error al cerrar el archivo", e2);
    }
  }
		// ------------------------------------

		URL root = null;

		try {
			root = new URL(rootPath);

			// Filter .class files.
			final File[] files = new File(root.getFile()).listFiles(new FilenameFilter() {
				@Override
				public boolean accept(final File dir, final String name) {
					return name.endsWith(".class");
				}
			});

			if (names.isEmpty()) {
				for (final File file : files) {
					file.getName().replaceAll(".class$", "");
				}
			}

			// Find classes implementing ICommand.
			boolean isAbstract, isInterface, isEnum, isAnonymousClass,
					hasConstructors, isTest, isPrivate, isMemberClass;

			for (final String fileName : names) {
				packageName = packageName.replaceAll("/", ".");
				final String className = fileName.replaceAll("/", ".").replaceAll(".class$", "");
				Class<?> cls;
				try {
					// cls = Class.forName(packageName + "." + className);
					final String classToLoad = packageName + "." + className;
					cls = loader.loadClass(classToLoad); // Class.forName(packageName
															// + "." +
															// className);
					if (cls == null) {
						continue;
					}

					isAnonymousClass = cls.isAnonymousClass();
					isAbstract = Modifier.isAbstract(cls.getModifiers());
					isInterface = Modifier.isInterface(cls.getModifiers());
					isEnum = cls.isEnum();
					isPrivate = Modifier.isPrivate(cls.getModifiers());
					isMemberClass = cls.isMemberClass();

					isTest = fileName.toLowerCase().contains("test");
					hasConstructors = cls.getConstructors().length > 0;

					if (!isEnum && !isAbstract && !isInterface && !isAnonymousClass && !isPrivate && hasConstructors
							&& !isTest && !isMemberClass) {
						final V instance = (V) cls.newInstance();
						destination.add(instance);
					}
				} catch (final ClassNotFoundException e) {
					logger.error("oops something is wrong here", e);
				} catch (final InstantiationException e) {
					logger.error("oops something is wrong here", e);
				} catch (final IllegalAccessException e) {
					logger.error("oops something is wrong here", e);
				}
			}
		} catch (final MalformedURLException e1) {
			e1.printStackTrace();
		}

		return destination;
	}

	public static <V> List<V> searchClasses(final Class<V> rootCls) {
		return searchClasses(rootCls, getActiveClassLoader());
	}

	public static <V> List<V> searchClasses(String packageName, final ClassLoader loader) {
		final List<V> destination = new ArrayList<>();

		String rootPath = ReflectionUtil.class.getProtectionDomain().getCodeSource().getLocation()
				+ packageName.replace(".", "/");

		final ArrayList<String> names = new ArrayList<>();

		// ------------------------------------
  JarFile jf = null;
		try {
			URL packageURL;

			packageName = packageName.replace(".", "/");
			packageURL = loader.getResource(packageName);

			if (packageURL != null) {

				if (packageURL.getProtocol().equals("jar")) {
					String jarFileName;
			
					Enumeration<JarEntry> jarEntries;
					String entryName;

					// build jar file name, then loop through zipped entries
					jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
					jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));
					// System.out.println(">"+jarFileName);
					jf = new JarFile(jarFileName);
					jarEntries = jf.entries();
					while (jarEntries.hasMoreElements()) {
						entryName = jarEntries.nextElement().getName();
						if (entryName.startsWith(packageName + "/") && entryName.length() > packageName.length() + 5) {
							// entryName =
							// entryName.substring(packageName.length()+1,entryName.lastIndexOf('.'));
							entryName = entryName.substring(packageName.length() + 1);
							// entryName = entryName.replace("/", ".");
							names.add(entryName);
						}
					}
					jf.close();
					rootPath = packageURL.getFile();
					// loop through files in classpath
				} else {
					// URI uri = new URI(packageURL.toString());
					final URI[] uris = new URI[] { new URI(packageURL.toString()), new URI(rootPath) };

					for (final URI uri : uris) {
						// if (names.isEmpty())
						{
							final File folder = new File(uri.getPath());
							// won't work with path which contains blank (%20)
							// File folder = new File(packageURL.getFile());
							final File[] contenuti = folder.listFiles();
							String entryName;
							if (contenuti != null) {
								for (final File actual : contenuti) {
									entryName = actual.getName();
									entryName = entryName.substring(0, entryName.lastIndexOf('.'));
									names.add(entryName);
								}
							}
						}
					}
				}
			}
		} catch (final Exception e) {
			logger.error("oops something is wrong here", e);
		}finally {
    try {
      jf.close();
    } catch (Exception e2) {
      logger.error("error al cerrar el archivo", e2);
    }
  }
		// ------------------------------------

		try {
			new URL(rootPath);

			// Find classes implementing ICommand.
			boolean isAbstract, isInterface, isEnum, isPrivate, hasConstructors,
					isTest, isMemberClass;

			for (final String fileName : names) {
				packageName = packageName.replaceAll("/", ".");
				final String className = fileName.replaceAll("/", "").replaceAll(".class$", "");
				Class<?> cls;
				try {
					final String classToLoad = packageName + "." + className;

					cls = loader.loadClass(classToLoad);
					if (cls == null) {
						continue;
					}

					isAbstract = Modifier.isAbstract(cls.getModifiers());
					isInterface = Modifier.isInterface(cls.getModifiers());
					isPrivate = Modifier.isPrivate(cls.getModifiers());
					isEnum = cls.isEnum();
					isMemberClass = cls.isMemberClass();

					isTest = fileName.toLowerCase().contains("test");
					hasConstructors = cls.getConstructors().length > 0;

					if (!isEnum && !isAbstract && !isInterface && !isPrivate && hasConstructors && !isTest
							&& !isMemberClass) {
						final V instance = (V) cls.newInstance();
						destination.add(instance);
					}
				} catch (final ClassNotFoundException e) {
					logger.error("oops something is wrong here", e);
				} catch (final InstantiationException e) {
					logger.error("oops something is wrong here", e);
				} catch (final IllegalAccessException e) {
					logger.error("oops something is wrong here", e);
				}
			}
		} catch (final MalformedURLException e1) {
			logger.error("oops something is wrong here", e1);
		}

		return destination;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <V> V createInstance(final Class cls) {
		try {
			boolean isAbstract, isInterface , isEnum, isPrivate, hasConstructors,
					isTest, isMemberClass;

			if (cls == null) {
				return null;
			}

			isAbstract = Modifier.isAbstract(cls.getModifiers());
			isInterface = Modifier.isInterface(cls.getModifiers());
			isPrivate = Modifier.isPrivate(cls.getModifiers());
			isEnum = cls.isEnum();
			isMemberClass = cls.isMemberClass();

			isTest = cls.getSimpleName().toLowerCase().endsWith("test");
			hasConstructors = cls.getConstructors().length > 0;

			if (!isEnum && !isAbstract && !isInterface && !isPrivate && hasConstructors && !isTest && !isMemberClass) {
				final V instance = (V) cls.newInstance();
				return instance;
			}
		} catch (final Exception e) {
			logger.error("oops something is wrong here", e);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <V> List<V> searchClasses(final String packageName) {
		return (List<V>) searchClasses(packageName, getActiveClassLoader());
	}

	public static void invokeVoid(final Object obj, final String methodName) {
		try {
			final Method method = obj.getClass().getDeclaredMethod(methodName, new Class[] {});
			if (method != null) {
				method.invoke(obj, new Object[] {});
			}
		} catch (final NoSuchMethodException e) {
			logger.error("oops something is wrong here", e);
		} catch (final SecurityException e) {
			logger.error("oops something is wrong here", e);
		} catch (final IllegalAccessException e) {
			logger.error("oops something is wrong here", e);
		} catch (final IllegalArgumentException e) {
			logger.error("oops something is wrong here", e);
		} catch (final InvocationTargetException e) {
			logger.error("oops something is wrong here", e);
		}
	}

	public static void invoke(final Object obj, final String methodName, final Object... params) {
		try {
			final Method[] metodos = obj.getClass().getMethods();
			for (final Method method : metodos) {
				if (methodName.equalsIgnoreCase(method.getName())) {
					try {
						method.invoke(obj, params);
					} catch (final Exception e) {
						logger.error("oops something is wrong here", e);
					}
				}
			}
		} catch (final SecurityException e) {
			logger.error("oops something is wrong here", e);
		} catch (final IllegalArgumentException e) {
			logger.error("oops something is wrong here", e);
		}
	}

	public static boolean hasInterface(final Class<?> clazz, final Class<?> interfaceToCheck) {
		List<Class<?>> interfaces = Arrays.asList(clazz.getInterfaces());
		boolean containInterface = interfaces.contains(interfaceToCheck);

		if (!containInterface) {// me fijo de las superclases
			Class<?> parent = clazz.getSuperclass();
			while (parent != null) {
				interfaces = Arrays.asList(parent.getInterfaces());
				containInterface = interfaces.contains(interfaceToCheck);
				if (containInterface) {
					break;
				}
				parent = parent.getSuperclass();
			}
		}
		return containInterface;
	}
}
