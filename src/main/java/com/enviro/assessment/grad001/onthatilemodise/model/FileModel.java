package com.enviro.assessment.grad001.onthatilemodise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// An entity that represents the 'FILE_DATA' table in the database
@Entity @Table(name = "FILE_DATA") @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class FileModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "ID") private Long id;
    @Column(name = "FILE_NAME") private String fileName;
    @Column(name = "FILE_DATA") public String fileData;
    @Column(name = "UUID") public String uuid;

    /**
     * Custom Constructor to initialize a new FileModel with fileName and fileData parameters
     * @param fileData String representation of file contents
     * @param fileName String representation of original file name
     * @param uuid String uuid for file content retrieval
     * **/
    public FileModel(String fileName, String fileData , String uuid) {
        this.fileName = fileName;
        this.fileData = fileData;
        this.uuid = uuid;
    }
}
