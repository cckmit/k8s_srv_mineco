/**
 *
 */
package com.egoveris.te.base.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
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
public final class ReflectionUtil extends org.springframework.util.ReflectionUtils {

  private static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

  public static ClassLoader activeClassLoader;

  public static Method findMethod(final Class<?> clazz, final String name,
      final boolean parentSearch) {
    if (logger.isDebugEnabled()) {
      logger.debug("findMethod(clazz={}, name={}, parentSearch={}) - start", clazz, name,
          parentSearch);
    }

    Method method = null;

    if (clazz != null) {
      method = findMethod(clazz, name);
      if (parentSearch && method == null) {
        method = findMethod(clazz.getSuperclass(), name, true);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("findMethod(Class<?>, String, boolean) - end - return value={}", method);
    }
    return method;
  }

  public static Method findMethod(final Class<?> clazz, final String name,
      final Class<?>[] paramTypes, final boolean parentSearch) {
    if (logger.isDebugEnabled()) {
      logger.debug("findMethod(clazz={}, name={}, paramTypes={}, parentSearch={}) - start", clazz,
          name, paramTypes, parentSearch);
    }

    Method method = null;

    if (clazz != null) {
      method = findMethod(clazz, name, paramTypes);
      if (parentSearch && method == null) {
        method = findMethod(clazz.getSuperclass(), name, paramTypes);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("findMethod(Class<?>, String, Class<?>[], boolean) - end - return value={}",
          method);
    }
    return method;
  }

  public static Method findMethodByName(final Class clazz, final String methodName) {
    if (logger.isDebugEnabled()) {
      logger.debug("findMethodByName(clazz={}, methodName={}) - start", clazz, methodName);
    }

    for (final Method method : clazz.getMethods()) {
      if (method.getName().equalsIgnoreCase(methodName)) {
        if (logger.isDebugEnabled()) {
          logger.debug("findMethodByName(Class, String) - end - return value={}", method);
        }
        return method;
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("findMethodByName(Class, String) - end - return value={null}");
    }
    return null;
  }

  /**
   * @return the activeClassLoader
   */
  public static ClassLoader getActiveClassLoader() {
    if (logger.isDebugEnabled()) {
      logger.debug("getActiveClassLoader() - start");
    }

    if (activeClassLoader == null) {
      while (activeClassLoader == null) {
        activeClassLoader = Thread.currentThread().getContextClassLoader();
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getActiveClassLoader() - end - return value={}", activeClassLoader);
    }
    return activeClassLoader;
  }

  @SuppressWarnings("unchecked")
  public static boolean hasInterface(final Class<?> clazz, final Class<?> interfaceToCheck) {
    if (logger.isDebugEnabled()) {
      logger.debug("hasInterface(clazz={}, interfaceToCheck={}) - start", clazz, interfaceToCheck);
    }

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

    if (logger.isDebugEnabled()) {
      logger.debug("hasInterface(Class<?>, Class<?>) - end - return value={}", containInterface);
    }
    return containInterface;
  }

  public static <V> List<V> searchClasses(final Class<V> rootCls) {
    if (logger.isDebugEnabled()) {
      logger.debug("searchClasses(rootCls={}) - start", rootCls);
    }

    final List<V> returnList = searchClasses(rootCls, getActiveClassLoader());
    if (logger.isDebugEnabled()) {
      logger.debug("searchClasses(Class<V>) - end - return value={}", returnList);
    }
    return returnList;
  }

  @SuppressWarnings("unchecked")
  public static <V> List<V> searchClasses(final Class<V> rootCls, final ClassLoader loader) {
    if (logger.isDebugEnabled()) {
      logger.debug("searchClasses(rootCls={}, loader={}) - start", rootCls, loader);
    }

    final List<V> destination = new ArrayList<V>();

    final Package pkg = rootCls.getPackage();
    String packageName = pkg.getName();
    final String rootPath = rootCls.getProtectionDomain().getCodeSource().getLocation() + "/"
        + packageName.replace(".", "/");
    final ArrayList<String> names = new ArrayList<String>();
    String jarFileName;
    JarFile jf = null;
    // ------------------------------------
    try {
      URL packageURL;

      packageName = packageName.replace(".", "/");
      packageURL = loader.getResource(packageName);

      if (packageURL.getProtocol().equals("jar")) {
        Enumeration<JarEntry> jarEntries;
        String entryName;

        // build jar file name, then loop through zipped entries
        jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
        jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));
        jf = new JarFile(jarFileName);
        jarEntries = jf.entries();
        while (jarEntries.hasMoreElements()) {
          entryName = jarEntries.nextElement().getName();
          if (entryName.startsWith(packageName) && entryName.length() > packageName.length() + 5) {
            entryName = entryName.substring(packageName.length() + 1);

            names.add(entryName);
          }
        }

        // loop through files in classpath
      } else {
        final URI uri = new URI(packageURL.toString());
        final File folder = new File(uri.getPath());
        // won't work with path which contains blank (%20)
        final File[] contenuti = folder.listFiles();
        String entryName;
        for (final File actual : contenuti) {
          entryName = actual.getName();
          entryName = entryName.substring(0, entryName.lastIndexOf('.'));
          names.add(entryName);
        }
      }

    } catch (final Exception e) {
      logger.error("ReflectionUtil Error", e);

    } finally {
      try {
        if (jf != null) {
          jf.close();
        }
      } catch (final IOException e) {
        logger.error("searchClasses(Class<V>, ClassLoader)", e);
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
          if (logger.isDebugEnabled()) {
            logger.debug("$FilenameFilter.accept(dir={}, name={}) - start", dir, name);
          }

          final boolean returnboolean = name.endsWith(".class");
          if (logger.isDebugEnabled()) {
            logger.debug("$FilenameFilter.accept(File, String) - end - return value={}",
                returnboolean);
          }
          return returnboolean;
        }
      });

      if (names.isEmpty()) {
        for (final File file : files) {
          file.getName().replaceAll(".class$", "");
        }
      }

      // Find classes implementing ICommand.
      boolean isAbstract = false, isInterface = false, isEnum = false, isAnonymousClass = false;

      for (final String fileName : names) {
        packageName = packageName.replaceAll("/", ".");
        final String className = fileName.replaceAll("/", ".").replaceAll(".class$", "");
        Class<?> cls;
        try {
          cls = Class.forName(packageName + "." + className);

          isAnonymousClass = cls.isAnonymousClass();
          isAbstract = Modifier.isAbstract(cls.getModifiers());
          isInterface = Modifier.isInterface(cls.getModifiers());
          isEnum = cls.isEnum();

          if (!isEnum && !isAbstract && !isInterface && !isAnonymousClass
              && !cls.isMemberClass()) {
            final V instance = (V) cls.newInstance();
            destination.add(instance);
          }
        } catch (final ClassNotFoundException e) {
          logger.error(e.getMessage());
        } catch (final InstantiationException e) {
          logger.error(e.getMessage());
        } catch (final IllegalAccessException e) {
          logger.error(e.getMessage());
        }
      }
    } catch (final MalformedURLException e1) {
      logger.error(e1.getMessage());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("searchClasses(Class<V>, ClassLoader) - end - return value={}", destination);
    }
    return destination;
  }

