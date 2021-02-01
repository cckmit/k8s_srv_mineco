/**
 * 
 */
package com.egoveris.plugins.manager.tools;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.plugins.manager.model.Version;

/**
 * @author difarias
 *
 */
public class SnapshotManager {
	final static Logger logger = LoggerFactory.getLogger(SnapshotManager.class);
	
	public static Version makeSnapshot(String workingDirectory, Version activeVersion,List<String> files, String checksum) {
		
		logger.debug("---------------");
		logger.debug("MAKING SNAPSHOT");
		logger.debug("---------------");
		
		activeVersion.setActive(false);

		Version newVersion = activeVersion.inc();
		newVersion.setSignal(checksum);
		newVersion.setActive(true);
		newVersion.getFiles().clear();
		
		try {
			URL url = new URL(workingDirectory);
		
			String destDir = url.getFile()+File.separator+newVersion.getSnapshotDir();
			
			for (String srcFile: files) {
				File source = new File(srcFile);
				File destination = new File(destDir);
				try {
					FileUtils.copyFileToDirectory(source, destination, false);
					destination.setLastModified(source.lastModified());
					String destinationFile = destination.getAbsolutePath()+File.separator+FilenameUtils.getName(source.getAbsolutePath());
					newVersion.getFiles().add(destinationFile);
				} catch (IOException e) {
					logger.error("error al copiar el archivo", e);
				}
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		return newVersion;
	}
}
