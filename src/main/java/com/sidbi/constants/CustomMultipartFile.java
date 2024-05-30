package com.sidbi.constants;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class CustomMultipartFile implements MultipartFile {
	
	private final byte[] byteArray;

    public CustomMultipartFile(byte[] byteArray) {
        this.byteArray = byteArray;
    }
    
	@Override
	public String getName() {
		// TODO - implementation depends on your requirements
		return null;
	}

	@Override
	public String getOriginalFilename() {
		// TODO - implementation depends on your requirements
		return null;
	}

	@Override
	public String getContentType() {
		// TODO - implementation depends on your requirements
		return null;
	}

	@Override
	public boolean isEmpty() {
		return byteArray == null || byteArray.length == 0;
	}

	@Override
	public long getSize() {
		return byteArray.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return byteArray;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(byteArray);
	}

	@SuppressWarnings("resource")
	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		new FileOutputStream(dest).write(byteArray);
	}

}
