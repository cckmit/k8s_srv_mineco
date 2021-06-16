/**
 * 
 */
package com.egoveris.plugins.manager.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
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
public final class Checksum {
	/**
	 * Method to calculate the MD5 checksum of a file
	 * @param filename
	 * @return
	 * @throws Exception
	 */
 private static final Logger logger = LoggerFactory.getLogger("Checksum"); 
	private static byte[] createChecksum(String filename) throws Exception {
		InputStream fis = new FileInputStream(filename);

		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;
		try {
		  do {
		    numRead = fis.read(buffer);
		    if (numRead > 0) {
		     complete.update(buffer, 0, numRead);
		    }
		   } while (numRead != -1);
		  
  } catch (Exception e) {
    logger.error("error createChecksum",e);
  }finally {
    try {
      fis.close();
    } catch (Exception e2) {
      logger.error("error al cerrar un arhivo", e2);
    }
  }
		
		return complete.digest();
	}

	/**
	 * Method to get the MD5 final checksum of a file
	 * @param filename
	 * @return String with the MD5 checksum
	 * @throws Exception
	 */
	// see this How-to for a faster way to convert
	// a byte array to a HEX string
	public static String getMD5Checksum(String filename) throws Exception {
		byte[] b = createChecksum(filename);
		String result = "";

		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}	

	/**
	 * Method to get the sum of all CRC files.
	 * @param jarName String... jar filename
	 * @return Long 
	 */
	public static Long entiredCRC(String jarName){
		long result=0;
		
		JarFile jf=null;

		try {
			jf = new JarFile(new File(jarName));
			Enumeration<JarEntry> entries = jf.entries();
			while (entries.hasMoreElements()) {
				JarEntry jarEntry = entries.nextElement();
				result += jarEntry.getCrc();
			}
		} catch (IOException e) {
		  logger.error("error entiredCRC - Checksum ", e);
		} finally {
			if (jf!=null) {
				try {
					jf.close();
				} catch (IOException e) {
					logger.error("error entiredCRC - Checksum ", e);
				}
			}
		}
		
		return new Long(result);
	}
	
	public static Long entireCRC(List<String> jars) {
		long result=0;
		for (String filename : jars) {
			result += entiredCRC(filename);
		}
		return result;
	}
}
