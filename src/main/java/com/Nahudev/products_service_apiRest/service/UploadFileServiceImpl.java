package com.Nahudev.products_service_apiRest.service;

import com.Nahudev.products_service_apiRest.dto.ResponseImageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class UploadFileServiceImpl implements IUploadFiles{

    @Override
    public String handleFileUpload(MultipartFile file) throws Exception {

        ResponseImageDTO response = new ResponseImageDTO();

        try {

            String fileName = UUID.randomUUID().toString();
            byte [] bytes = file.getBytes();
            String fileOriginalName = file.getOriginalFilename();

            long fileSize = file.getSize();
            long maxFileSize = (long) (5 * 1024 * 1024);

            if (fileSize > maxFileSize) {
                return "El tamaño del archivo debe ser menor o igual a 5 MB.";
            }

            if (
                    !fileOriginalName.endsWith(".jpg") &&
                    !fileOriginalName.endsWith(".jpeg") &&
                    !fileOriginalName.endsWith(".png")
            ) {
                return "Solo se permiten archivos JPG, JPEG y PNG!";
            }

            String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
            String newFileName = fileName + fileExtension;
            response.setNewFileName(newFileName);

            File folder = new File("src/main/resources/pictures");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Path path = Paths.get("src/main/resources/pictures/" + newFileName);
            Files.write(path, bytes);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }


        return response.getNewFileName();
    }

    public void deleteImage(String nombre) {
        String route = "images//";
        File file = new File(route + nombre);
        file.delete();
    }


}
