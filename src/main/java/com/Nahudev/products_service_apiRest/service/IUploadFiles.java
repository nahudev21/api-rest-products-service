package com.Nahudev.products_service_apiRest.service;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadFiles {

    public String handleFileUpload(MultipartFile file) throws Exception;

}
