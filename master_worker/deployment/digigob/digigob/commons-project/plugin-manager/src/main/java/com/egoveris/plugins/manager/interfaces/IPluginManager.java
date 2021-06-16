package com.egoveris.plugins.manager.interfaces;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.Predicate;

import com.egoveris.plugins.manager.PluginManager;
import com.egoveris.plugins.manager.deployers.IDeployer;
import com.egoveris.plugins.manager.deployers.strategies.IDeployStrategy;
import com.egoveris.plugins.manager.model.ExecutableInfo;
import com.egoveris.plugins.manager.model.PluginInfo;
import com.egoveris.plugins.manager.model.Version;
import com.egoveris.plugins.manager.plugins.exceptions.ExecutableException;
import com.egoveris.plugins.manager.plugins.exceptions.UnauthorizedException;
import com.egoveris.plugins.manager.plugins.interfaces.IAuthorization;
import com.egoveris.plugins.manager.plugins.interfaces.PMEventListener;

public interface IPluginManager {

	public abstract void addEventListener(PMEventListener listener);

	/**
	 * @return the initialized
	 */
	public abstract boolean isInitialized();

	/**
	 * @param initialized the initialized to set
	 */
	public abstract void setInitialized(boolean initialized);

	/**
	 * @param deployers the deployers to set
	 */
	public abstract void setDeployers(List<IDeployer> deployers);

	public abstract Version getActiveVersion();

	/**
	 * @param deployStrategy the deployStrategy to set
	 */
	public abstract void setDeployStrategy(IDeployStrategy deployStrategy);

	public abstract void switchVersion(String newVersion);

	//	public JarClassLoader getActiveClassLoader() {
	//		return getDeploys().get(getActiveVersion());
	//	}	
	//	
	//	public boolean existVersion(String checksum) {
	//		try {
	//			for (Version ver : getDeploys().keySet()) {
	//				if (ver.getSignal()!=null && ver.getSignal().equals(checksum)) return true;
	//			}
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//		
	//		return false;
	//	}
	//	
	public abstract void printVersions();

	public abstract <V> List<V> searchClasses(String packageName);

	/**
	 * Method to get info about all executable's classes from plugins
	 * @return List<ExecutableInfo>
	 */
	public abstract List<ExecutableInfo> getExecsInfo();

	/**
	 * Method for call executble's classes
	 * @param executableName
	 * @param context
	 * @param IAuthorization
	 * @throws ExecutableException
	 * @throws UnauthorizedException 
	 */
	public abstract Object execute(String executableName, Map<String, Object> context, IAuthorization autorization) throws ExecutableException,
			UnauthorizedException;

	/**
	 * Wrapper method without context parameter</b>
	 * @see PluginManager#execute(String, Map, IAuthorization) 
	 * @throws ExecutableException
	 * @throws UnauthorizedException 
	 */
	public abstract Object execute(String executableName, IAuthorization autorization) throws ExecutableException, UnauthorizedException;

	/**
	 * Wrapper method without context parameter</b>
	 * @see PluginManager#execute(String, Map, IAuthorization)
	 * @throws ExecutableException
	 * @throws UnauthorizedException 
	 */
	public abstract Object execute(String executableName) throws ExecutableException, UnauthorizedException;

	public abstract void startDeployers(String pathToCheck);

	public abstract void startDeployers();

	public abstract void pauseDeployers();

	public abstract void stopDeployers();

	/**
	 * @return the workingDirectory
	 */
	public abstract String getWorkingDirectory();

	/**
	 * Method to get Instances Of clases filtered by class and object type
	 * @param classFilter Predicate<Class<?>> for filter by Class
	 * @param objectFilter Predicate<Object> for filter by Object
	 * @return List<Object>
	 */
	public abstract List<Object> getInstancesOf(Predicate<Class<?>> classFilter, Predicate<Object> objectFilter);

	/**
	 * Method to get the PluginInfo from all installed plugins
	 * @return List<PluginInfo>
	 */
	public abstract List<PluginInfo> getPluginsInfo();

}