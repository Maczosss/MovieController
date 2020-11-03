package com.example.vanilla.controllers;


import com.example.vanilla.model.UploadFileResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimeType;
import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class FileController {

    private final static String FILE_UPLOAD_PATH = "C:\\Users\\macie\\OneDrive\\Desktop\\Projekty java\\ProjektyJava\\Spring\\saved";

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public UploadFileResponse upload(@RequestParam("file") MultipartFile file) {

        String fileName = file.getOriginalFilename();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String myString = fileName.substring(0, fileName.lastIndexOf("."));


        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(FILE_UPLOAD_PATH + fileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new UploadFileResponse(
                file.getOriginalFilename(),
                file.getContentType(),
                (double) file.getSize() / 1024 / 1024);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName,
                                                 HttpServletRequest request) {

        Resource resource = getFile(fileName);

        MediaType mt = recognizeContentType(resource, request);

        return ResponseEntity.ok().contentType(mt).body(resource);
//        return ResponseEntity.ok().contentType(MediaType.MULTIPART_FORM_DATA).body(resource);
    }

    private MediaType recognizeContentType(Resource resource, HttpServletRequest request) {

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            {
                if (contentType != null) {
                    return MediaType.parseMediaType(contentType);
                } else {
                    return MediaType.APPLICATION_JSON;
                }
            }
        }
    }

    private Resource getFile(String fileName) {

        Resource resource = null;
        Path path = Paths.get(FILE_UPLOAD_PATH + fileName);
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return resource;
    }

}