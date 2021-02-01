/**
 *
 */
package com.egoveris.workflow.designer.module.service.impl;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.DirectoryScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.egoveris.te.web.ee.satra.pl.helpers.states.GenericState;
import com.egoveris.workflow.designer.module.exception.TransformerException;
import com.egoveris.workflow.designer.module.model.Project;
import com.egoveris.workflow.designer.module.service.TransformerService;
import com.egoveris.workflow.designer.module.util.ConfigTransformer;
import com.egoveris.workflow.designer.module.util.GenericStateTemplate;
import com.egoveris.workflow.designer.module.util.JpdlTransformer;
import com.egoveris.workflow.designer.module.util.StringUtil;
import com.egoveris.workflow.designer.module.util.SubsanacionState;
import com.egoveris.workflow.designer.module.util.TypeWorkFlow;

import freemarker.template.TemplateException;
import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.Loader;
import javassist.NotFoundException;
import javassist.bytecode.ClassFile;

@Service
public class TransformerServiceImpl implements TransformerService {
	/**
	 * Logger for this class
	 */

	private static final Logger logger = LoggerFactory.getLogger(TransformerServiceImpl.class);

	public static int BUFFER_SIZE = 10240;
	private final JpdlTransformer jpdlTransformer = new JpdlTransformer();
	private final ConfigTransformer configTransformer = new ConfigTransformer();

	@Override
	public Manifest createMetaInf(final Project project, final String activationClassName) {
		if (logger.isDebugEnabled()) {
			logger.debug("createMetaInf(Project, String) - start"); //$NON-NLS-1$
		}

		final Manifest manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		manifest.getMainAttributes().put(new Name("Build-By"), project.getAuthor());
		manifest.getMainAttributes().put(new Name("Build-Jdk"), "1.6.0_29");
		manifest.getMainAttributes().put(new Name("Created-By"), "Workflow Designer");
		manifest.getMainAttributes().put(new Name("Archiver-Version"), "1.6.0_29");
		manifest.getMainAttributes().put(new Name("Build-Jdk"), "Plexus Archiver");

		manifest.getMainAttributes().put(new Name("Plugin-Type"), "WEB");
		manifest.getMainAttributes().put(new Name("Plugin-Owner"), project.getAuthor());
		manifest.getMainAttributes().put(new Name("Plugin-Name"), project.getName());
		manifest.getMainAttributes().put(new Name("Plugin-Version"), String.valueOf(project.getVersion()));
		manifest.getMainAttributes().put(new Name("Plugin-Description"), project.getDescription());
		manifest.getMainAttributes().put(new Name("Activation-Class"), String.format("%s.class", activationClassName));

		if (logger.isDebugEnabled()) {
			logger.debug("createMetaInf(Project, String) - end"); //$NON-NLS-1$
		}
		return manifest;
	}

