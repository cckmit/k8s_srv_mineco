/**
 * 
 */
package com.egoveris.plugins.manager.tools;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author difarias
 *
 */
public class JarClassLoader extends ClassLoader {
  final static Logger logger = LoggerFactory.getLogger(JarClassLoader.class);
  private List<String> myClassPath;

  private List<String> myClassPathDependencies;

  private HashMap<String, Class<?>> classes; // used to cache already defined
                                               // classes

  private ClassLoader parent;

  /**
   * Default constructor
   */
  public JarClassLoader() {
    /// super(JarClassLoader.class.getClassLoader()); //calls the parent class
    /// loader's constructor
    super(Thread.currentThread().getContextClassLoader());
  }

  /**
   * Default constructor
   */
  public JarClassLoader(ClassLoader parent) {
    if (!(parent instanceof JarClassLoader)) {
      this.parent = parent;
    }
    // System.out.println("Parent classloader -->
    // "+parent.getClass().getName());
  }

  /**
   * @return the myClassPath
   */
  public List<String> getMyClassPath() {
    if (myClassPath == null) {
      myClassPath = new ArrayList<>();

      // --- adding JARS from the default classLoader ---
      ClassLoader cl = ClassLoader.getSystemClassLoader();
      URL[] urls = ((URLClassLoader) cl).getURLs();
      for (URL url : urls) {
        myClassPath.add(url.getFile());
      }

      // --- adding JARS from the parent classLoader ---
      if (!(parent instanceof JarClassLoader)) {
        cl = parent;
        urls = ((URLClassLoader) cl).getURLs();
        for (URL url : urls) {
          myClassPath.add(url.getFile());
        }
      }

    }
    return myClassPath;
  }

  /**
   * @param myClassPath
   *          the myClassPath to set
   */
  public void setMyClassPath(List<String> myClassPath) {
    this.myClassPath = myClassPath;
  }

  public List<String> getMyClassPathDependencies() {
    if (myClassPathDependencies == null) {
      myClassPathDependencies = new ArrayList<>();
    }

    return myClassPathDependencies;
  }

  public void setMyClassPathDependencies(List<String> myClassPathDependencies) {
    this.myClassPathDependencies = myClassPathDependencies;
  }

  /**
   * @return the classes
   */
  public HashMap<String, Class<?>> getClasses() {
    if (classes == null) {
      classes = new HashMap<>();
    }
    return classes;
  }

  /**
   * @param classes
   *          the classes to set
   */
  public void setClasses(HashMap<String, Class<?>> classes) {
    this.classes = classes;
  }

  public Class<?> loadClass(String className) throws ClassNotFoundException {
    return super.loadClass(className);
  }

  private Class<?> innerFindClass(String className, List<String> classPath) {
    byte classByte[];
    Class<?> result = null;
    InputStream istream = null;
    JarFile jar = null;
    try {
      JarEntry entry = null;
      for (String jarFile : classPath) {
          jar = getJarFIle(jarFile);
          if(jar == null)
            continue;
          entry = jar.getJarEntry(className.replace(".", "/") + ".class");
          if (entry != null)
            break;
      }

      if (entry != null) {
        istream = jar.getInputStream(entry);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int nextValue = istream.read();
        while (-1 != nextValue) {
          byteStream.write(nextValue);
          nextValue = istream.read();
        }
        classByte = byteStream.toByteArray();
        result = defineClass(className, classByte, 0, classByte.length, null);
        getClasses().put(className, result);
      }
      return result;
    } catch (ClassFormatError e) {
      logger.error(String.format("Cannot define class %s", className));
      logger.error(String.format("+-> %s: %s", e.getClass().getName(), e));
    } catch (Exception e) {
      logger.error(String.format("Cannot define class %s ", className), e);
    } finally {
        try {
          if(istream != null)
            istream.close();
          if (jar != null) 
            jar.close();
        } catch (IOException e) {
          logger.error("Error close inputStream", e);
        }
    }

    return null;
  }

