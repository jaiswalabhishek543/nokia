package com.nokia.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nokia.assembly.FileStorageService;
import com.nokia.payload.ImportFileResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller to handle operations on files.
 *
 */
@RestController
@Api(value = "Nokia-Api", tags = "I/O operation")
public class NokiaController {
	private static final Logger log = LoggerFactory.getLogger(NokiaController.class);

	@Autowired
	private FileStorageService fileStorageService;

	/**
	 * @param file - file
	 * @return ImportFileResponse
	 */
	@ApiOperation(value = "", notes = "Import files from storage", tags = "I/O operation")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "File imported successfully", response = Void.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PostMapping("/importFile")
	public ImportFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
		String fileName = fileStorageService.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();

		return new ImportFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}

	/**
	 * @param files - files
	 * @return List
	 */
	@ApiOperation(value = "", notes = "Import multiple files from storage", tags = "I/O operation")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "File imported successfully", response = Void.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PostMapping("/importMultipleFiles")
	public List<ImportFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}

	/**
	 * @param fileName - fileName
	 * @param request  - request
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "", notes = "Download file from storage", tags = "I/O operation")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "File fetched successfully", response = Void.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping("/downloadFile/{fileName}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName,
			HttpServletRequest request) {

		if (StringUtils.isEmpty(fileName)) {
			return null;
		}

		Resource resource = fileStorageService.loadFileAsResource(fileName.trim());
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			log.info("Could not determine file type.");
		}

		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	/**
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "", notes = "List names of all files in storage", tags = "I/O operation")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Files name fetched successfully", response = Void.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping("/listFileName")
	public ResponseEntity<Set> listFilesInStorage() {

		return new ResponseEntity<Set>(fileStorageService.listFiles(), HttpStatus.OK);

	}

}
