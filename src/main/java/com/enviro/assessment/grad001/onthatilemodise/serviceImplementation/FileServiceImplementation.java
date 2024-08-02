package com.enviro.assessment.grad001.onthatilemodise.serviceImplementation;

import jakarta.persistence.PersistenceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileServiceImplementation {
    //Interface to add an extra layer on encapsulation

    List<String> SAVE_FILE_CONTENTS(List<MultipartFile> files) throws IOException , PersistenceException;

    String RETRIEVE_FILE_CONTENTS_BY_UUID(String uuid);
}
