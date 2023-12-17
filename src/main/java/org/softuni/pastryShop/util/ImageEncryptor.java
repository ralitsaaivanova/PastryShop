package org.softuni.pastryShop.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageEncryptor {
    public byte[] EncryptImage(MultipartFile multipartFile) throws IOException;

    public String DecryptImage(byte[] image);

    MultipartFile DecryptImageAsMultipartFile(byte[] fileBytes) throws IOException;
}
