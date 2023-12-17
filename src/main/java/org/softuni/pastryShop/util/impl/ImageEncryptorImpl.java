package org.softuni.pastryShop.util.impl;

import org.softuni.pastryShop.util.ImageEncryptor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    public String DecryptImage(byte[] image) {
        String imageBase64 = "";
        try {
            imageBase64 = new String(Base64.getEncoder().encode(image));
        } catch (Exception e) {
            return null;
        }

        return imageBase64;
    }

    @Override
    public MultipartFile DecryptImageAsMultipartFile(byte[] fileBytes) throws IOException {
        File file = File.createTempFile("tempFile","png");

        try{
            OutputStream f = new FileOutputStream(file);
            f.write(fileBytes);
            f.close();
        }
        catch (Exception e){
            return null;
        }

        MultipartFile multipartFile = new MockMultipartFile("photo", file.getName(), "img", fileBytes);

        return multipartFile;

    }


}
