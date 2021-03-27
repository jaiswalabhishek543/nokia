package com.nokia.assembly;

import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jaisw
 * 
 * Provide operations to store, download & list files.
 */
public interface FileStorageService {

	/**
	 * Store file into storage.
	 * 
	 * @param file - file
	 * @return String
	 */
	String storeFile(MultipartFile file);

	/**
	 * Load file from storage.
	 * 
	 * @param fileName - fileName
	 * @return Resource
	 */
	Resource loadFileAsResource(String fileName);
	
	
	/**
	 * List all the files in storage.
	 * 
	 * @return Set<String>
	 */
	Set<String> listFiles();

}
