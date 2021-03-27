package com.nokia.assembly.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nokia.dao.StoreFile;
import com.nokia.exception.FileNotFoundExcep;
import com.nokia.exception.FileStorageExcep;

@SpringBootTest
class FileStorageServiceImplTest {

	@Mock
	private StoreFile storeFile;

	@InjectMocks
	private FileStorageServiceImpl fileStorageServiceImpl;

	@BeforeEach
	public void setup() {
		ReflectionTestUtils.setField(fileStorageServiceImpl, "fileStorageLocation", Paths.get("/up"));
	}

	@Test
	public void storeFileTest() throws IOException {

		MultipartFile fileTemp = new MockMultipartFile("temp.tar", "Hello ".getBytes());
		doNothing().when(storeFile).storingFile(Mockito.any(), Mockito.any());
		assertNotNull(fileStorageServiceImpl.storeFile(fileTemp));
	}

	@Test
	public void loadFileAsResourceTest() {

		assertThrows(FileNotFoundExcep.class, () -> {
			fileStorageServiceImpl.loadFileAsResource("fileName");
		});

	}

	@Test
	public void listFilesTest() {
		assertThrows(FileStorageExcep.class, () -> {
			fileStorageServiceImpl.listFiles();
		});

	}

}
