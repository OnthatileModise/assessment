package com.enviro.assessment.grad001.onthatilemodise.controller;

import com.enviro.assessment.grad001.onthatilemodise.serviceImplementation.FileServiceImplementation;
import jakarta.persistence.PersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/file")
public class FileController {
    private final FileServiceImplementation fileServiceImplementation;

    /**
     * Dependency Injection of the FileServiceImplementation Interface, using a constructor
     * @param fileServiceImplementation The interface being injected in to the constructor
     * **/
    public FileController(FileServiceImplementation fileServiceImplementation) {
        this.fileServiceImplementation = fileServiceImplementation;
    }

    /**
     * Endpoint to receive the MultipartFiles
     * @param multipartFiles The entry to get the file contents from an array of files allowing for multiple uploads
     * @return Response Status stating whether the file contents where persisted properly by returning file uuids to the user
     */
    @PostMapping("/uploadFile") private ResponseEntity<List<String>> uploadFile(@RequestParam List<MultipartFile> multipartFiles) {
        try {
           return new ResponseEntity<>(fileServiceImplementation.SAVE_FILE_CONTENTS(multipartFiles), HttpStatus.OK);
        }catch (IOException | PersistenceException exception){
           return new ResponseEntity<>(Collections.singletonList("Error saving file contents with please try again later "), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to receive the persisted file contents using the files uuid
     * @param fileUUID unique uuid for file
     * @return Response Status containing file contents to the user if the uuid is correct
     */
    @GetMapping("/getFileContentsByFileName") private ResponseEntity<String> getFileContentsByFileName(@RequestParam String fileUUID) {
        try {
            return new ResponseEntity<>(fileServiceImplementation.RETRIEVE_FILE_CONTENTS_BY_UUID(fileUUID)  , HttpStatus.OK);
        }catch (NullPointerException exception){
            return new ResponseEntity<>("No file found with provided uuid " + fileUUID, HttpStatus.NOT_FOUND);
        }
    }
}
