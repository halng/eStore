package com.e.store.media.service.utils;

import com.e.store.media.exception.BadRequestException;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public abstract class UploadToBucketUtils {

	private final List<String> SUPPORTED_EXTENSION = Arrays.asList(".jpeg", ".jpg", ".png");

	String getFileExtension(String fileName) {
		if (fileName != null && fileName.contains(".")) {
			for (var e : SUPPORTED_EXTENSION) {
				if (fileName.endsWith(e)) {
					return e;
				}
			}
		}
		throw new BadRequestException("File extension not supported " + fileName);
	}

	public abstract String uploadFile(MultipartFile multipartFile, String fileName, String contentType);

}
