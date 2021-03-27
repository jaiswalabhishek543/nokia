package com.nokia.assembly.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nokia.assembly.FileStorageService;
import com.nokia.controller.NokiaController;
import com.nokia.dao.StoreFile;
import com.nokia.exception.FileNotFoundExcep;
import com.nokia.exception.FileStorageExcep;

/**
 * @author jaisw
 *
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {

	private static final Logger log = LoggerFactory.getLogger(NokiaController.class);

	@Autowired
	private StoreFile storeFile;

	private final Path fileStorageLocation;

	@Value("${file.upload.directory}")
	private static String fileStorage;

	/**
	 * Constructor.
	 */
	public FileStorageServiceImpl() {
		fileStorageLocation = Paths.get("/uploads").toAbsolutePath().normalize();

		try {
			Files.createDirectories(fileStorageLocation);
		} catch (Exception ex) {
			log.error("Not able to create directory where the uploaded files will be stored.");
			throw new FileStorageExcep("Not able to create directory where the uploaded files will be stored.", ex);
		}
	}

	@Override
	public String storeFile(MultipartFile file) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {

			if (fileName.contains("..")) {
				throw new FileStorageExcep("Filename contains invalid path sequence " + fileName);
			}

			Path targetLocation = fileStorageLocation.resolve(fileName);
			storeFile.storingFile(file.getInputStream(), targetLocation);
			return fileName;
		} catch (IOException ex) {
			log.error("Could not store file " + fileName);
			throw new FileStorageExcep("Could not store file " + fileName, ex);
		}
	}

	@Override
	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				log.error("File not found " + fileName);
				throw new FileNotFoundExcep("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			log.error("File not found " + fileName);
			throw new FileNotFoundExcep("File not found " + fileName, ex);
		}
	}

	@Override
	public Set<String> listFiles() {
		try (Stream<Path> stream = Files.list(Paths.get(fileStorageLocation.toUri()))) {
			return stream.filter(file -> !Files.isDirectory(file)).map(Path::getFileName).map(Path::toString)
					.collect(Collectors.toSet());
		} catch (IOException ex) {
			log.error("Exception occurred while listing the files.");
			throw new FileStorageExcep("Exception occurred while listing the files ", ex);
		}

	}

}
