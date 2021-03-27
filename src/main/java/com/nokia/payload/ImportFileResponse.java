package com.nokia.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.experimental.Accessors;

/**
 * @author jaisw
 *
 * Response when file saved successfully.
 */
@Accessors(chain = true, fluent = true)
public class ImportFileResponse {
	@JsonProperty("fileName")
	private String fileName;
	@JsonProperty("fileDownloadUri")
	private String fileDownloadUri;
	@JsonProperty("fileType")
	private String fileType;
	@JsonProperty("size")
	private long size;

	public ImportFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.fileType = fileType;
		this.size = size;
	}

}
