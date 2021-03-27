package com.nokia.dao;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * @author jaisw
 *
 */
public interface StoreFile {

	/**
	 * Storing files to storage.
	 * 
	 * @param inpStream      - inpStream
	 * @param targetLocation - targetLocation
	 */
	public void storingFile(InputStream inpStream, Path targetLocation) throws IOException;
}
