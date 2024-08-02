package com.enviro.assessment.grad001.onthatilemodise.service;

import com.enviro.assessment.grad001.onthatilemodise.model.FileModel;
import com.enviro.assessment.grad001.onthatilemodise.repository.FileRepository;
import com.enviro.assessment.grad001.onthatilemodise.serviceImplementation.FileServiceImplementation;
import jakarta.persistence.PersistenceException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileService implements FileServiceImplementation {

    private final FileRepository fileRepository;

    //Dependency Injection of the FileRepository Interface, using a constructor
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Method to attempt to save the MultipartFiles contents
     * @param multipartFiles files where the content will be read and persisted
     **/

    @Override
    public List<String> SAVE_FILE_CONTENTS(List<MultipartFile> multipartFiles) throws IOException , PersistenceException {

        // Get the number of files where the contents need to be read
        int fileCount = multipartFiles.size();

        //List of uuid String that have been stored in the database
        List<String> uuids = new ArrayList<>();

        // Loop through the files that have been uploaded
        for (MultipartFile multipartFile : multipartFiles) {

            //Generate uuid for file contents retrieval
            UUID uuid = UUID.randomUUID();

            // Initialise new StringBuilder Instance
            StringBuilder fileContents = new StringBuilder();

            //Attempt to read Contents of File
            // using a try with resources block in order to ensure that the reader is closed at the end of the statement
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))){
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContents.append(line).append("\n");
                }
            }

            // Initialise a new file Model Instance ,
            // using a custom constructor pass in the values that need to be persisted
            // crete a unique file name using the file name and uuid
            FileModel fileModel = new FileModel(multipartFile.getOriginalFilename() , fileContents.toString() , multipartFile.getOriginalFilename() + "_" + uuid);

            // Persist the FileModel
            // and retrieve saved uuids
             uuids.add(fileRepository.save(fileModel).getUuid());
        }

         // if file save count matches the number of files uploaded then all files were saved successfully else we return false
         if (fileCount == uuids.size()) return uuids;
         // if there is a mismatch between the number of uuids then something went wrong
         else throw new PersistenceException();
    }

    /**
    * Method to retrieve the file contents using the files uuid
     * @param uuid unique uuid for file
     * @return String containing file contents
    **/
    @Override
    public String RETRIEVE_FILE_CONTENTS_BY_UUID(String uuid){
        // Retrieve the saved file contents using the files uuid
        String fileContents = fileRepository.getFileContentsByFileUUID(uuid);
        // if the file contents are null then throw a null pointer exception else return the contents of the file
        if (Strings.isEmpty(fileContents)){
            throw new NullPointerException();
        }else {
            return fileContents;
        }
    }
}
