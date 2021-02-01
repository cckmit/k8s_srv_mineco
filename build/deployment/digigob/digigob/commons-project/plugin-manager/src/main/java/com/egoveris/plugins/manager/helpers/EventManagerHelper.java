/**
 * 
 */
package com.egoveris.plugins.manager.helpers;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.ListUtils;

import com.egoveris.plugins.manager.model.PluginInfo;
import com.egoveris.plugins.manager.plugins.interfaces.PMEventListener;

/**
 * @author difarias
 *
 */
public class EventManagerHelper {
	private static EventManagerHelper ourInstance;
	private List<PMEventListener> lstListeners;

	private class ThreadMessage {
		private PluginInfo pluginInfo;
		public List<PluginInfo> lstPluginInfo;
		boolean isDeploy = false;
		boolean isMasive = false;

		public ThreadMessage(List<PluginInfo> lstPluginInfo, boolean isDeploy) {
			this.lstPluginInfo = lstPluginInfo;
			this.isDeploy = isDeploy;
			this.isMasive = true;
		}

		public ThreadMessage(PluginInfo pluginInfo, boolean isDeploy) {
			this.pluginInfo = pluginInfo;
			this.isDeploy = isDeploy;
			this.isMasive = false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#start()
		 */
		
//		public synchronized void start() {
//			/*
//			 * for (PMEventListener listener : getLstListeners()) { if
//			 * (isMasive) { listener.onFullActivation(lstPluginInfo); } else {
//			 * if (isDeploy) { listener.onDeploy(this.pluginInfo); } else {
//			 * listener.onActivation(this.pluginInfo); } } }
//			 */
//		}
	}

	private EventManagerHelper() {
	}

	public synchronized static EventManagerHelper getInstance() {
		if (ourInstance == null) {
			ourInstance = new EventManagerHelper();
		}
		return ourInstance;
	}

	/**
	 * @return the lstListeners
	 */
	public List<PMEventListener> getLstListeners() {
		if (lstListeners == null) {
			lstListeners = new LinkedList<>();
		}
		return lstListeners;
	}

	private void notifyListeners(boolean isDeploy, boolean isMasive, PluginInfo... lstPluginInfo) {
		for (PMEventListener listener : getLstListeners()) {
			if (isMasive) {
				listener.onFullActivation(lstPluginInfo);
			} else {
				for (PluginInfo pluginInfo : lstPluginInfo) {
					if (isDeploy) {
						listener.onDeploy(pluginInfo);
					} else {
						listener.onActivation(pluginInfo);
					}
				}
			}
		}
	}

	/**
	 * Method to add an event listener
	 * 
	 * @param listener
	 *            PMEventListener
	 */
	public void addEventListener(PMEventListener listener) {
		getLstListeners().add(listener);
	}

	public void notifyDeploy(PluginInfo pluginInfo) {
		// new ThreadMessage(pluginInfo,true).start();
		notifyListeners(true, false, pluginInfo);
	}

	public void notifyActivation(PluginInfo pluginInfo) {
		notifyListeners(false, false, pluginInfo);
		// new ThreadMessage(pluginInfo,false).start();
	}

	public void notifyAllActivation(List<PluginInfo> lstPluginInfo) {
		notifyListeners(false, true, lstPluginInfo.toArray(new PluginInfo[] {}));
		// new ThreadMessage(lstPluginInfo,true).start();
	}
}