  public static <V> List<V> searchClasses(final String packageName) {
    if (logger.isDebugEnabled()) {
      logger.debug("searchClasses(packageName={}) - start", packageName);
    }

    final List<V> returnList = (List<V>) searchClasses(packageName, getActiveClassLoader());
    if (logger.isDebugEnabled()) {
      logger.debug("searchClasses(String) - end - return value={}", returnList);
    }
    return returnList;
  }

  public static <V> List<V> searchClasses(String packageName, final ClassLoader loader) {
    if (logger.isDebugEnabled()) {
      logger.debug("searchClasses(packageName={}, loader={}) - start", packageName, loader);
    }

    final List<V> destination = new ArrayList<V>();

    String rootPath = ReflectionUtil.class.getProtectionDomain().getCodeSource().getLocation()
        + packageName.replace(".", "/");

    final ArrayList<String> names = new ArrayList<String>();
    String jarFileName;
    JarFile jf = null;
    // ------------------------------------
    try {
      URL packageURL;

      packageName = packageName.replace(".", "/");
      packageURL = loader.getResource(packageName);

      if (packageURL != null && packageURL.getProtocol().equals("jar")) {

        Enumeration<JarEntry> jarEntries;
        String entryName;

        // build jar file name, then loop through zipped entries
        jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
        jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));
        jf = new JarFile(jarFileName);
        jarEntries = jf.entries();
        while (jarEntries.hasMoreElements()) {
          entryName = jarEntries.nextElement().getName();
          if (entryName.startsWith(packageName) && entryName.length() > packageName.length() + 5) {
            entryName = entryName.substring(packageName.length() + 1);
            names.add(entryName);
          }
        }
        rootPath = packageURL.getFile();
        // loop through files in classpath
      } else {
        final URI[] uris = new URI[] { new URI(packageURL.toString()), new URI(rootPath) };

        for (final URI uri : uris) {
          {
            final File folder = new File(uri.getPath());
            // won't work with path which contains blank (%20)
            final File[] contenuti = folder.listFiles();
            String entryName;
            for (final File actual : contenuti) {
              entryName = actual.getName();
              entryName = entryName.substring(0, entryName.lastIndexOf('.'));
              names.add(entryName);
            }
          }
        }
      }
    } catch (final Exception e) {
      logger.error("ReflectionUtil Error", e);

    } finally {
      try {
        if (jf != null) {
          jf.close();
        }
      } catch (final IOException e) {
        logger.error("searchClasses(Class<V>, ClassLoader)", e);
      }
    }
    // ------------------------------------

    try {
      new URL(rootPath);

      // Find classes implementing ICommand.
      boolean isAbstract = false, isInterface = false, isEnum = false, isPrivate = false,
          hasConstructors = false, isTest = false;

      for (final String fileName : names) {
        packageName = packageName.replaceAll("/", ".");
        final String className = fileName.replaceAll("/", ".").replaceAll(".class$", "");
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

          isTest = fileName.toLowerCase().contains("test");
          hasConstructors = cls.getConstructors().length > 0;

          if (!isEnum && !isAbstract && !isInterface && !isPrivate && hasConstructors && !isTest) {
            final V instance = (V) cls.newInstance();
            destination.add(instance);
          }
        } catch (final ClassNotFoundException e) {
          logger.error(e.getMessage());
        } catch (final InstantiationException e) {
          logger.error(e.getMessage());
        } catch (final IllegalAccessException e) {
          logger.error(e.getMessage());
        }
      }
    } catch (final MalformedURLException e1) {
      logger.error(e1.getMessage());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("searchClasses(String, ClassLoader) - end - return value={}", destination);
    }
    return destination;
  }

  /**
   * @param activeClassLoader
   *          the activeClassLoader to set
   */
  public static void setActiveClassLoader(final ClassLoader activeClassLoader) {
    ReflectionUtil.activeClassLoader = activeClassLoader;
  }
}
