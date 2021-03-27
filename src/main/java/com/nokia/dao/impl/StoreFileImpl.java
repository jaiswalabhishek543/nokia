package com.nokia.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Repository;

import com.nokia.dao.StoreFile;

/**
 * @author jaisw
 *
 */
@Repository
public class StoreFileImpl implements StoreFile {

	@Override
	public void storingFile(InputStream inpStream, Path targetLocation) throws IOException {
		Files.copy(inpStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
	}

}