  private JarFile getJarFIle(String jarFile){
    JarFile jarFileInput;
    try {
      jarFileInput = new JarFile(jarFile);
      return jarFileInput;
    } catch (IOException fnfe) {
      //logger.error("Error al crear JarFile",fnfe);
    }
    return null;
  }
  
  
  
  
  
  
  @Override
  public Class<?> findClass(String classNameParam) {
    Class<?> result;
    String className = classNameParam;
    if (className.endsWith(".class")) {
         className = className.substring(0, className.lastIndexOf('.'));
    }

    result = getClasses().get(className); // checks in cached classes
    if (result != null) {
      return result;
    }

    try {
      return findSystemClass(className);
    } catch (ClassNotFoundException e) {
    //  logger.error("error al encontrar clase",e);
    }

    // ---- buscamos primero en nuestro classpath de dependencias ---
    try {
      result = innerFindClass(className, getMyClassPathDependencies());
      if (result != null) {
        return result;
      }
    } catch (Exception e) {
     // logger.error("error al encontrar clase",e);
    }
    // ---------------------------------------------------------
    try {
      if (parent != null) {
        return parent.loadClass(className);
      }
    } catch (ClassNotFoundException e) {
      //logger.error("error al encontrar clase",e);
    }

    try {
      return JarClassLoader.class.getClassLoader().loadClass(className);
    } catch (Exception e) {
    //  logger.error("error al encontrar clase",e);
    }

    try {
      if (!(Thread.currentThread().getContextClassLoader() instanceof JarClassLoader)) {
        return Thread.currentThread().getContextClassLoader().loadClass(className);
      }
    } catch (ClassNotFoundException e) {
     // logger.error("error al encontrar clase",e);
    }

    // ---- buscamos primero en nuestro propio classpath ---
     result = innerFindClass(className, getMyClassPath());
     if (result != null) {
       return result;
     }

    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.ClassLoader#getResource(java.lang.String)
   */
  @Override
  public URL getResource(String name) {
    URL url = findResource(name);

    if (url == null) {
      if (parent != null) {
        URL parentUrl = parent.getResource(name);
        if (parentUrl != null)
          return parentUrl;
      }
    }

    return super.getResource(name);
  }

  private URL findResourceInClassPath(String name, List<String> classPath) {
    JarFile jar = null;
    try {
      for (String jarFile : classPath) {
        jar = getJarFIle(jarFile);
        if(jar==null)
          continue;
        JarEntry je = jar.getJarEntry(name);
        if (je != null) {
          File file = new File(jarFile);
          String jarUrlStr = String.format("jar:%s!/%s", file.toURI().toURL().toString(), name);
          return new URL(jarUrlStr);
        }
      }
    } catch (IOException e) {
      logger.error("error al cerrar el archivo", e);
    } finally {
      try {
        if(jar != null){
             jar.close();
        }
      } catch (Exception e2) {
        logger.error("error al cerrar el archivo", e2);
      }
    }

    if (parent != null) {
      URL parentUrl = parent.getResource(name);
      if (parentUrl != null)
        return parentUrl;
    }

    return null;

  }

  @Override
  protected URL findResource(String name) {
    URL result;
    result = findResourceInClassPath(name, getMyClassPath());
    if (result == null) {
      result = findResourceInClassPath(name, getMyClassPathDependencies());
    }
    return result;
  }

  public File findResourceAndExtract(String name, String dirToDeploy) {
    JarFile jar = null;
    InputStream input = null;
    try {
      for (String jarFile : getMyClassPath()) {
        jar = getJarFIle(jarFile);
        if(jar == null)
           continue;
        JarEntry je = jar.getJarEntry(name);
        if (je != null) {
          File tmpFile = new File(String.format("%s/%s", dirToDeploy, name));
          logger.debug("File created: " + tmpFile.getAbsolutePath());
          input = jar.getInputStream(je);
          FileOutputStream output = new FileOutputStream(tmpFile);
          IOUtils.copy(input, output);
          output.flush();
          output.close();
          return tmpFile;
        }
      }
    } catch (IOException e) {
      logger.error("Error close input",e);
    } finally {
        try {
          if(input != null)
            input.close();
          if(jar != null)
            jar.close();
        } catch (IOException e) {
           logger.error("Error close input",e);
        }
    }
    return null;
  }

  /**
   * Method to add a jarFile in my classPath
   * 
   * @param jarFilename
   */
  public void includeJar(String jarFilename) {
    getMyClassPath().add(jarFilename);
  }

  /**
   * Method to add a jarfile in the Dependencies classPath
   * 
   * @param jarFile
   */
  public void includeJarDependencies(String jarFile) {
    getMyClassPathDependencies().add(jarFile);
  }

  /**
   * Method to clone classLoader but with inner classPath empty
   */
  public JarClassLoader clone() {
    JarClassLoader newCL = new JarClassLoader(Thread.currentThread().getContextClassLoader());
    if (getParent() != null) {
      newCL.parent = this.getParent();
    }
    newCL.getClasses();
    return newCL;
  }

  /**
   * Method to include various jars to classpath
   * 
   * @param pluginJar
   */
  public void includeJars(List<String> pluginJar) {
    if (pluginJar != null && !pluginJar.isEmpty()) {
      for (String jarfile : pluginJar) {
        includeJar(jarfile);
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.ClassLoader#getResourceAsStream(java.lang.String)
   */
  @Override
  public InputStream getResourceAsStream(String name) {
    return super.getResourceAsStream(name);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.ClassLoader#loadClass(java.lang.String, boolean)
   */
  @Override
  protected synchronized Class<?> loadClass(String name, boolean resolve)
      throws ClassNotFoundException {
    return super.loadClass(name, resolve);
  }

  @Override
  public Package getPackage(String name) {
    StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();

    boolean cameFromJarCL = false;
    boolean invokedByDefinePackage = false;

    for (int c = 0; c < stacktrace.length; c++) {
      if (!invokedByDefinePackage) {
        invokedByDefinePackage = stacktrace[c].getMethodName().equalsIgnoreCase("definePackage");
      }
      if (!cameFromJarCL) {
        cameFromJarCL = stacktrace[c].getClassName().contains("JarClassLoader");
      }
    }

    if (cameFromJarCL && invokedByDefinePackage) {
      return null;
    }

    Package pkg = super.getPackage(name);

    if (pkg == null) {
      pkg = definePackage(name, "class: " + name, null, null, "class: " + name, null, null, null);
    }

    return pkg;
  }
}
