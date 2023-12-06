package org.softuni.pastryShop.util.impl;

import org.softuni.pastryShop.util.ImageEncryptor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@Component
public class ImageEncryptorImpl implements ImageEncryptor {
    @Override
    public byte[] EncryptImage(MultipartFile multipartFile) throws IOException {
        boolean isPngFile = Objects.requireNonNull(multipartFile.getContentType()).endsWith("png");
        boolean isJpgFile = Objects.requireNonNull(multipartFile.getContentType()).endsWith("jpg");
        boolean isJpegFile = Objects.requireNonNull(multipartFile.getContentType()).endsWith("jpeg");

        if (!isPngFile && !isJpgFile && !isJpegFile) {
            return null;
        }
        byte[] fileBytes = null;
        try {
            fileBytes = multipartFile.getBytes();
        } catch (Exception e) {
            return null;
        }
        return fileBytes;
    }

    @Override
    public String DecryptImage(byte[] image) throws IOException {
        String imageBase64 = "";
        try {
            imageBase64 = new String(Base64.getDecoder().decode(image));
        } catch (Exception e) {
            return null;
        }

        return imageBase64;
    }


}