	@Override
	public String onDeployMethodTemplate(final String workflowName, final int version, TypeWorkFlow type) {
		if (logger.isDebugEnabled()) {
			logger.debug("onDeployMethodTemplate(String) - start"); //$NON-NLS-1$
		}

		final StringBuilder tmp = new StringBuilder();

		tmp.append("public void onDeploy(java.util.Map context) {").append("\n");
		tmp.append(" System.out.println(\" ---------------------- INIT DEPLOY ---------------------------\");").append("\n");
		tmp.append(" try {").append("\n");
		tmp.append("    this.workflowResource = this.getClass().getResource(\"/resources/" +workflowName+ ".jpdl.xml\");").append("\n");
		tmp.append("	System.out.println(\"" + workflowName + ": Activation.onDeploy()\");").append("\n");
		tmp.append("	System.out.println(\"WorkFlow Resource: \" + this.workflowResource);").append("\n");
		tmp.append("	com.egoveris.te.base.service.WorkFlowService workFlowService = "
				+ "  (com.egoveris.te.base.service.WorkFlowService ) context.get(\"workflowService\");")
		.append("\n").append("\n");
		if(TypeWorkFlow.STATE.equals(type)){
			// if workflow is type of STATE add service that copy the states in TE
			tmp.append("	System.out.println(\"copy states ..... \");").append("\n");
			tmp.append("	workFlowService.copyStates(\"" + workflowName + "\"" + ", " + version + ");").append("\n");
			tmp.append("	System.out.println(\"states copied ....\");").append("\n");
		}
		tmp.append("	if (workflowResource!=null) {").append("\n");
		tmp.append("         com.egoveris.plugins.tools.WorkflowUtils.searchAndDeploy(workflowResource, workFlowService.getProcessEngine());")
							.append("\n");
		tmp.append("	}").append("\n");
		tmp.append(" System.out.println(\" ---------------------- END DEPLOY ---------------------------\");").append("\n");
		tmp.append(" } catch (java.lang.Exception e) {").append("\n");
		tmp.append(" 		e.printStackTrace();").append("\n");
		tmp.append(" }").append("\n");
		tmp.append("}").append("\n");
       
		final String returnString = tmp.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("onDeployMethodTemplate(String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	@Override
	public String onActivateMethodTemplate(final String workflowName, final String stateClassName) {
		if (logger.isDebugEnabled()) {
			logger.debug("onActivateMethodTemplate(String, String) - start"); //$NON-NLS-1$
		}

		final StringBuilder tmp = new StringBuilder();

		tmp.append("public void onActivate(java.util.List classInstances) {").append("\n"); // Object
		tmp.append("	System.out.println(\"Calling --> public void onActivate(List classInstances);\");")
				.append("\n");
		tmp.append("    this.urlConfig=this.getClass().getResource(\"/resources/config" + workflowName + ".xml\");")
				.append("\n");
		tmp.append(
				"	java.util.List lstClasses =  com.egoveris.plugins.tools.ConfigurationUtils.getConfigureClassNames(urlConfig, classInstances);")
				.append("\n");
		tmp.append("	classInstances.addAll(com.egoveris.plugins.tools.ReflectionUtils.createClassInstances("
				+ stateClassName + ".class, lstClasses));").append("\n");
		tmp.append("	java.util.List reconverted = new ArrayList();").append("\n");
		tmp.append("	for (java.util.Iterator it=(java.util.Iterator) classInstances.iterator(); it.hasNext();) {")
				.append("\n");
		tmp.append("		Object obj = (Object) it.next();").append("\n");
		tmp.append("		String clsName = obj.getClass().getName();").append("\n");
		tmp.append(
				"		if ((obj instanceof  com.egoveris.te.base.states.IState) && (lstClasses.contains(clsName))) {")
				.append("\n");
		tmp.append("			reconverted.add(obj);").append("\n");
		tmp.append("		}").append("\n");
		tmp.append("	};").append("\n");
		tmp.append("	com.egoveris.plugins.tools.ConfigurationUtils.configure(urlConfig, reconverted);").append("\n");
		tmp.append("}").append("\n");

		final String returnString = tmp.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("onActivateMethodTemplate(String, String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public String createActivationClass(final Project project, final Object stateClassObj, final String directory)
			throws TransformerException {
		if (logger.isDebugEnabled()) {
			logger.debug("createActivationClass(Project, Object, String) - start"); //$NON-NLS-1$
		}

		try {
			final String workflowName = StringUtil.camelName(project.getName());
			final String packageName = String.format("com.everis.plugin.%s", workflowName.toLowerCase());
			final String className = String.format("%s.Activation", packageName);
			final String classDirectory = packageName.replace(".", "/");
			final String pathToCreate = directory + File.separator + classDirectory;
			final String stateClass = ((CtClass) stateClassObj).getName();
			final String stateClassName = stateClass.substring(stateClass.lastIndexOf(".") + 1, stateClass.length());
			final String packageStateClass = stateClass.substring(0, stateClass.lastIndexOf("."));

			FileUtils.forceMkdir(new File(pathToCreate));
			Class parentClass;
			parentClass = GenericState.class;
			final ClassPool pool = ClassPool.getDefault();

			pool.importPackage("java.net");
			pool.importPackage("java.util");
			pool.importPackage(packageStateClass);

			pool.importPackage(URL.class.getName());

			final Loader cl = new Loader(this.getClass().getClassLoader(), pool);
			final Class parent = cl.loadClass(parentClass.getName());

			pool.appendClassPath(new ClassClassPath(parent));
			pool.appendClassPath(new ClassClassPath(cl.loadClass(java.util.Map.class.getName())));
			pool.appendClassPath(new ClassClassPath(cl.loadClass(java.util.List.class.getName())));
			pool.appendClassPath(new ClassClassPath(cl.loadClass(java.util.ArrayList.class.getName())));
			pool.appendClassPath(new ClassClassPath(cl.loadClass(java.util.Iterator.class.getName())));

			logger.info("createActivationClass(Project, Object, String)"); //$NON-NLS-1$
			final CtClass stateClassCt = (CtClass) stateClassObj;
			final CtClass evalClass = pool.makeClass(className);
			evalClass.defrost();
			stateClassCt.defrost();
			evalClass.setSuperclass(pool.get(parentClass.getName()));
			evalClass.setInterfaces(pool.get(new String[] { Serializable.class.getName() })); // Serializable

			final CtConstructor constructor = CtNewConstructor.defaultConstructor(evalClass);
			evalClass.addConstructor(constructor);

			final CtField ctFieldWorkflowResource = CtField.make("private java.net.URL workflowResource;", evalClass);
			final CtField ctFieldConfigResource = CtField.make("private java.net.URL urlConfig ;", evalClass);

			evalClass.addField(ctFieldWorkflowResource);
			evalClass.addField(ctFieldConfigResource);

			String met = onDeployMethodTemplate(workflowName, project.getVersion(), project.getTypeWorkFlow());
			final CtMethod onDeployMethod = CtNewMethod.make(met, evalClass);
			evalClass.addMethod(onDeployMethod);

			met = onActivateMethodTemplate(workflowName, stateClassName);
			final CtMethod onActivateMethod = CtNewMethod.make(met, evalClass);
			evalClass.addMethod(onActivateMethod);

			final ClassFile cf = evalClass.getClassFile();
			cf.setVersionToJava5();
			cf.write(new DataOutputStream(new FileOutputStream(String.format("%s/Activation.class", pathToCreate))));
			evalClass.detach();
			stateClassCt.detach();
			final String returnString = evalClass.getName();
			if (logger.isDebugEnabled()) {
				logger.debug("createActivationClass(Project, Object, String) - end"); //$NON-NLS-1$
			}
			return returnString;
		} catch (IOException | CannotCompileException | NotFoundException | ClassNotFoundException e) {
			logger.error("createActivationClass(Project, Object, String)", e); //$NON-NLS-1$
			throw new TransformerException("Error al crear clase", e);
		}
	}

	@Override
	public Object createStateClass(final Project project, final String directory, final Class<?> templateClass,
			String stateName) throws TransformerException {
		if (logger.isDebugEnabled()) {
			logger.debug("createStateClass(Project, String, Class<?>, String) - start"); //$NON-NLS-1$
		}
		String stateNameAux = "";
		try {
			final String workflowName = StringUtil.camelName(project.getName());

			if (stateName == null || stateName.isEmpty()) {
				stateNameAux = StringUtil.camelName(project.getName()) + "State";
			}

			final String packageName = String.format("com.everis.plugin.%s.state", workflowName.toLowerCase());
			final String className = String.format("%s.%s", packageName, stateNameAux);
			final String classDirectory = packageName.replace(".", "/");
			final String pathToCreate = directory + File.separator + classDirectory;
			FileUtils.forceMkdir(new File(pathToCreate));
			final ClassPool pool = ClassPool.getDefault();
			final Loader cl = new Loader(this.getClass().getClassLoader(), pool);
			final Class<?> parent = cl.loadClass(templateClass.getName());
			pool.appendClassPath(new ClassClassPath(parent));

			logger.info("createStateClass(Project, String, Class<?>, String)"); //$NON-NLS-1$
			CtClass existClass = null;
			try {
				existClass = pool.getCtClass(className);
				if (existClass != null) {
					existClass.defrost();
					existClass.detach();
				}
			} catch (final Exception e) {
				logger.warn("createStateClass(Project, String, Class<?>, String) - exception ignored", e); //$NON-NLS-1$
			}

			final CtClass evalClass = pool.getAndRename(templateClass.getName(), className);
			evalClass.defrost();

			final CtMethod m = evalClass.getDeclaredMethod("getTabName");
			m.setBody(String.format("{ return \"%s\"; }", project.getName()));
			final ClassFile cf = evalClass.getClassFile();
			cf.setVersionToJava5();
			cf.write(new DataOutputStream(new FileOutputStream(String.format("%s/%s.class", pathToCreate, stateNameAux))));

			if (logger.isDebugEnabled()) {
				logger.debug("createStateClass(Project, String, Class<?>, String) - end"); //$NON-NLS-1$
			}
			return evalClass;
		} catch (IOException | CannotCompileException | NotFoundException | ClassNotFoundException e) {
			logger.error("createStateClass(Project, String, Class<?>, String)", e); //$NON-NLS-1$

			throw new TransformerException("Error al crear metodo", e);
		}
	}

	@Override
	public void createJarArchive(final Manifest manifest, final File archiveFile, final File[] tobeJared,
			final String baseDirectory) throws TransformerException {
		if (logger.isDebugEnabled()) {
			logger.debug("createJarArchive(Manifest, File, File[], String) - start"); //$NON-NLS-1$
		}

		try {
			final byte buffer[] = new byte[BUFFER_SIZE];
			// Open archive file
			final FileOutputStream stream = new FileOutputStream(archiveFile);
			final JarOutputStream out = new JarOutputStream(stream, manifest);

			for (int i = 0; i < tobeJared.length; i++) {
				String fileToAdd = tobeJared[i].getPath().substring(baseDirectory.length());
				fileToAdd = fileToAdd.replace("\\", "/");

				if (tobeJared[i] == null || !tobeJared[i].exists()) {
					continue; // Just in case...
				}

				if (!fileToAdd.endsWith(".class")) {
					final String aux = fileToAdd.substring(0, fileToAdd.lastIndexOf("/"));
					final String[] directories = aux.split("/");
					StringBuilder token = new StringBuilder("");
					for (final String dir : directories) {
						if (dir.isEmpty()) {
							continue;
						}
						token.append(dir);
						token.append("/");
						final JarEntry jarAdd = new JarEntry(token.toString());
						jarAdd.setTime(tobeJared[i].lastModified());
						try {
							out.putNextEntry(jarAdd);
						} catch (final Exception e) {
							logger.warn("createJarArchive(Manifest, File, File[], String) - exception ignored", e); //$NON-NLS-1$
						}
						token.append("/");
					}
				}

				if (fileToAdd.startsWith("/")) {
					fileToAdd = fileToAdd.substring(1);
				}

				// Add archive entry
				final JarEntry jarAdd = new JarEntry(fileToAdd);
				jarAdd.setTime(tobeJared[i].lastModified());
				out.putNextEntry(jarAdd);

				// Write file to archive
				final FileInputStream in = new FileInputStream(tobeJared[i]);
				while (true) {
					final int nRead = in.read(buffer, 0, buffer.length);
					if (nRead <= 0) {
						break;
					}
					out.write(buffer, 0, nRead);
				}
				in.close();
			}

			out.close();
			stream.close();
		} catch (final IOException e) {
			logger.error("createJarArchive(Manifest, File, File[], String)", e); //$NON-NLS-1$
			throw new TransformerException("Error al crear archivo jar", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("createJarArchive(Manifest, File, File[], String) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public String createProjectJar(final Project project) throws TransformerException {
		if (logger.isDebugEnabled()) {
			logger.debug("createProjectJar(Project) - start"); //$NON-NLS-1$
		}

		try {
			final String workflowName = StringUtil.camelName(project.getName());
			final String directory = System.getProperty("java.io.tmpdir");
			final String outputDirectory = directory + File.separator + workflowName;
			final File fileTemp = new File(outputDirectory);
			if (fileTemp.exists()) {
				try {
					FileUtils.forceDelete(new File(outputDirectory));
				} catch (final IOException e) {
					logger.error("error al eliminar archivo", e);
				}
			}
			createStateClass(project, outputDirectory, SubsanacionState.class, SubsanacionState.class.getSimpleName());
			final Object stateClass = createStateClass(project, outputDirectory, GenericStateTemplate.class, null);
			final String activationObject = createActivationClass(project, stateClass, outputDirectory);

			jpdlTransformer.createJpdlFile(project, outputDirectory);
			configTransformer.createConfigFile(project, outputDirectory);

			final String zulPath = String.format("%s/expediente/macros/%s/genericState.zul", outputDirectory,
					workflowName.toLowerCase());
			final URL urlOrigen = this.getClass().getResource("/spring/genericState.zul");
			if (urlOrigen != null) {
				FileUtils.copyURLToFile(urlOrigen, new File(zulPath));
			}

			final File jarFile = new File(outputDirectory + File.separator + workflowName + ".jar");

			final DirectoryScanner scanner = new DirectoryScanner();
			scanner.setBasedir(outputDirectory);
			scanner.setIncludes(new String[] { "**/*.*" }); // Antes iba con \\
															// al comienzo
			scanner.setExcludes(new String[] { "*.jar" });
			scanner.scan();

			if (scanner.getIncludedFilesCount() > 0) {
				final File[] files = new File[scanner.getIncludedFilesCount()];
				final List<String> archivos = Arrays.asList(scanner.getIncludedFiles());
				int count = 0;
				for (final String file : archivos) {
					final String f = outputDirectory + File.separator + file;
					files[count++] = new File(f);
				}

				createJarArchive(createMetaInf(project, activationObject), jarFile, files, outputDirectory);
			}

			final String returnString = jarFile.getPath();
			if (logger.isDebugEnabled()) {
				logger.debug("createProjectJar(Project) - end"); //$NON-NLS-1$
			}
			return returnString;
		} catch (IOException | TemplateException e) {
			logger.error("createProjectJar(Project)", e); //$NON-NLS-1$
			throw new TransformerException("Error al crear projecto jar", e);
		}
	}
}
